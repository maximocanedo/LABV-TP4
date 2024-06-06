package logicImpl;

import java.util.List;

import dao.ISpecialtyDAO;
import daoImpl.SpecialtyDAOImpl;
import entity.Specialty;
import logic.ISpecialtyLogic;

import entity.Optional;
import exceptions.NotFoundException;

public class SpecialtyLogicImpl implements ISpecialtyLogic {
	
	private final ISpecialtyDAO repository;
	
	public SpecialtyLogicImpl() {
		repository = new SpecialtyDAOImpl();
	}
		
	@Override
    public void add(Specialty e) {
		repository.add(e);
	}

	@Override
    public Optional<Specialty> findById(int id) {
		return repository.findById(id);
	}
	
	@Override
    public void update(Specialty record) throws NotFoundException {
    	Optional<Specialty> file = findById(record.getId());
    	if(file.isEmpty()) throw new NotFoundException();
        Specialty original = file.get();
        if(record.getName() != null)
        	original.setName(record.getName());
        if(record.getDescription() != null)
        	original.setDescription(record.getDescription());
		repository.update(original);
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
	public List<Specialty> list(int page, int size, boolean includeInactiveRecords) {
		return repository.list(page, size, includeInactiveRecords);
	}
	
	@Override
    public List<Specialty> list(int page, int size) {
		return list(page, size, false);
	}
	
	@Override
    public List<Specialty> list(boolean includeInactiveRecords) {
		return list(1, 15, includeInactiveRecords);
	}
	
	@Override
    public List<Specialty> list() {
		return list(1, 15, false);
	}

	@Override
	@Deprecated
	public void permanentlyDelete(Specialty especialidad) {
		return;
	}
	
}
