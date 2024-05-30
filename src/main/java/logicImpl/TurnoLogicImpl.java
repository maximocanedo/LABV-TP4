package logicImpl;

import java.util.Date;
import entity.Optional;
import java.util.List;

import dao.ITurnoDAO;
import daoImpl.TurnoDAOImpl;
import entity.*;
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
    public void disable(Turno t) {
		this.repository.erase(t);
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
    public void update(Turno turno) {
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
	
}
