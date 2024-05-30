package logicImpl;

import java.util.List;

import dao.IEspecialidadDAO;
import daoImpl.EspecialidadDAOImpl;
import entity.Especialidad;
import logic.IEspecialidadLogic;

import entity.Optional;

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
    public void update(Especialidad e) {
		repository.update(e);
	}
	
	@Override
    public void disable(Especialidad e) {
		return; // No hay campo "active" en Especialidad.
	}
	
	@Override
    public void enable(Especialidad e) {
		return; // No hay campo "active" en Especialidad.
	}
	
	@Override
    public void permanentlyDelete(Especialidad e) {
		repository.erase(e);
	}
	
	@Override
    public List<Especialidad> list(int page, int size) {
		return repository.list(page, size);
	}
	
	@Override
    public List<Especialidad> list() {
		return list(1, 15);
	}
	
}
