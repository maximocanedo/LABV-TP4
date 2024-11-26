package web.logicImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IAppointmentDAO;
import web.entity.Appointment;
import web.entity.AppointmentStatus;
import web.entity.Doctor;
import web.entity.IAppointment;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.User;
import web.entity.input.AppointmentQuery;
import web.entity.view.AppointmentCommunicationView;
import web.entity.view.AppointmentMinimalView;
import web.exceptions.CommonException;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;
import web.logic.IAppointmentLogic;
import web.logic.validator.AppointmentValidator;

@Component("appointments")
public class AppointmentLogicImpl implements IAppointmentLogic {
	
	@Autowired
	private IAppointmentDAO appointmentsrepository;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	@Autowired
	private AppointmentValidator appointmentValidator;

	public AppointmentLogicImpl() {}
	

	@Override
    public Appointment register(Appointment t, User requiring) {
		Doctor d = t.getAssignedDoctor();
		try {
			requiring = permits.inquire(requiring, Permit.CREATE_APPOINTMENT);
		} catch(NotAllowedException e) {
			if(requiring.getDoctor() == null || !requiring.getDoctor().isActive()) {
				throw e;
			} else {
				d = requiring.getDoctor();
			}
		}
		appointmentValidator.isAssigned(t);
		System.out.print(t);
		t.setDate(appointmentValidator.date(t.getDate(), t.getAssignedDoctor()));
		t.setRemarks(appointmentValidator.remarks(t.getRemarks()));
		t.setStatus(AppointmentStatus.PENDING);
		t.setAssignedDoctor(appointmentValidator.doctor(d));
		t.setPatient(appointmentValidator.patient(t.getPatient()));
		return this.appointmentsrepository.add(t);
	}
	
	public AppointmentCommunicationView registerComm(Appointment t, User requiring) {
		Appointment data = register(t, requiring);
		return AppointmentCommunicationView.from(data);
	}
	
	@Override
    public Optional<Appointment> findById(int id, User requiring) {
		requiring = permits.inquireAppointment(requiring, id, Permit.READ_APPOINTMENT);
		boolean includeInactives = requiring.can(Permit.ENABLE_APPOINTMENT);
		return this.appointmentsrepository.findById(id, includeInactives);
	}

	@Override
    public Appointment update(Appointment turno, User requiring) throws NotFoundException {
		permits.inquireAppointment(requiring, turno.getId(), Permit.UPDATE_APPOINTMENT);
		Optional<Appointment> search = appointmentsrepository.findById(turno.getId(), false);
		if(search.isEmpty()) throw new NotFoundException();
		Appointment original = search.get();
		//if(original.getAssignedDoctor().getAssignedUser().getUsername() != requiring.getUsername())
		//	permits.require(requiring, Permit.UPDATE_APPOINTMENT);
        if (turno.getAssignedDoctor() != null) original.setAssignedDoctor(appointmentValidator.doctor(turno.getAssignedDoctor()));
		if (turno.getDate() != null) original.setDate(appointmentValidator.date(turno.getDate(), turno.getAssignedDoctor()));
        if (turno.getRemarks() != null) original.setRemarks(appointmentValidator.remarks(turno.getRemarks()));
        if (turno.getStatus() != null) original.setStatus(appointmentValidator.status(turno.getStatus()));
        if (turno.getPatient() != null) original.setPatient(appointmentValidator.patient(turno.getPatient()));
		return this.appointmentsrepository.update(original);
	}
	
	public AppointmentCommunicationView updateComm(Appointment appointment, User requiring) throws NotFoundException {
		Appointment data = update(appointment, requiring);
		return AppointmentCommunicationView.from(data);
	}

	@Override
	public int countPresencesBetween(Date d1, Date d2, User requiring) {
		permits.require(requiring, Permit.READ_APPOINTMENT);
		return this.appointmentsrepository.countPresencesBetween(d1, d2);
	}

	@Override
	public int countAbsencesBetween(Date d1, Date d2, User requiring) {
		permits.require(requiring, Permit.READ_APPOINTMENT);
		return this.appointmentsrepository.countAbsencesBetween(d1, d2);
	}

	@Override
	public void enable(int id, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.ENABLE_APPOINTMENT);
		appointmentsrepository.enable(id);
	}
	
	@Override
	public void disable(int id, User requiring) throws NotFoundException {
		permits.inquireAppointment(requiring, id, Permit.DISABLE_APPOINTMENT);
		appointmentsrepository.disable(id);
	}

	@Override
	public Optional<Appointment> findById(int id, boolean includeInactives, User requiring) {
		permits.inquireAppointment(requiring, id, Permit.READ_APPOINTMENT);
		boolean i = includeInactives && requiring.can(Permit.ENABLE_APPOINTMENT);
		return appointmentsrepository.findById(id, i);
	}
	

	@Override
	public List<AppointmentMinimalView> search(AppointmentQuery query, User requiring) {
		try {
			requiring = permits.inquire(requiring, Permit.READ_APPOINTMENT);			
		} catch(NotAllowedException e) {
			if(!requiring.can(Permit.READ_APPOINTMENT) && requiring.getDoctor() != null) {
				query.filterByDoctor(requiring.getDoctor().getFile() + "", requiring.getDoctor().getId() + "");
			} else throw e;			
		}
		return appointmentsrepository.search(query);
	}
	

	public IAppointment getById(int id, User requiring) throws NotAllowedException, NotFoundException {
		requiring = permits.inquireAppointment(requiring, id, Permit.READ_DOCTOR_APPOINTMENTS, Permit.READ_PATIENT_APPOINTMENTS, Permit.READ_APPOINTMENT);
		boolean includeInactives = requiring.can(Permit.ENABLE_APPOINTMENT);
		CommonException notFound = new NotFoundException("Appointment not found. ");
		if(requiring.can(Permit.READ_PATIENT_APPOINTMENTS)) {
			Optional<AppointmentMinimalView> x = appointmentsrepository.findMinById(id, includeInactives);
			if(x.isEmpty()) throw notFound;
			return x.get();
		}
		Optional<AppointmentCommunicationView> x = appointmentsrepository.findComById(id, includeInactives);
		if(x.isEmpty()) throw notFound;
		return x.get();
	}
	
	
	
}
