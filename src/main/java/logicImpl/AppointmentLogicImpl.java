package logicImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.IAppointmentDAO;
import entity.*;
import exceptions.NotFoundException;
import logic.IAppointmentLogic;

@Component
public class AppointmentLogicImpl implements IAppointmentLogic {
	
	@Autowired
	private IAppointmentDAO appointmentsrepository;

	public AppointmentLogicImpl() {}
	
	@Override
    public void register(Appointment t) {
		this.appointmentsrepository.add(t);
	}
	
	@Override
    public Optional<Appointment> findById(int id) {
		return this.appointmentsrepository.findById(id);
	}
	
	@Override
    public List<Appointment> list(int page, int size) {
		return this.appointmentsrepository.list(page, size);
	}
	
	@Override
    public List<Appointment> list() {
		return this.appointmentsrepository.list();
	}

	@Override
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
	public int countPresencesBetween(Date d1, Date d2) {
		return this.appointmentsrepository.countPresencesBetween(d1, d2);
	}

	@Override
	public int countAbsencesBetween(Date d1, Date d2) {
		return this.appointmentsrepository.countAbsencesBetween(d1, d2);
	}

	@Override
	@Deprecated
	public void erase(Appointment turno) {
		appointmentsrepository.erase(turno);
	}

	@Override
	public void enable(int id) throws NotFoundException {
		appointmentsrepository.enable(id);
	}
	
	@Override
	public void disable(int id) throws NotFoundException {
		appointmentsrepository.disable(id);
	}

	@Override
	public Optional<Appointment> findById(int id, boolean includeInactives) {
		return appointmentsrepository.findById(id, includeInactives);
	}

	@Override
	public List<Appointment> list(int page, int size, boolean includeInactives) {
		return appointmentsrepository.list(page, size, includeInactives);
	}
	
}
