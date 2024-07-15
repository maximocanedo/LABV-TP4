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
import web.logic.validator.SpecialtyValidator;

@Component("specialties")
public class SpecialtyLogicImpl implements ISpecialtyLogic {
	
	@Autowired
	private ISpecialtyDAO specialtiesRepository;
	
	@Autowired
	private SpecialtyValidator specialtyValidator;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	public SpecialtyLogicImpl() {}
	
	@Override
    public Specialty add(Specialty e, User requiring) {
		permits.require(requiring, Permit.CREATE_SPECIALTY);
		e.setName(specialtyValidator.name(e.getName()));
		e.setDescription(specialtyValidator.description(e.getDescription()));
		return specialtiesRepository.add(e);
	}

	public Optional<Specialty> findById(int id, boolean includeInactive, User requiring) {
		if(includeInactive) {
			includeInactive = permits.check(requiring, Permit.ENABLE_SPECIALTY);
		}
		return specialtiesRepository.findById(id, includeInactive);
	}
	
	@Override
    public Specialty update(Specialty record, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.UPDATE_SPECIALTY);
    	Optional<Specialty> file = findById(record.getId(), false, requiring);
    	if(file.isEmpty()) throw new NotFoundException("Specialty not found");
        Specialty original = file.get();
        if(record.getName() != null)
        	original.setName(specialtyValidator.name(record.getName()));
        if(record.getDescription() != null)
        	original.setDescription(specialtyValidator.description(record.getDescription()));
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
		return specialtiesRepository.search(query);
	}
	

	@Override
	public Optional<Specialty> findById(int id) {
		return findById(id, false, null);
	}

}
