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
    	getPermits().require(requiring, Permit.CREATE_DOCTOR);
    	doctor.setFile(getDoctorValidator().file(doctor.getFile()));
    	doctor.setName(getDoctorValidator().name(doctor.getName()));
    	doctor.setSurname(getDoctorValidator().surname(doctor.getSurname()));
    	doctor.setSex(getDoctorValidator().sex(doctor.getSex()));
    	doctor.setBirth(getDoctorValidator().birth(doctor.getBirth()));
    	doctor.setAddress(getDoctorValidator().address(doctor.getAddress()));
    	doctor.setLocalty(getDoctorValidator().localty(doctor.getLocalty()));
    	doctor.setEmail(getDoctorValidator().email(doctor.getEmail()));
    	doctor.setPhone(getDoctorValidator().phone(doctor.getPhone()));
    	doctor.setSpecialty(getDoctorValidator().specialty(doctor.getSpecialty()));
    	doctor.setUser(getDoctorValidator().user(doctor.getUser()));
    	// No es posible agregar un conjunto de horarios al registrar un doctor. Ver más info en el javadoc de este método.
    	//doctor.setSchedules(doctorValidator.nonOverlapping(doctor.getSchedules(), new HashSet<Schedule>()));
        return getDoctorsrepository().add(doctor);
    }
    
    // TODO eliminado, Metodo testeado.
    @SuppressWarnings("unchecked")
	@Override
    public Set<Schedule> addSchedule(int file, Schedule schedule, User requiring) {

        requiring = getPermits().inquireDoctorByFile(requiring, file, Permit.UPDATE_DOCTOR_SCHEDULES);
        Optional<Doctor> optDoctor = getDoctorsrepository().findByFile(file);
        if (optDoctor.isEmpty()) {
            throw new NotFoundException("Doctor not found.");
        }
        Doctor doctor = optDoctor.get();
        Set<Schedule> existingSchedules = doctor.getSchedules();
        Set<Schedule> clonedSchedules = new HashSet<>(existingSchedules);
        getDoctorValidator().nonOverlapping(schedule, clonedSchedules);
        Schedule savedSchedule = getSchedulesrepository().save(schedule);
        getSchedulesrepository().link(savedSchedule, doctor);
        existingSchedules.add(savedSchedule);
        doctor.setSchedules(existingSchedules);

        return existingSchedules;
    }
	//newSchedules = doctorValidator.nonOverlappingIndividual(schedule, originalSchedules);
    
    public Set<Schedule> addScheduleById(int id, Schedule schedule, User requiring) {
    	IDoctor d = getDoctorsrepository().findMinById(id, false).get();
    	if(d == null) throw new NotFoundException("Doctor not found. ");
    	return addSchedule(d.getFile(), schedule, requiring);
    }
    
    // TODO Pendiente probar
    @Override
    public Set<Schedule> removeSchedule(int file, Schedule schedule, User requiring) {
    	requiring = getPermits().inquireDoctorByFile(requiring, file, Permit.UPDATE_DOCTOR_SCHEDULES);
    	getSchedulesrepository().delete(schedule.getId());
    	Optional<Doctor> optdoctor = getDoctorsrepository().findByFile(file);
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
    	IDoctor d = getDoctorsrepository().findMinById(id, false).get();
    	if(d == null) throw new NotFoundException("Doctor not found. ");
    	return removeSchedule(d.getFile(), schedule, requiring);
    }
    
    public Set<Schedule> removeScheduleById(int id, int schedule, User requiring) {
    	IDoctor d = getDoctorsrepository().findMinById(id, false).get();
    	if(d == null) throw new NotFoundException("Doctor not found. ");
    	return removeSchedule(d.getFile(), schedule, requiring);
    }

    @Override
    public Optional<Doctor> findById(int id, User requiring) {
    	requiring = getPermits().inquireDoctorById(requiring, id, Permit.READ_APPOINTMENT, Permit.READ_DOCTOR);
    	boolean includeInactives = requiring.can(Permit.ENABLE_DOCTOR);
        return getDoctorsrepository().findById(id, includeInactives);
    }
    
    public IDoctor getById(int id, User requiring) {
    	requiring = getPermits().inquireDoctorById(requiring, id, Permit.READ_DOCTOR, Permit.READ_APPOINTMENT);
    	IDoctor x = null;
    	boolean includeInactives = requiring.can(Permit.ENABLE_DOCTOR);
    	if(!requiring.can(Permit.READ_DOCTOR)) {
    		x = getDoctorsrepository().findMinById(id, includeInactives).get();
    	} else x = getDoctorsrepository().findById(id, includeInactives).get();
		if(x == null) throw new NotFoundException("Doctor not found. ");
		return x;
    }
    
    public IDoctor getByFile(int file, User requiring) {
    	requiring = getPermits().inquireDoctorByFile(requiring, file, Permit.READ_DOCTOR, Permit.READ_APPOINTMENT);
    	IDoctor x = null;
    	boolean includeInactives = requiring.can(Permit.ENABLE_DOCTOR);
    	if(!requiring.can(Permit.READ_DOCTOR)) {
    		x = getDoctorsrepository().findMinByFile(file, includeInactives).get();
    	} else x = getDoctorsrepository().findByFile(file, includeInactives).get();
		if(x == null) throw new NotFoundException("Doctor not found. ");
		return x;
    }
    
    @Override
    public List<DoctorMinimalView> search(DoctorQuery query, User requiring) {
    	getPermits().require(requiring, Permit.READ_DOCTOR, Permit.CREATE_APPOINTMENT, Permit.READ_APPOINTMENT, Permit.UPDATE_APPOINTMENT);
    	return getDoctorsrepository().search(query);
    }
    
    public List<DoctorMinimalView> searchForSelector(DoctorQuery query, User requiring) {
    	try {
        	requiring = getPermits().require(requiring, Permit.READ_DOCTOR, Permit.CREATE_APPOINTMENT, Permit.READ_APPOINTMENT, Permit.UPDATE_APPOINTMENT);
    	} catch(NotAllowedException e) {
    		if(requiring.getDoctor() != null) throw e;
    		
    	}
    	return getDoctorsrepository().search(query);
    }

	@Override
	public List<Object[]> listOnlyFileNumbersAndNames(User requiring) {
    	getPermits().require(requiring, Permit.READ_DOCTOR);
		return getDoctorsrepository().listOnlyFileNumbersAndNames();
	}
	
	@Override
    public List<Integer> listOnlyFileNumbers(User requiring){
    	getPermits().require(requiring, Permit.READ_DOCTOR);
		return getDoctorsrepository().listOnlyFileNumbers();
	}
	
	@Override
    public Doctor findDoctorWithHighestFileNumber(User requiring) {
    	getPermits().require(requiring, Permit.READ_DOCTOR);
		return getDoctorsrepository().findDoctorWithHighestFileNumber();
	}
	
	@Override
    public List<Doctor> listOrderByFileDescending(int page, int size, User requiring) {
    	getPermits().require(requiring, Permit.READ_DOCTOR);
		return getDoctorsrepository().listOrderByFileDescending(page, size);
	}
	
	@Override
    public List<Doctor> listOrderByFileDescending(User requiring) {
    	getPermits().require(requiring, Permit.READ_DOCTOR);
		return this.listOrderByFileDescending(1, 10, requiring);
	}
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDate(int legajo, LocalDate fecha, User requiring) {
    	getPermits().require(requiring, Permit.READ_DOCTOR_APPOINTMENTS);
        return getDoctorsrepository().getAppointmentsByDoctorAndDate(legajo, fecha);
    }
	
	@Override
    public List<Object[]> getAppointmentsByDoctorAndDateRange(int legajo, LocalDate fechaInicio, LocalDate fechaFin, User requiring) {
    	getPermits().require(requiring, Permit.READ_DOCTOR_APPOINTMENTS);
        return getDoctorsrepository().getAppointmentsByDoctorAndDateRange(legajo, fechaInicio, fechaFin);
    }

	@Override
	public Optional<Doctor> findByFile(int file, User requiring) {
		requiring = getPermits().inquireDoctorByFile(requiring, file, Permit.READ_DOCTOR);
    	boolean includeInactives = requiring.can(Permit.ENABLE_DOCTOR);
		return getDoctorsrepository().findByFile(file, includeInactives);
	}

	@Override
	public Optional<Doctor> findById(int id, boolean includeInactive, User requiring) {
		requiring = getPermits().inquireDoctorById(requiring, id, Permit.READ_DOCTOR);
    	boolean includeInactives = includeInactive && requiring.can(Permit.ENABLE_DOCTOR);
		return getDoctorsrepository().findById(id, includeInactives);
	}

	@Override
	public Doctor update(Doctor medico, User requiring) throws NotFoundException {
    	getPermits().inquireDoctor(requiring, medico, Permit.UPDATE_DOCTOR_PERSONAL_DATA);
    	Optional<Doctor> search = getDoctorsrepository().findById(medico.getId());
    	if(search.isEmpty()) search = getDoctorsrepository().findByFile(medico.getFile());
    	if(search.isEmpty()) throw new NotFoundException();
    	Doctor original = search.get();
    	if (medico.getName() != null) 
    		original.setName(getDoctorValidator().name(medico.getName()));
        if (medico.getSurname() != null)
        	original.setSurname(getDoctorValidator().surname(medico.getSurname()));
        if (medico.getSex() != null) 
        	original.setSex(getDoctorValidator().sex(medico.getSex()));
        if (medico.getBirth() != null) 
        	original.setBirth(getDoctorValidator().birth(medico.getBirth()));
        if (medico.getAddress() != null) 
        	original.setAddress(getDoctorValidator().address(medico.getAddress()));
        if (medico.getLocalty() != null) 
        	original.setLocalty(getDoctorValidator().localty(medico.getLocalty()));
        if (medico.getEmail() != null) 
        	original.setEmail(getDoctorValidator().email(medico.getEmail()));
        if (medico.getPhone() != null) 
        	original.setPhone(getDoctorValidator().phone(medico.getPhone()));
        if (medico.getSpecialty() != null) 
        	original.setSpecialty(getDoctorValidator().specialty(medico.getSpecialty()));
        if (medico.getUser() != null) 
        	original.setUser(getDoctorValidator().user(requiring, medico.getUser()));
		return getDoctorsrepository().update(original);
	}

	@Override
	public void disable(int id, User requiring) throws NotFoundException {
    	getPermits().inquireDoctorById(requiring, id, Permit.DISABLE_DOCTOR);
		getDoctorsrepository().disable(id);
		
	}

	@Override
	public void enable(int id, User requiring) throws NotFoundException {
    	getPermits().require(requiring, Permit.ENABLE_DOCTOR);
		getDoctorsrepository().enable(id);
	}
	
	public List<Date> getSchedulesForDoctor(int file, Date startDate, User requiring) {
		getPermits().inquireDoctorByFile(requiring, file, Permit.CREATE_APPOINTMENT, Permit.UPDATE_APPOINTMENT, Permit.READ_DOCTOR_APPOINTMENTS);
		if(!getDoctorsrepository().existsByFile(file))
			throw new NotFoundException("Doctor not found. ");
		return getDoctorsrepository().getScheduleForDoctor(file, startDate);
	}
	
	public List<Date> getSchedulesForDoctor(int file, Calendar startDate, User requiring) {
		getPermits().inquireDoctorByFile(requiring, file, Permit.CREATE_APPOINTMENT, Permit.UPDATE_APPOINTMENT, Permit.READ_DOCTOR_APPOINTMENTS);
		if(!getDoctorsrepository().existsByFile(file))
			throw new NotFoundException("Doctor not found. ");
		return getDoctorsrepository().getScheduleForDoctor(file, startDate);
	}
	
	public List<LocalTime> getFreeTimeForDoctor(int file, Date date, User requiring) {
		getPermits().inquireDoctorByFile(requiring, file, Permit.CREATE_APPOINTMENT, Permit.UPDATE_APPOINTMENT, Permit.READ_DOCTOR_APPOINTMENTS);
		if(!getDoctorsrepository().existsByFile(file))
			throw new NotFoundException("Doctor not found. ");
		return getDoctorsrepository().getFreeTimeForDoctor(file, date);		
	}

	public boolean existsByFile(int file) {
		return getDoctorsrepository().existsByFile(file);
	}
	
	public boolean existsById(int id) {
		return getDoctorsrepository().existsById(id);
	}

	public DoctorDAOImpl getDoctorsrepository() {
		return doctorsrepository;
	}

	public void setDoctorsrepository(DoctorDAOImpl doctorsrepository) {
		this.doctorsrepository = doctorsrepository;
	}

	public UserPermitLogicImpl getPermits() {
		return permits;
	}

	public void setPermits(UserPermitLogicImpl permits) {
		this.permits = permits;
	}

	public ScheduleDAOImpl getSchedulesrepository() {
		return schedulesrepository;
	}

	public void setSchedulesrepository(ScheduleDAOImpl schedulesrepository) {
		this.schedulesrepository = schedulesrepository;
	}

	public DoctorValidator getDoctorValidator() {
		return doctorValidator;
	}

	public void setDoctorValidator(DoctorValidator doctorValidator) {
		this.doctorValidator = doctorValidator;
	}
	
}
