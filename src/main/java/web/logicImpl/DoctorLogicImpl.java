package web.logicImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.DoctorDAOImpl;
import web.daoImpl.ScheduleDAOImpl;
import web.entity.Doctor;
import web.entity.IDoctor;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.Schedule;
import web.entity.User;
import web.entity.input.DoctorQuery;
import web.entity.view.DoctorMinimalView;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;
import web.logic.IDoctorLogic;
import web.logic.validator.DoctorValidator;

@Component("doctors")
public class DoctorLogicImpl implements IDoctorLogic {

	@Autowired
    private DoctorDAOImpl doctorsrepository;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	@Autowired
	private ScheduleDAOImpl schedulesrepository;
	
	@Autowired
	private DoctorValidator doctorValidator;

    public DoctorLogicImpl() {}

    @Override
    public Doctor add(Doctor doctor, User requiring) {
    	permits.require(requiring, Permit.CREATE_DOCTOR);
    	doctor.setFile(doctorValidator.file(doctor.getFile()));
    	doctor.setName(doctorValidator.name(doctor.getName()));
    	doctor.setSurname(doctorValidator.surname(doctor.getSurname()));
    	doctor.setSex(doctorValidator.sex(doctor.getSex()));
    	doctor.setBirth(doctorValidator.birth(doctor.getBirth()));
    	doctor.setAddress(doctorValidator.address(doctor.getAddress()));
    	doctor.setLocalty(doctorValidator.localty(doctor.getLocalty()));
    	doctor.setEmail(doctorValidator.email(doctor.getEmail()));
    	doctor.setPhone(doctorValidator.phone(doctor.getPhone()));
    	doctor.setSpecialty(doctorValidator.specialty(doctor.getSpecialty()));
    	doctor.setUser(doctorValidator.user(doctor.getUser()));
    	// No es posible agregar un conjunto de horarios al registrar un doctor. Ver más info en el javadoc de este método.
    	//doctor.setSchedules(doctorValidator.nonOverlapping(doctor.getSchedules(), new HashSet<Schedule>()));
        return doctorsrepository.add(doctor);
    }
    
    // TODO Pendiente probar
    @Override
    public Set<Schedule> addSchedule(int file, Schedule schedule, User requiring) {
    	requiring = permits.inquireDoctorByFile(requiring, file, Permit.UPDATE_DOCTOR_SCHEDULES);
    	Optional<Doctor> optdoctor = doctorsrepository.findByFile(file);
    	if(optdoctor.isEmpty()) throw new NotFoundException("Doctor not found. ");
    	Doctor doctor = optdoctor.get();

    	Set<Schedule> originalSchedules = doctor.getSchedules();
    	HashSet<Schedule> clonedOriginal = new HashSet<Schedule>(originalSchedules);
    	doctorValidator.nonOverlappingIndividual(schedule, clonedOriginal);
    	
    	Schedule saved = schedulesrepository.save(schedule);
    	Set<Schedule> n2wSchedules = doctorValidator.nonOverlappingIndividual(schedule, originalSchedules);
    	//originalSchedules.add(saved);
    	schedulesrepository.link(saved, doctor);
    	doctor.setSchedules(n2wSchedules);
    	return n2wSchedules;
    }
	//newSchedules = doctorValidator.nonOverlappingIndividual(schedule, originalSchedules);
    
    public Set<Schedule> addScheduleById(int id, Schedule schedule, User requiring) {
    	IDoctor d = doctorsrepository.findMinById(id, false).get();
    	if(d == null) throw new NotFoundException("Doctor not found. ");
    	return addSchedule(d.getFile(), schedule, requiring);
    }
    
    // TODO Pendiente probar
    @Override
    public Set<Schedule> removeSchedule(int file, Schedule schedule, User requiring) {
    	requiring = permits.inquireDoctorByFile(requiring, file, Permit.UPDATE_DOCTOR_SCHEDULES);
    	schedulesrepository.delete(schedule.getId());
    	Optional<Doctor> optdoctor = doctorsrepository.findByFile(file);
    	if(optdoctor.isEmpty()) throw new NotFoundException("Doctor not found. ");
    	Doctor doctor = optdoctor.get();
    	doctor.removeSchedule(schedule);
    	return doctor.getSchedules();
    }
    
