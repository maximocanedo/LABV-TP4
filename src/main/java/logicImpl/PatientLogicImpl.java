package logicImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Optional;

import dao.IPatientDAO;
import entity.Patient;
import exceptions.NotFoundException;
import logic.IPatientLogic;

@Component
public class PatientLogicImpl implements IPatientLogic {
	
	@Autowired
	private IPatientDAO patientsrepository;
	
	public PatientLogicImpl() {}
	
	@Override
	public void add(Patient paciente) {
		patientsrepository.add(paciente);
	}
	
	@Override
	public Optional<Patient> findById(int id) {
		return patientsrepository.findById(id);
	}
	
	@Override
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
	public List<Patient> list(int page, int size) {
		return patientsrepository.list(page, size);
	}
	
	@Override
    public List<Patient> list() {
		return list(1, 15);
	}

	@Override
	@Deprecated
	public void erase(Patient paciente) {
		patientsrepository.erase(paciente);
		
	}

	@Override
	public Optional<Patient> findById(int id, boolean includeInactives) {
		return patientsrepository.findById(id, includeInactives);
	}

	@Override
	public List<Patient> list(int page, int size, boolean includeInactives) {
		return patientsrepository.list(page, size, includeInactives);
	}

	@Override
	public void disable(int id) throws NotFoundException {
		patientsrepository.disable(id);
	}

	@Override
	public void enable(int id) throws NotFoundException {
		patientsrepository.enable(id);		
	}	
}
