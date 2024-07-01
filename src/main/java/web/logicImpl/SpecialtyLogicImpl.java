package web.logicImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.ISpecialtyDAO;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.Specialty;
import web.entity.User;
import web.entity.input.SpecialtyQuery;
import web.exceptions.NotFoundException;
import web.logic.ISpecialtyLogic;

@Component("specialties")
public class SpecialtyLogicImpl implements ISpecialtyLogic {
	
	@Autowired
	private ISpecialtyDAO specialtiesRepository;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	public SpecialtyLogicImpl() {}
	
	@Override
    public Specialty add(Specialty e, User requiring) {
		permits.require(requiring, Permit.CREATE_SPECIALTY);
		return specialtiesRepository.add(e);
	}

	public Optional<Specialty> findById(int id, boolean includeInactive, User requiring) {
    	//permits.require(requiring, Permit.READ_SPECIALTY); //
		return specialtiesRepository.findById(id, includeInactive);
	}
	
	@Override
    public Specialty update(Specialty record, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.UPDATE_SPECIALTY);
    	Optional<Specialty> file = findById(record.getId(), false, requiring);
    	if(file.isEmpty()) throw new NotFoundException("Specialty not found");
        Specialty original = file.get();
        if(record.getName() != null)
        	original.setName(record.getName());
        if(record.getDescription() != null)
        	original.setDescription(record.getDescription());
		return specialtiesRepository.update(original);
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
	
	@Override
	public List<Specialty> search(SpecialtyQuery query, User requiring) {
		// permits.require(requiring, Permit.SEARCH_SPECIALTY); //
		return specialtiesRepository.search(query);
	}
	
	@Override
	@Deprecated
	public List<Specialty> list(int page, int size, boolean includeInactiveRecords, User requiring) {
		permits.require(requiring, Permit.READ_DISABLED_SPECIALTY_RECORDS);
		return specialtiesRepository.list(page, size, includeInactiveRecords);
	}

	@Override
	@Deprecated
	public Optional<Specialty> findById(int id) {
		return null;
	}

	@Override
	@Deprecated
	public void permanentlyDelete(Specialty specialty) {
		
	}

	@Override
	@Deprecated
	public List<Specialty> list(int page, int size) {
		return null;
	}

	@Override
	@Deprecated
	public List<Specialty> list(boolean includeInactiveRecords, User requiring) {
		return null;
	}

	@Override
	@Deprecated
	public List<Specialty> list() {
		return null;
	}

	@Override
	@Deprecated
	public void add(Specialty specialty) {
		
	}

	@Override
	@Deprecated
	public void update(Specialty specialty) throws NotFoundException {
		
	}

	@Override
	@Deprecated
	public void disable(int id) throws NotFoundException {
		
	}

	@Override
	@Deprecated
	public void enable(int id) throws NotFoundException {
		
	}

	@Override
	@Deprecated
	public List<Specialty> list(int page, int size, boolean includeInactiveRecords) {
		return null;
	}

	@Override
	@Deprecated
	public List<Specialty> list(boolean includeInactiveRecords) {
		return null;
	}
}
