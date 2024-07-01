package web.logicImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IDoctorDAO;
import web.entity.Doctor;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.User;
import web.entity.input.DoctorQuery;
import web.entity.view.DoctorMinimalView;
import web.exceptions.NotFoundException;
import web.logic.IDoctorLogic;

@Component("doctors")
public class DoctorLogicImpl implements IDoctorLogic {

	@Autowired
    private IDoctorDAO doctorsrepository;
	
	@Autowired
	private UserPermitLogicImpl permits;

    public DoctorLogicImpl() {}

    @Override
    public Doctor add(Doctor medico, User requiring) {
    	permits.require(requiring, Permit.CREATE_DOCTOR);
        return doctorsrepository.add(medico);
    }

    @Override
    public Optional<Doctor> findById(int id, User requiring) {
    	requiring = permits.require(requiring, Permit.READ_APPOINTMENT, Permit.READ_DOCTOR);
        return doctorsrepository.findById(id);
    }
    
    public List<DoctorMinimalView> search(DoctorQuery query, User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
    	return doctorsrepository.search(query);
    }

    @Override
    @Deprecated
    public List<Doctor> list(int page, int size, User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
        return doctorsrepository.list(page, size);
    }

    @Override
    @Deprecated
    public List<Doctor> list(User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
        return list(1, 15);
    }

	@Override
	public List<Object[]> listOnlyFileNumbersAndNames(User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
		return doctorsrepository.listOnlyFileNumbersAndNames();
	}
	
	@Override
    public List<Integer> listOnlyFileNumbers(User requiring){
    	permits.require(requiring, Permit.READ_DOCTOR);
		return doctorsrepository.listOnlyFileNumbers();
	}
	
	@Override
    public Doctor findDoctorWithHighestFileNumber(User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
		return doctorsrepository.findDoctorWithHighestFileNumber();
	}
	
	@Override
    public List<Doctor> listOrderByFileDescending(int page, int size, User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
		return doctorsrepository.listOrderByFileDescending(page, size);
	}
	
	@Override
    public List<Doctor> listOrderByFileDescending(User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
		return this.listOrderByFileDescending(1, 10, requiring);
	}
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDate(int legajo, LocalDate fecha, User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR_APPOINTMENTS);
        return doctorsrepository.getAppointmentsByDoctorAndDate(legajo, fecha);
    }
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDateRange(int legajo, LocalDate fechaInicio, LocalDate fechaFin, User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR_APPOINTMENTS);
        return doctorsrepository.getAppointmentsByDoctorAndDateRange(legajo, fechaInicio, fechaFin);
    }

	@Override
	public Optional<Doctor> findByFile(int file, User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
		return doctorsrepository.findByFile(file);
	}

	@Override
	public Optional<Doctor> findById(int id, boolean includeInactive, User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
		return doctorsrepository.findById(id, includeInactive);
	}

	@Override
    @Deprecated
	public List<Doctor> list(int page, int size, boolean includeInactive, User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR);
		return doctorsrepository.list(page, size, includeInactive);
	}

	@Override
	public Doctor update(Doctor medico, User requiring) throws NotFoundException {
    	permits.require(requiring, Permit.UPDATE_DOCTOR_PERSONAL_DATA);
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
		return doctorsrepository.update(original);
	}

	@Override
	@Deprecated
	public void erase(Doctor medico) {
		doctorsrepository.erase(medico);
	}

	@Override
	public void disable(int id, User requiring) throws NotFoundException {
    	permits.require(requiring, Permit.DISABLE_DOCTOR);
		doctorsrepository.disable(id);
		
	}

	@Override
	public void enable(int id, User requiring) throws NotFoundException {
    	permits.require(requiring, Permit.ENABLE_DOCTOR);
		doctorsrepository.disable(id);
	}
	
	/** # Deprecated methods **/
    
    @Override
    @Deprecated
    public void add(Doctor medico) {
        doctorsrepository.add(medico);
    }

    @Override
    @Deprecated
    public Optional<Doctor> findById(int id) {
        return doctorsrepository.findById(id);
    }

    @Override
    @Deprecated
    public List<Doctor> list(int page, int size) {
        return doctorsrepository.list(page, size);
    }

    @Override
    @Deprecated
    public List<Doctor> list() {
        return list(1, 15);
    }

	@Override
    @Deprecated
	public List<Object[]> listOnlyFileNumbersAndNames() {
		return doctorsrepository.listOnlyFileNumbersAndNames();
	}
	
	@Override
    @Deprecated
    public List<Integer> listOnlyFileNumbers(){
		return doctorsrepository.listOnlyFileNumbers();
	}
	
	@Override
    @Deprecated
    public Doctor findDoctorWithHighestFileNumber() {
		return doctorsrepository.findDoctorWithHighestFileNumber();
	}
	
	@Override
    @Deprecated
    public List<Doctor> listOrderByFileDescending(int page, int size) {
		return doctorsrepository.listOrderByFileDescending(page, size);
	}
	
	@Override
    @Deprecated
    public List<Doctor> listOrderByFileDescending() {
		return this.listOrderByFileDescending(1, 10);
	}
	
	@Override
    @Deprecated
    public List<Object[]> getAppointmentsByDoctorAndDate(int legajo, LocalDate fecha) {
        return doctorsrepository.getAppointmentsByDoctorAndDate(legajo, fecha);
    }
	
	@Override
    @Deprecated
    public List<Object[]> getAppointmentsByDoctorAndDateRange(int legajo, LocalDate fechaInicio, LocalDate fechaFin) {
        return doctorsrepository.getAppointmentsByDoctorAndDateRange(legajo, fechaInicio, fechaFin);
    }

	@Override
    @Deprecated
	public Optional<Doctor> findByFile(int file) {
		return doctorsrepository.findByFile(file);
	}

	@Override
    @Deprecated
	public Optional<Doctor> findById(int id, boolean includeInactive) {
		return doctorsrepository.findById(id, includeInactive);
	}

	@Override
    @Deprecated
	public List<Doctor> list(int page, int size, boolean includeInactive) {
		return doctorsrepository.list(page, size, includeInactive);
	}

	@Override
    @Deprecated
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
	public void disable(int id) throws NotFoundException {
		doctorsrepository.disable(id);
		
	}

	@Override
    @Deprecated
	public void enable(int id) throws NotFoundException {
		doctorsrepository.disable(id);
	}
    
}
