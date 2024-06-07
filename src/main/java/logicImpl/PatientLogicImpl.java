package logicImpl;

import java.util.List;
import entity.Optional;

import dao.IPatientDAO;
import daoImpl.PatientDAOImpl;
import entity.Patient;
import exceptions.NotFoundException;
import logic.IPatientLogic;

public class PatientLogicImpl implements IPatientLogic {
	
	private final IPatientDAO repository;
	
	public PatientLogicImpl() {
		repository = new PatientDAOImpl();
	}
	
	@Override
	public void add(Patient paciente) {
		repository.add(paciente);
	}
	
	@Override
	public Optional<Patient> findById(int id) {
		return repository.findById(id);
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
		repository.update(paciente);
	}
	
	@Override
	public List<Patient> list(int page, int size) {
		return repository.list(page, size);
	}
	
	@Override
    public List<Patient> list() {
		return list(1, 15);
	}

	@Override
	@Deprecated
	public void erase(Patient paciente) {
		repository.erase(paciente);
		
	}

	@Override
	public Optional<Patient> findById(int id, boolean includeInactives) {
		return repository.findById(id, includeInactives);
	}

	@Override
	public List<Patient> list(int page, int size, boolean includeInactives) {
		return repository.list(page, size, includeInactives);
	}

	@Override
	public void disable(int id) throws NotFoundException {
		repository.disable(id);
	}

	@Override
	public void enable(int id) throws NotFoundException {
		repository.enable(id);		
	}	
}