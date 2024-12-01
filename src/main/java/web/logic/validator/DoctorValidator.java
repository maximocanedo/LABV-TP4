package web.logic.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.DoctorDAOImpl;
import web.daoImpl.SpecialtyDAOImpl;
import web.daoImpl.UserDAOImpl;
import web.entity.Day;
import web.entity.Permit;
import web.entity.Schedule;
import web.entity.Specialty;
import web.entity.User;
import web.exceptions.ValidationException;
import web.logicImpl.UserPermitLogicImpl;

@Component("doctorValidator")
public class DoctorValidator {
	@Autowired
	private DoctorDAOImpl doctorsrepository;
	
	@Autowired
	private SpecialtyDAOImpl specialtiesrepository;
	
	@Autowired
	private PatientValidator patientValidator;
	
	@Autowired
	private UserDAOImpl usersrepository;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	public char FEMALE = 'F';
	
	public char MALE = 'M';
	
	public int file(int file) throws ValidationException {
		if(doctorsrepository.existsByFile(file))
			throw new ValidationException("Doctor already in system. ", "El legajo introducido corresponde a un médico ya registrado. ");
		return file;
	}
	
	public String name(String name) throws ValidationException {
		return patientValidator.name(name);
	}
	
	public String surname(String name) throws ValidationException {
		return patientValidator.surname(name);
	}
	
	public String sex(String sex) throws ValidationException {
		sex = sex.toUpperCase();
		char s = sex.charAt(0);
		if(s != FEMALE && s != MALE) {
			throw new ValidationException("Invalid sex", "El valor para 'sexo' sólo puede ser F para femenino y M para masculino. ");
		}
		return sex;
	}
	
	public Date birth(Date birth) throws ValidationException {
		Calendar now = Calendar.getInstance();
		Date eighteenYearsAgo = new Calendar.Builder().setDate(now.get(Calendar.YEAR) - 18, now.get(Calendar.MONTH), now.get(Calendar.DATE)).build().getTime();
		if(birth.after(new Date(System.currentTimeMillis()))) 
			throw new ValidationException("Doctor is not born. ", "Ingrese una fecha de nacimiento anterior a la fecha actual. ");
		if(birth.after(eighteenYearsAgo))
			throw new ValidationException("Doctor must be +18 years old. ", "El médico debe tener al menos dieciocho años. ");
		return birth;
	}
	
	public String address(String address) throws ValidationException {
		return address;
	}
	
	public String localty(String localty) throws ValidationException {
		return localty;
	}
	
	public String email(String email) throws ValidationException {
		return patientValidator.email(email);
	}
	
	public String phone(String phone) throws ValidationException {
		return patientValidator.phone(phone);
	}
	
	public Specialty specialty(Specialty specialty) throws ValidationException {
		web.entity.Optional<Specialty> optS = specialtiesrepository.findById(specialty.getId());
		if(optS.isEmpty() || !optS.get().isActive())
			throw new ValidationException("Specialty not found. ", "Ingrese una especialidad válida. ");
		return optS.get();
	}
	
	public Specialty specialty(int id) throws ValidationException {
		Specialty s = new Specialty();
		s.setId(id);
		return specialty(s);
	}
	
	public User user(User requiring, User x) throws ValidationException {
		web.entity.Optional<User> optU = usersrepository.findByUsername(x.getUsername());
		if(optU.isEmpty() || !optU.get().isActive())
			throw new ValidationException("User not found. ", "Ingrese un usuario válido. ");
		if(optU.get().getDoctor() != null) {
			permits.require(requiring, Permit.ASSIGN_DOCTOR);
		}
		return optU.get();
	}
	
	public User user(User user) throws ValidationException {
		web.entity.Optional<User> optU = usersrepository.findByUsername(user.getUsername());
		if(optU.isEmpty() || !optU.get().isActive())
			throw new ValidationException("User not found. ", "Ingrese un usuario válido. ");
		return optU.get();
	}
	
	public User userButVerifyNoAssignedDoctors(User user) throws ValidationException {
		user = user(user);
		if(user.getDoctor() != null)
			throw new ValidationException("Chosen user is taken by another doctor. ", "El usuario seleccionado tiene asignado otro doctor. ");
		return user;
	}

	

    public void nonOverlapping(Schedule newSchedule, Set<Schedule> legacy) throws ValidationException {
    	validateNonOverlapping(newSchedule, legacy);
        legacy.add(newSchedule);
	}

    private void validateNonOverlapping(Schedule newSchedule, Set<Schedule> existingSchedules) throws ValidationException {
        for (Schedule existingSchedule : existingSchedules) {
            if (schedulesOverlap(newSchedule, existingSchedule)) {
                throw new ValidationException(
                    "Overlapping!!!!!!.",
                    "Uno o varios de los horarios a introducir se superpone con uno existente."
                );
            }
        }
    }

    private boolean schedulesOverlap(Schedule newSchedule, Schedule existingSchedule) {
        int startNew = newSchedule.getStartTime();
        int endNew = newSchedule.getEndTime();
        
        int startExisting = existingSchedule.getStartTime();
        int endExisting = existingSchedule.getEndTime();

        Day beginDayNew = newSchedule.getBeginDay();
        Day finishDayNew = newSchedule.getFinishDay();

        Day beginDayExisting = existingSchedule.getBeginDay();
        Day finishDayExisting = existingSchedule.getFinishDay();

		if (beginDayNew == beginDayExisting && timeRangesOverlap(startNew, endNew, startExisting, endExisting)) {
			return true;
		}

		if (finishDayNew == beginDayExisting && timeRangesOverlap(startNew, endNew, startExisting, endExisting)) {
			return true;
		}

		if (beginDayNew == finishDayExisting && timeRangesOverlap(startNew, endNew, startExisting, endExisting)) {
			return true;
		}

		if (finishDayNew == finishDayExisting && timeRangesOverlap(startNew, endNew, startExisting, endExisting)) {
			return true;
		}

        return false;
    }

    private boolean timeRangesOverlap(int startTime, int endTime, int startTimeExisting, int endTimeExisting) {
		if (endTime < startTime) endTime += 24;
		if (endTimeExisting < startTimeExisting) endTimeExisting += 24;
		return startTime < endTimeExisting && endTime > startTimeExisting;
    }
}
