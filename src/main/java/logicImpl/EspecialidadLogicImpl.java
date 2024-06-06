package logicImpl;

import java.util.List;

import dao.IEspecialidadDAO;
import daoImpl.EspecialidadDAOImpl;
import entity.Especialidad;
import logic.IEspecialidadLogic;

import entity.Optional;
import exceptions.NotFoundException;

public class EspecialidadLogicImpl implements IEspecialidadLogic {
	
	private final IEspecialidadDAO repository;
	
	public EspecialidadLogicImpl() {
		repository = new EspecialidadDAOImpl();
	}
		
	@Override
    public void add(Especialidad e) {
		repository.add(e);
	}

	@Override
    public Optional<Especialidad> findById(int id) {
		return repository.findById(id);
	}
	
	@Override
    public void update(Especialidad e) throws NotFoundException {
		repository.update(e);
	}
	
	@Override
    public void disable(int id) throws NotFoundException {
		repository.disable(id);
	}
	
	@Override
    public void enable(int id) throws NotFoundException {
		repository.enable(id);
	}
	
	@Override
	public List<Especialidad> list(int page, int size, boolean includeInactiveRecords) {
		return repository.list(page, size, includeInactiveRecords);
	}
	
	@Override
    public List<Especialidad> list(int page, int size) {
		return list(page, size, false);
	}
	
	@Override
    public List<Especialidad> list(boolean includeInactiveRecords) {
		return list(1, 15, includeInactiveRecords);
	}
	
	@Override
    public List<Especialidad> list() {
		return list(1, 15, false);
	}

	@Override
	@Deprecated
	public void permanentlyDelete(Especialidad especialidad) {
		return;
	}
	
}