    public Set<Schedule> removeSchedule(int file, int schedule, User requiring) {
    	Schedule s = new Schedule();
    	s.setId(schedule);
    	return removeSchedule(file, s, requiring);
    }
    
    public Set<Schedule> removeScheduleById(int id, Schedule schedule, User requiring) {
    	IDoctor d = doctorsrepository.findMinById(id, false).get();
    	if(d == null) throw new NotFoundException("Doctor not found. ");
    	return removeSchedule(d.getFile(), schedule, requiring);
    }
    
    public Set<Schedule> removeScheduleById(int id, int schedule, User requiring) {
    	IDoctor d = doctorsrepository.findMinById(id, false).get();
    	if(d == null) throw new NotFoundException("Doctor not found. ");
    	return removeSchedule(d.getFile(), schedule, requiring);
    }

    @Override
    public Optional<Doctor> findById(int id, User requiring) {
    	requiring = permits.inquireDoctorById(requiring, id, Permit.READ_APPOINTMENT, Permit.READ_DOCTOR);
    	boolean includeInactives = requiring.can(Permit.ENABLE_DOCTOR);
        return doctorsrepository.findById(id, includeInactives);
    }
    
    public IDoctor getById(int id, User requiring) {
    	requiring = permits.inquireDoctorById(requiring, id, Permit.READ_DOCTOR, Permit.READ_APPOINTMENT);
    	IDoctor x = null;
    	boolean includeInactives = requiring.can(Permit.ENABLE_DOCTOR);
    	if(!requiring.can(Permit.READ_DOCTOR)) {
    		x = doctorsrepository.findMinById(id, includeInactives).get();
    	} else x = doctorsrepository.findById(id, includeInactives).get();
		if(x == null) throw new NotFoundException("Doctor not found. ");
		return x;
    }
    
    public IDoctor getByFile(int file, User requiring) {
    	requiring = permits.inquireDoctorByFile(requiring, file, Permit.READ_DOCTOR, Permit.READ_APPOINTMENT);
    	IDoctor x = null;
    	boolean includeInactives = requiring.can(Permit.ENABLE_DOCTOR);
    	if(!requiring.can(Permit.READ_DOCTOR)) {
    		x = doctorsrepository.findMinByFile(file, includeInactives).get();
    	} else x = doctorsrepository.findByFile(file, includeInactives).get();
		if(x == null) throw new NotFoundException("Doctor not found. ");
		return x;
    }
    
    @Override
    public List<DoctorMinimalView> search(DoctorQuery query, User requiring) {
    	permits.require(requiring, Permit.READ_DOCTOR, Permit.CREATE_APPOINTMENT, Permit.READ_APPOINTMENT, Permit.UPDATE_APPOINTMENT);
    	return doctorsrepository.search(query);
    }
    
    public List<DoctorMinimalView> searchForSelector(DoctorQuery query, User requiring) {
    	try {
        	requiring = permits.require(requiring, Permit.READ_DOCTOR, Permit.CREATE_APPOINTMENT, Permit.READ_APPOINTMENT, Permit.UPDATE_APPOINTMENT);
    	} catch(NotAllowedException e) {
    		if(requiring.getDoctor() != null) throw e;
    		
    	}
    	return doctorsrepository.search(query);
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
		requiring = permits.inquireDoctorByFile(requiring, file, Permit.READ_DOCTOR);
    	boolean includeInactives = requiring.can(Permit.ENABLE_DOCTOR);
		return doctorsrepository.findByFile(file, includeInactives);
	}

