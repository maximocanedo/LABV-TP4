package web.logicImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IPatientDAO;
import web.entity.IPatient;
import web.entity.Optional;
import web.entity.Patient;
import web.entity.Permit;
import web.entity.User;
import web.entity.input.PatientQuery;
import web.entity.view.PatientCommunicationView;
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
	public List<PatientCommunicationView> search(PatientQuery q, User requiring) {
		permits.require(requiring, Permit.READ_USER_DATA);
		return patientsrepository.search(q);
	}
	
	@Override
	public Patient add(Patient paciente, User requiring) {
		permits.require(requiring, Permit.CREATE_PATIENT);
		paciente.setActive(true);
		return patientsrepository.add(paciente);
	}
		
	@Override
	public Optional<Patient> findById(int id, User requiring) throws NotAllowedException {
		requiring = permits.require(requiring, Permit.READ_PATIENT_PERSONAL_DATA);
		boolean includeInactives = requiring.can(Permit.ENABLE_PATIENT);
		return patientsrepository.findById(id, includeInactives);
	}
	
	@Override
	public IPatient getById(int id, boolean includeInactives, User requiring) throws NotAllowedException, NotFoundException {
		requiring = permits.require(requiring, Permit.READ_PATIENT_PERSONAL_DATA, Permit.READ_USER_DATA);
		boolean f = includeInactives && requiring.can(Permit.ENABLE_PATIENT);
		if(!requiring.can(Permit.READ_PATIENT_PERSONAL_DATA)) {
			Optional<PatientCommunicationView> x = patientsrepository.findComViewById(id, f);
			if(x.isEmpty()) throw new NotFoundException("Patient not found. ");
			return x.get();
		}
		Optional<Patient> x = patientsrepository.findById(id, f);
		if(x.isEmpty()) throw new NotFoundException("Patient not found. ");
		return x.get();
	}
	
	
	@Override
	public IPatient getById(int id, User requiring) throws NotAllowedException, NotFoundException {
		requiring = permits.require(requiring, Permit.READ_PATIENT_PERSONAL_DATA, Permit.READ_USER_DATA);
		boolean includeInactives = requiring.can(Permit.ENABLE_PATIENT);
		if(!requiring.can(Permit.READ_PATIENT_PERSONAL_DATA)) {
			Optional<PatientCommunicationView> x = patientsrepository.findComViewById(id, includeInactives);
			if(x.isEmpty()) throw new NotFoundException("Patient not found. ");
			return x.get();
		}
		Optional<Patient> x = patientsrepository.findById(id, includeInactives);
		if(x.isEmpty()) throw new NotFoundException("Patient not found. ");
		return x.get();
	}
	
	@Override
    public Patient update(Patient paciente, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.UPDATE_PATIENT);
		Optional<Patient> search = findById(paciente.getId(), requiring);
		if(search.isEmpty()) throw new NotFoundException();
		Patient original = search.get();
		if (paciente.getName() != null) original.setName(paciente.getName());
        if (paciente.getSurname() != null) original.setSurname(paciente.getSurname());
        if (paciente.getDni() != null) original.setDni(paciente.getDni());
        if (paciente.getPhone() != null) original.setPhone(paciente.getPhone());
        if (paciente.getAddress() != null) original.setAddress(paciente.getAddress());
        if (paciente.getLocalty() != null) original.setLocalty(paciente.getLocalty());
        if (paciente.getProvince() != null) original.setProvince(paciente.getProvince());
        if (paciente.getBirth() != null) original.setBirth(paciente.getBirth());
        if (paciente.getEmail() != null) original.setEmail(paciente.getEmail());
        return patientsrepository.update(original);
	}
	@Override
	public Optional<Patient> findById(int id, boolean includeInactives, User requiring) {
		permits.require(requiring, Permit.READ_PATIENT_PERSONAL_DATA);
		boolean f = includeInactives && requiring.can(Permit.ENABLE_PATIENT);
		return patientsrepository.findById(id, f);
	}

	@Override
	public void disable(int id, User requiring) throws NotFoundException {
		permits.require(requiring, Permit.DISABLE_PATIENT);
		patientsrepository.disable(id);
	}

}
