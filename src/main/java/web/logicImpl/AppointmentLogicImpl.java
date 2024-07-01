package web.logicImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IAppointmentDAO;
import web.entity.Appointment;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.User;
import web.entity.input.AppointmentQuery;
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
	@Deprecated
    public List<Appointment> list(int page, int size, User requiring) {
		permits.require(requiring, Permit.READ_APPOINTMENT);
		return this.appointmentsrepository.list(page, size);
	}
	
	@Override
	@Deprecated
    public List<Appointment> list(User requiring) {
		permits.require(requiring, Permit.READ_APPOINTMENT);
		return this.appointmentsrepository.list();
	}

	@Override
    public Appointment update(Appointment turno, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.UPDATE_APPOINTMENT);
		Optional<Appointment> search = findById(turno.getId());
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
	@Deprecated
	public void erase(Appointment turno) {
		appointmentsrepository.erase(turno);
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
	@Deprecated
	public List<Appointment> list(int page, int size, boolean includeInactives, User requiring) {
		permits.require(requiring, Permit.READ_APPOINTMENT);
		return appointmentsrepository.list(page, size, includeInactives);
	}
	
	
	
	/** # Deprecated methods **/
	
	@Override
	@Deprecated
    public void register(Appointment t) {
		this.appointmentsrepository.add(t);
	}
	
	@Override
	@Deprecated
    public Optional<Appointment> findById(int id) {
		return this.appointmentsrepository.findById(id);
	}
	
	@Override
	@Deprecated
    public List<Appointment> list(int page, int size) {
		return this.appointmentsrepository.list(page, size);
	}
	
	@Override
	@Deprecated
    public List<Appointment> list() {
		return this.appointmentsrepository.list();
	}

	@Override
	@Deprecated
    public void update(Appointment turno) throws NotFoundException {
		Optional<Appointment> search = findById(turno.getId());
		if(search.isEmpty()) throw new NotFoundException();
		Appointment original = search.get();
		if (turno.getDate() != null) original.setDate(turno.getDate());
        if (turno.getRemarks() != null) original.setRemarks(turno.getRemarks());
        if (turno.getStatus() != null) original.setStatus(turno.getStatus());
        if (turno.getAssignedDoctor() != null) original.setAssignedDoctor(turno.getAssignedDoctor());
        if (turno.getPatient() != null) original.setPatient(turno.getPatient());
		this.appointmentsrepository.update(turno);
	}

	@Override
	@Deprecated
	public int countPresencesBetween(Date d1, Date d2) {
		return this.appointmentsrepository.countPresencesBetween(d1, d2);
	}

	@Override
	@Deprecated
	public int countAbsencesBetween(Date d1, Date d2) {
		return this.appointmentsrepository.countAbsencesBetween(d1, d2);
	}

	@Override
	@Deprecated
	public void enable(int id) throws NotFoundException {
		appointmentsrepository.enable(id);
	}
	
	@Override
	@Deprecated
	public void disable(int id) throws NotFoundException {
		appointmentsrepository.disable(id);
	}

	@Override
	@Deprecated
	public Optional<Appointment> findById(int id, boolean includeInactives) {
		return appointmentsrepository.findById(id, includeInactives);
	}

	@Override
	@Deprecated
	public List<Appointment> list(int page, int size, boolean includeInactives) {
		return appointmentsrepository.list(page, size, includeInactives);
	}

	@Override
	public List<Appointment> search(AppointmentQuery patientQuery, User requiring) {
		permits.require(requiring, Permit.READ_APPOINTMENT);
		return appointmentsrepository.search(patientQuery);
	}
	

	public Appointment getById(int id, User requiring) throws NotAllowedException, NotFoundException {
		Optional<Appointment> opt = findById(id, requiring);
		if(opt.isEmpty()) throw new NotFoundException("Appointment not found. ");
		return opt.get();
	}
	
	
}
