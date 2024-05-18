package logicImpl;

import java.util.List;

import dao.IEspecialidadDAO;
import daoImpl.EspecialidadDAOImpl;
import entity.Especialidad;
import logic.IEspecialidadLogic;

public class EspecialidadLogicImpl implements IEspecialidadLogic {
	
	private final IEspecialidadDAO repository;
	
	public EspecialidadLogicImpl() {
		repository = new EspecialidadDAOImpl();
	}
	
	
	/* (non-Javadoc)
	 * @see logicImpl.IEspecialidadLogic#add(entity.Especialidad)
	 */
	@Override
	public void add(Especialidad e) {
		repository.add(e);
	}
	/* (non-Javadoc)
	 * @see logicImpl.IEspecialidadLogic#findById(int)
	 */
	@Override
	public Especialidad findById(int id) {
		return repository.getById(id);
	}
	
	/* (non-Javadoc)
	 * @see logicImpl.IEspecialidadLogic#update(entity.Especialidad)
	 */
	@Override
	public void update(Especialidad e) {
		repository.update(e);
	}
	
	/* (non-Javadoc)
	 * @see logicImpl.IEspecialidadLogic#disable(entity.Especialidad)
	 */
	@Override
	public void disable(Especialidad e) {
		return; // No hay campo "active" en Especialidad.
	}
	
	/* (non-Javadoc)
	 * @see logicImpl.IEspecialidadLogic#enable(entity.Especialidad)
	 */
	@Override
	public void enable(Especialidad e) {
		return; // No hay campo "active" en Especialidad.
	}
	
	/* (non-Javadoc)
	 * @see logicImpl.IEspecialidadLogic#permanentlyDelete(entity.Especialidad)
	 */
	@Override
	public void permanentlyDelete(Especialidad e) {
		repository.erase(e);
	}
	
	/* (non-Javadoc)
	 * @see logicImpl.IEspecialidadLogic#list(int, int)
	 */
	@Override
	public List<Especialidad> list(int page, int size) {
		return repository.list(page, size);
	}
	
	/* (non-Javadoc)
	 * @see logicImpl.IEspecialidadLogic#list()
	 */
	@Override
	public List<Especialidad> list() {
		return list(1, 15);
	}
	
}
