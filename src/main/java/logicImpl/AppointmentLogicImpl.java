package logicImpl;

import java.util.Date;
import java.util.List;

import dao.IAppointmentDAO;
import daoImpl.AppointmentDAOImpl;
import entity.*;
import exceptions.NotFoundException;
import logic.IAppointmentLogic;

public class AppointmentLogicImpl implements IAppointmentLogic {
	private IAppointmentDAO repository;
	
	public AppointmentLogicImpl() {
		this.repository = new AppointmentDAOImpl();
	}
	
	@Override
    public void register(Appointment t) {
		this.repository.add(t);
	}
	
	@Override
    public Optional<Appointment> findById(int id) {
		return this.repository.findById(id);
	}
	
	@Override
    public List<Appointment> list(int page, int size) {
		return this.repository.list(page, size);
	}
	
	@Override
    public List<Appointment> list() {
		return this.repository.list();
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
		this.repository.update(turno);
	}

	@Override
	public int countPresencesBetween(Date d1, Date d2) {
		return this.repository.countPresencesBetween(d1, d2);
	}

	@Override
	public int countAbsencesBetween(Date d1, Date d2) {
		return this.repository.countAbsencesBetween(d1, d2);
	}

	@Override
	@Deprecated
	public void erase(Appointment turno) {
		repository.erase(turno);
	}

	@Override
	public void enable(int id) throws NotFoundException {
		repository.enable(id);
	}
	
	@Override
	public void disable(int id) throws NotFoundException {
		repository.disable(id);
	}

	@Override
	public Optional<Appointment> findById(int id, boolean includeInactives) {
		return repository.findById(id, includeInactives);
	}

	@Override
	public List<Appointment> list(int page, int size, boolean includeInactives) {
		return repository.list(page, size, includeInactives);
	}
	
}
