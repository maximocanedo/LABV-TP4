package logicImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Optional;
import exceptions.NotFoundException;
import dao.IDoctorDAO;
import daoImpl.DoctorDAOImpl;
import entity.Doctor;
import logic.IDoctorLogic;
@Component
public class DoctorLogicImpl implements IDoctorLogic {

	@Autowired
    private IDoctorDAO repository;

    public DoctorLogicImpl() {}

    @Override
    public void add(Doctor medico) {
        repository.add(medico);
    }

    @Override
    public Optional<Doctor> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Doctor> list(int page, int size) {
        return repository.list(page, size);
    }

    @Override
    public List<Doctor> list() {
        return list(1, 15);
    }

	@Override
	public List<Object[]> listOnlyFileNumbersAndNames() {
		return repository.listOnlyFileNumbersAndNames();
	}
	
	@Override
    public List<Integer> listOnlyFileNumbers(){
		return repository.listOnlyFileNumbers();
	}
	
	@Override
    public Doctor findDoctorWithHighestFileNumber() {
		return repository.findDoctorWithHighestFileNumber();
	}
	
	@Override
    public List<Doctor> listOrderByFileDescending(int page, int size) {
		return repository.listOrderByFileDescending(page, size);
	}
	
	@Override
    public List<Doctor> listOrderByFileDescending() {
		return this.listOrderByFileDescending(1, 10);
	}
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDate(int legajo, LocalDate fecha) {
        return repository.getAppointmentsByDoctorAndDate(legajo, fecha);
    }
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDateRange(int legajo, LocalDate fechaInicio, LocalDate fechaFin) {
        return repository.getAppointmentsByDoctorAndDateRange(legajo, fechaInicio, fechaFin);
    }

	@Override
	public Optional<Doctor> findByFile(int file) {
		return repository.findByFile(file);
	}

	@Override
	public Optional<Doctor> findById(int id, boolean includeInactive) {
		return repository.findById(id, includeInactive);
	}

	@Override
	public List<Doctor> list(int page, int size, boolean includeInactive) {
		return repository.list(page, size, includeInactive);
	}

	@Override
	public void update(Doctor medico) throws NotFoundException {
    	Optional<Doctor> search = findById(medico.getId());
    	if(search.isEmpty()) throw new NotFoundException();
    	Doctor original = search.get();
    	if (medico.getName() != null) original.setName(medico.getName());
        if (medico.getSurname() != null) original.setSurname(medico.getSurname());
        if (medico.getSex() != null) original.setSex(medico.getSex());
        if (medico.getBirth() != null) original.setBirth(medico.getBirth());
        if (medico.getAddress() != null) original.setAddress(medico.getAddress());
        if (medico.getLocalty() != null) original.setLocalty(medico.getLocalty());
        if (medico.getEmail() != null) original.setEmail(medico.getEmail());
        if (medico.getPhone() != null) original.setPhone(medico.getPhone());
        if (medico.getSpecialty() != null) original.setSpecialty(medico.getSpecialty());
        if (medico.getUser() != null) original.setUser(medico.getUser());
		repository.update(original);
	}

	@Override
	@Deprecated
	public void erase(Doctor medico) {
		repository.erase(medico);
	}

	@Override
	public void disable(int id) throws NotFoundException {
		repository.disable(id);
		
	}

	@Override
	public void enable(int id) throws NotFoundException {
		repository.disable(id);
	}
    
}
