package web.logicImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IAppointmentDAO;
import web.entity.Appointment;
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

@Component("appointments")
public class AppointmentLogicImpl implements IAppointmentLogic {
	
	@Autowired
	private IAppointmentDAO appointmentsrepository;
	
	@Autowired
	private UserPermitLogicImpl permits;

	public AppointmentLogicImpl() {}

	@Override
    public Appointment register(Appointment t, User requiring) {
		permits.require(requiring, Permit.CREATE_APPOINTMENT);
		return this.appointmentsrepository.add(t);
	}
	
	@Override
    public Optional<Appointment> findById(int id, User requiring) {
		permits.require(requiring, Permit.READ_APPOINTMENT);
		return this.appointmentsrepository.findById(id);
	}

	@Override
    public Appointment update(Appointment turno, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.UPDATE_APPOINTMENT);
		Optional<Appointment> search = appointmentsrepository.findById(turno.getId());
		if(search.isEmpty()) throw new NotFoundException();
		Appointment original = search.get();
		if (turno.getDate() != null) original.setDate(turno.getDate());
        if (turno.getRemarks() != null) original.setRemarks(turno.getRemarks());
        if (turno.getStatus() != null) original.setStatus(turno.getStatus());
        if (turno.getAssignedDoctor() != null) original.setAssignedDoctor(turno.getAssignedDoctor());
        if (turno.getPatient() != null) original.setPatient(turno.getPatient());
		return this.appointmentsrepository.update(turno);
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
		permits.require(requiring, Permit.DISABLE_APPOINTMENT);
		appointmentsrepository.disable(id);
	}

	@Override
	public Optional<Appointment> findById(int id, boolean includeInactives, User requiring) {
		permits.require(requiring, Permit.READ_APPOINTMENT);
		return appointmentsrepository.findById(id, includeInactives);
	}
	

	@Override
	public List<AppointmentMinimalView> search(AppointmentQuery patientQuery, User requiring) {
		permits.require(requiring, Permit.READ_APPOINTMENT);
		return appointmentsrepository.search(patientQuery);
	}
	

	public IAppointment getById(int id, User requiring) throws NotAllowedException, NotFoundException {
		requiring = permits.require(requiring, Permit.READ_DOCTOR_APPOINTMENTS, Permit.READ_PATIENT_APPOINTMENTS, Permit.READ_APPOINTMENT);
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
