package web.logicImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.ISpecialtyDAO;
import web.entity.Specialty;
import web.entity.User;
import web.entity.Doctor;
import web.entity.Optional;
import web.entity.Permit;
import web.exceptions.NotFoundException;
import web.logic.ISpecialtyLogic;
import web.entity.input.SpecialtyQuery;

@Component("specialties")
public class SpecialtyLogicImpl implements ISpecialtyLogic {
	
	@Autowired
	private ISpecialtyDAO specialtiesRepository;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	public SpecialtyLogicImpl() {}
	
	@Override
    public void add(Specialty e, User requiring) {
		permits.require(requiring, Permit.CREATE_SPECIALTY);
		specialtiesRepository.add(e);
	}

	public Optional<Specialty> findById(int id, boolean includeInactive, User requiring) {
    	permits.require(requiring, Permit.READ_SPECIALTY);
		return specialtiesRepository.findById(id, includeInactive);
	}
	
	@Override
    public void update(Specialty record, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.UPDATE_SPECIALTY);
    	Optional<Specialty> file = findById(record.getId(), false, requiring);
    	if(file.isEmpty()) throw new NotFoundException("Specialty not found");
        Specialty original = file.get();
        if(record.getName() != null)
        	original.setName(record.getName());
        if(record.getDescription() != null)
        	original.setDescription(record.getDescription());
		specialtiesRepository.update(original);
	}
	
	@Override
    public void disable(int id, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.DISABLE_SPECIALTY);
		Optional<Specialty> specialty = specialtiesRepository.findById(id);
		if(specialty.isEmpty()) throw new NotFoundException("Specialty not found");
		specialtiesRepository.disable(id);
	}
	
	@Override
    public void enable(int id, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.ENABLE_SPECIALTY);
		Optional<Specialty> specialty = specialtiesRepository.findById(id);
		if(specialty.isEmpty()) throw new NotFoundException("Specialty not found");
		specialtiesRepository.enable(id);
	}
	
	public List<Specialty> search(SpecialtyQuery query, User requiring) {
		permits.require(requiring, Permit.SEARCH_SPECIALTY);
		return specialtiesRepository.search(query);
	}
	
	@Override
	public List<Specialty> list(int page, int size, boolean includeInactiveRecords, User requiring) {
		permits.require(requiring, Permit.READ_DISABLED_SPECIALTY_RECORDS);
		return specialtiesRepository.list(page, size, includeInactiveRecords);
	}

	@Override
	public Optional<Specialty> findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void permanentlyDelete(Specialty specialty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Specialty> list(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Specialty> list(boolean includeInactiveRecords, User requiring) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Specialty> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Specialty specialty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Specialty specialty) throws NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable(int id) throws NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable(int id) throws NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Specialty> list(int page, int size, boolean includeInactiveRecords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Specialty> list(boolean includeInactiveRecords) {
		// TODO Auto-generated method stub
		return null;
	}
}