	@Override
	public Optional<Doctor> findById(int id, boolean includeInactive, User requiring) {
		requiring = permits.inquireDoctorById(requiring, id, Permit.READ_DOCTOR);
    	boolean includeInactives = includeInactive && requiring.can(Permit.ENABLE_DOCTOR);
		return doctorsrepository.findById(id, includeInactives);
	}

	@Override
	public Doctor update(Doctor medico, User requiring) throws NotFoundException {
    	permits.inquireDoctor(requiring, medico, Permit.UPDATE_DOCTOR_PERSONAL_DATA);
    	Optional<Doctor> search = doctorsrepository.findById(medico.getId());
    	if(search.isEmpty()) search = doctorsrepository.findByFile(medico.getFile());
    	if(search.isEmpty()) throw new NotFoundException();
    	Doctor original = search.get();
    	if (medico.getName() != null) 
    		original.setName(doctorValidator.name(medico.getName()));
        if (medico.getSurname() != null)
        	original.setSurname(doctorValidator.surname(medico.getSurname()));
        if (medico.getSex() != null) 
        	original.setSex(doctorValidator.sex(medico.getSex()));
        if (medico.getBirth() != null) 
        	original.setBirth(doctorValidator.birth(medico.getBirth()));
        if (medico.getAddress() != null) 
        	original.setAddress(doctorValidator.address(medico.getAddress()));
        if (medico.getLocalty() != null) 
        	original.setLocalty(doctorValidator.localty(medico.getLocalty()));
        if (medico.getEmail() != null) 
        	original.setEmail(doctorValidator.email(medico.getEmail()));
        if (medico.getPhone() != null) 
        	original.setPhone(doctorValidator.phone(medico.getPhone()));
        if (medico.getSpecialty() != null) 
        	original.setSpecialty(doctorValidator.specialty(medico.getSpecialty()));
        if (medico.getUser() != null) 
        	original.setUser(doctorValidator.user(requiring, medico.getUser()));
		return doctorsrepository.update(original);
	}

	@Override
	public void disable(int id, User requiring) throws NotFoundException {
    	permits.inquireDoctorById(requiring, id, Permit.DISABLE_DOCTOR);
		doctorsrepository.disable(id);
		
	}

	@Override
	public void enable(int id, User requiring) throws NotFoundException {
    	permits.require(requiring, Permit.ENABLE_DOCTOR);
		doctorsrepository.enable(id);
	}
	
	public List<Date> getSchedulesForDoctor(int file, Date startDate, User requiring) {
		permits.inquireDoctorByFile(requiring, file, Permit.CREATE_APPOINTMENT, Permit.UPDATE_APPOINTMENT, Permit.READ_DOCTOR_APPOINTMENTS);
		if(!doctorsrepository.existsByFile(file))
			throw new NotFoundException("Doctor not found. ");
		return doctorsrepository.getScheduleForDoctor(file, startDate);
	}
	
	public List<Date> getSchedulesForDoctor(int file, Calendar startDate, User requiring) {
		permits.inquireDoctorByFile(requiring, file, Permit.CREATE_APPOINTMENT, Permit.UPDATE_APPOINTMENT, Permit.READ_DOCTOR_APPOINTMENTS);
		if(!doctorsrepository.existsByFile(file))
			throw new NotFoundException("Doctor not found. ");
		return doctorsrepository.getScheduleForDoctor(file, startDate);
	}
	
	public List<LocalTime> getFreeTimeForDoctor(int file, Date date, User requiring) {
		permits.inquireDoctorByFile(requiring, file, Permit.CREATE_APPOINTMENT, Permit.UPDATE_APPOINTMENT, Permit.READ_DOCTOR_APPOINTMENTS);
		if(!doctorsrepository.existsByFile(file))
			throw new NotFoundException("Doctor not found. ");
		return doctorsrepository.getFreeTimeForDoctor(file, date);		
	}

	public boolean existsByFile(int file) {
		return doctorsrepository.existsByFile(file);
	}
	
	public boolean existsById(int id) {
		return doctorsrepository.existsById(id);
	}
	
}
