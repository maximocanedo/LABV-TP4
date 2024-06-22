package web.logicImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IPatientDAO;
import web.entity.Optional;
import web.entity.Patient;
import web.entity.Permit;
import web.entity.User;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;
import web.logic.IPatientLogic;

@Component("patients")
public class PatientLogicImpl implements IPatientLogic {
	
	@Autowired
	private IPatientDAO patientsrepository;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	public PatientLogicImpl() {}
	
	@Override
	public void add(Patient paciente, User requiring) {
		permits.require(requiring, Permit.CREATE_PATIENT);
		patientsrepository.add(paciente);
	}
		
	@Override
	public Optional<Patient> findById(int id, User requiring) throws NotAllowedException {
		permits.require(requiring, Permit.READ_PATIENT_PERSONAL_DATA);
		return patientsrepository.findById(id);
	}
	
	@Override
    public void update(Patient paciente, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.UPDATE_PATIENT);
		Optional<Patient> search = findById(paciente.getId(), requiring);
		if(search.isEmpty()) throw new NotFoundException();
		Patient original = search.get();
		if (paciente.getName() != null) original.setName(paciente.getName());
        if (paciente.getSurname() != null) original.setSurname(paciente.getSurname());
        if (paciente.getDni() != 0) original.setDni(paciente.getDni());
        if (paciente.getPhone() != null) original.setPhone(paciente.getPhone());
        if (paciente.getAddress() != null) original.setAddress(paciente.getAddress());
        if (paciente.getLocalty() != null) original.setLocalty(paciente.getLocalty());
        if (paciente.getProvince() != null) original.setProvince(paciente.getProvince());
        if (paciente.getBirth() != null) original.setBirth(paciente.getBirth());
        if (paciente.getEmail() != null) original.setEmail(paciente.getEmail());
        patientsrepository.update(paciente);
	}

	@Override
	public List<Patient> list(int page, int size, User requiring) {
		permits.require(requiring, Permit.READ_PATIENT_PERSONAL_DATA);
		return patientsrepository.list(page, size);
	}
	
	@Override
    public List<Patient> list(User requiring) {
		permits.require(requiring, Permit.READ_PATIENT_PERSONAL_DATA);
		return list(1, 15);
	}

	@Override
	public Optional<Patient> findById(int id, boolean includeInactives, User requiring) {
		permits.require(requiring, Permit.READ_PATIENT_PERSONAL_DATA);
		return patientsrepository.findById(id, includeInactives);
	}

	@Override
	public List<Patient> list(int page, int size, boolean includeInactives, User requiring) {
		permits.require(requiring, Permit.READ_PATIENT_PERSONAL_DATA);
		return patientsrepository.list(page, size, includeInactives);
	}

	@Override
	public void disable(int id, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.DISABLE_PATIENT);
		patientsrepository.disable(id);
	}

	@Override
	public void enable(int id, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.ENABLE_PATIENT);
		patientsrepository.enable(id);		
	}	
	
	/** # Deprecated methods **/

	@Override
	@Deprecated
	public void add(Patient paciente) {
		patientsrepository.add(paciente);
	}

	@Override
	@Deprecated
	public Optional<Patient> findById(int id) {
		return patientsrepository.findById(id);
	}

	@Override
	@Deprecated
    public void update(Patient paciente) throws NotFoundException {
		Optional<Patient> search = findById(paciente.getId());
		if(search.isEmpty()) throw new NotFoundException();
		Patient original = search.get();
		if (paciente.getName() != null) original.setName(paciente.getName());
        if (paciente.getSurname() != null) original.setSurname(paciente.getSurname());
        if (paciente.getDni() != 0) original.setDni(paciente.getDni());
        if (paciente.getPhone() != null) original.setPhone(paciente.getPhone());
        if (paciente.getAddress() != null) original.setAddress(paciente.getAddress());
        if (paciente.getLocalty() != null) original.setLocalty(paciente.getLocalty());
        if (paciente.getProvince() != null) original.setProvince(paciente.getProvince());
        if (paciente.getBirth() != null) original.setBirth(paciente.getBirth());
        if (paciente.getEmail() != null) original.setEmail(paciente.getEmail());
        patientsrepository.update(paciente);
	}

	@Override
	@Deprecated
	public List<Patient> list(int page, int size) {
		return patientsrepository.list(page, size);
	}
	
	@Override
	@Deprecated
    public List<Patient> list() {
		return list(1, 15);
	}

	@Override
	@Deprecated
	public void erase(Patient paciente) {
		patientsrepository.erase(paciente);
		
	}

	@Override
	@Deprecated
	public Optional<Patient> findById(int id, boolean includeInactives) {
		return patientsrepository.findById(id, includeInactives);
	}

	@Override
	@Deprecated
	public List<Patient> list(int page, int size, boolean includeInactives) {
		return patientsrepository.list(page, size, includeInactives);
	}

	@Override
	@Deprecated
	public void disable(int id) throws NotFoundException {
		patientsrepository.disable(id);
	}

	@Override
	@Deprecated
	public void enable(int id) throws NotFoundException {
		patientsrepository.enable(id);		
	}	
	
	
}
