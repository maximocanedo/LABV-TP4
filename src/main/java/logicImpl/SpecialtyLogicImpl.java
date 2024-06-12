package logicImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.ISpecialtyDAO;
import entity.Optional;
import entity.Specialty;
import exceptions.NotFoundException;
import logic.ISpecialtyLogic;

@Component
public class SpecialtyLogicImpl implements ISpecialtyLogic {
	
	@Autowired
	private ISpecialtyDAO specialtiesrepository;
	
	public SpecialtyLogicImpl() {}
		
	@Override
    public void add(Specialty e) {
		specialtiesrepository.add(e);
	}

	@Override
    public Optional<Specialty> findById(int id) {
		return specialtiesrepository.findById(id);
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
		specialtiesrepository.update(original);
	}
	
	@Override
    public void disable(int id) throws NotFoundException {
		specialtiesrepository.disable(id);
	}
	
	@Override
    public void enable(int id) throws NotFoundException {
		specialtiesrepository.enable(id);
	}
	
	@Override
	public List<Specialty> list(int page, int size, boolean includeInactiveRecords) {
		return specialtiesrepository.list(page, size, includeInactiveRecords);
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
