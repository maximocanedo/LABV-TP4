package logicImpl;

import java.util.Date;
import java.util.List;

import dao.ITurnoDAO;
import daoImpl.TurnoDAOImpl;
import entity.*;
import exceptions.NotFoundException;
import logic.ITurnoLogic;

public class TurnoLogicImpl implements ITurnoLogic {
	private ITurnoDAO repository;
	
	public TurnoLogicImpl() {
		this.repository = new TurnoDAOImpl();
	}
	
	@Override
    public void register(Turno t) {
		this.repository.add(t);
	}
	
	@Override
    public Optional<Turno> findById(int id) {
		return this.repository.findById(id);
	}
	
	@Override
    public List<Turno> list(int page, int size) {
		return this.repository.list(page, size);
	}
	
	@Override
    public List<Turno> list() {
		return this.repository.list();
	}

	@Override
    public void update(Turno turno) throws NotFoundException {
		Optional<Turno> search = findById(turno.getId());
		if(search.isEmpty()) throw new NotFoundException();
		Turno original = search.get();
		if (turno.getFecha() != null) original.setFecha(turno.getFecha());
        if (turno.getObservacion() != null) original.setObservacion(turno.getObservacion());
        if (turno.getEstado() != null) original.setEstado(turno.getEstado());
        if (turno.getMedico() != null) original.setMedico(turno.getMedico());
        if (turno.getPaciente() != null) original.setPaciente(turno.getPaciente());
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
	public void erase(Turno turno) {
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
	public Optional<Turno> findById(int id, boolean includeInactives) {
		return repository.findById(id, includeInactives);
	}

	@Override
	public List<Turno> list(int page, int size, boolean includeInactives) {
		return repository.list(page, size, includeInactives);
	}
	
}
