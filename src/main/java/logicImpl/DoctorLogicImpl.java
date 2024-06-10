package logicImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import daoImpl.DoctorDAOImpl;
import entity.Doctor;
import entity.Optional;
import exceptions.NotFoundException;
import logic.IDoctorLogic;

public class DoctorLogicImpl implements IDoctorLogic {
	
	@Autowired
    private DoctorDAOImpl doctorsrepository;

    public DoctorLogicImpl() { }

    @Override
    public void add(Doctor medico) {
        doctorsrepository.add(medico);
    }

    @Override
    public Optional<Doctor> findById(int id) {
        return doctorsrepository.findById(id);
    }

    @Override
    public List<Doctor> list(int page, int size) {
        return doctorsrepository.list(page, size);
    }

    @Override
    public List<Doctor> list() {
        return list(1, 15);
    }

	@Override
	public List<Object[]> listOnlyFileNumbersAndNames() {
		return doctorsrepository.listOnlyFileNumbersAndNames();
	}
	
	@Override
    public List<Integer> listOnlyFileNumbers(){
		return doctorsrepository.listOnlyFileNumbers();
	}
	
	@Override
    public Doctor findDoctorWithHighestFileNumber() {
		return doctorsrepository.findDoctorWithHighestFileNumber();
	}
	
	@Override
    public List<Doctor> listOrderByFileDescending(int page, int size) {
		return doctorsrepository.listOrderByFileDescending(page, size);
	}
	
	@Override
    public List<Doctor> listOrderByFileDescending() {
		return this.listOrderByFileDescending(1, 10);
	}
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDate(int legajo, LocalDate fecha) {
        return doctorsrepository.getAppointmentsByDoctorAndDate(legajo, fecha);
    }
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDateRange(int legajo, LocalDate fechaInicio, LocalDate fechaFin) {
        return doctorsrepository.getAppointmentsByDoctorAndDateRange(legajo, fechaInicio, fechaFin);
    }

	@Override
	public Optional<Doctor> findByFile(int file) {
		return doctorsrepository.findByFile(file);
	}

	@Override
	public Optional<Doctor> findById(int id, boolean includeInactive) {
		return doctorsrepository.findById(id, includeInactive);
	}

	@Override
	public List<Doctor> list(int page, int size, boolean includeInactive) {
		return doctorsrepository.list(page, size, includeInactive);
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
		doctorsrepository.update(original);
	}

	@Override
	@Deprecated
	public void erase(Doctor medico) {
		doctorsrepository.erase(medico);
	}

	@Override
	public void disable(int id) throws NotFoundException {
		doctorsrepository.disable(id);
		
	}

	@Override
	public void enable(int id) throws NotFoundException {
		doctorsrepository.disable(id);
	}
    
}
