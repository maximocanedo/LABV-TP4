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
	
	/* (non-Javadoc)
	 * @see logicImpl.ITurnoLogic#register(entity.Turno)
	 */
	@Override
	public void register(Turno t) {
		this.repository.add(t);
	}
	/* (non-Javadoc)
	 * @see logicImpl.ITurnoLogic#disable(entity.Turno)
	 */
	@Override
	public void disable(Turno t) {
		this.repository.erase(t);
	}
	/* (non-Javadoc)
	 * @see logicImpl.ITurnoLogic#getById(int)
	 */
	@Override
	public Optional<Turno> getById(int id) {
		return this.repository.getByid(id);
	}
	/* (non-Javadoc)
	 * @see logicImpl.ITurnoLogic#list(int, int)
	 */
	@Override
	public List<Turno> list(int page, int size) {
		return this.repository.list(page, size);
	}
	/* (non-Javadoc)
	 * @see logicImpl.ITurnoLogic#list()
	 */
	@Override
	public List<Turno> list() {
		return this.repository.list();
	}
	/* (non-Javadoc)
	 * @see logicImpl.ITurnoLogic#update(entity.Turno)
	 */
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
