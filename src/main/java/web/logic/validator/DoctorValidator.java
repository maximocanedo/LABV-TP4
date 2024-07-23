package web.logic.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.DoctorDAOImpl;
import web.daoImpl.SpecialtyDAOImpl;
import web.daoImpl.UserDAOImpl;
import web.entity.Optional;
import web.entity.Schedule;
import web.entity.Specialty;
import web.entity.User;
import web.exceptions.CommonException;
import web.exceptions.ValidationException;

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
		Optional<Specialty> optS = specialtiesrepository.findById(specialty.getId());
		if(optS.isEmpty() || !optS.get().isActive())
			throw new ValidationException("Specialty not found. ", "Ingrese una especialidad válida. ");
		return optS.get();
	}
	
	public Specialty specialty(int id) throws ValidationException {
		Specialty s = new Specialty();
		s.setId(id);
		return specialty(s);
	}
	
	public User user(User user) throws ValidationException {
		Optional<User> optU = usersrepository.findByUsername(user.getUsername());
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
	
	public Set<Schedule> nonOverlapping(Set<Schedule> newSchedules, Set<Schedule> legacy) throws ValidationException {
		for(Schedule candidate : newSchedules) 
			legacy.add(nonOverlapping(candidate, legacy));
		return legacy;
	}
	
	
	private Schedule nonOverlapping(Schedule newSchedule, Set<Schedule> schedules) throws ValidationException {
		CommonException ve = new ValidationException("E.Overlapping schedules. ", "Uno o varios de los horarios a introducir se superpone con uno existente. ");
        for (Schedule existingSchedule : schedules) {
            if (newSchedule.getBeginDay() == existingSchedule.getBeginDay()) {
                if (newSchedule.getEndTimeLT().isAfter(existingSchedule.getStartTimeLT()) &&
                    newSchedule.getStartTimeLT().isBefore(existingSchedule.getEndTimeLT()))
                    throw ve;
            } else if (newSchedule.getFinishDay() == existingSchedule.getBeginDay()) {
                if (newSchedule.getEndTimeLT().isAfter(existingSchedule.getStartTimeLT()) &&
                    newSchedule.getEndTimeLT().isBefore(existingSchedule.getEndTimeLT()))
                    throw ve;
            } else if (newSchedule.getBeginDay() == existingSchedule.getFinishDay()) {
                if (newSchedule.getStartTimeLT().isBefore(existingSchedule.getEndTimeLT()) &&
                    newSchedule.getStartTimeLT().isAfter(existingSchedule.getStartTimeLT()))
                    throw ve;
            }
        }
        return newSchedule;
    }
	
	public Set<Schedule> nonOverlappingIndividual(Schedule newSchedule, Set<Schedule> schedules) throws ValidationException {
		CommonException ve = new ValidationException("1.Overlapping schedules. ", "Uno o varios de los horarios a introducir se superpone con uno existente. ");
		CommonException ve2 = new ValidationException("2.Overlapping schedules. ", "Uno o varios de los horarios a introducir se superpone con uno existente. ");
		CommonException ve3 = new ValidationException("3.Overlapping schedules. ", "Uno o varios de los horarios a introducir se superpone con uno existente. ");
        /* boolean overlaps = schedules.parallelStream()
        		.anyMatch(e -> 
        			( // N.day = E.day
        				newSchedule.getBeginDay() == e.getBeginDay()
        				// & N.end > E.start
        				&& newSchedule.getEndTimeLT().isAfter(e.getStartTimeLT()) 
        				// & N.start < E.end
        				&& newSchedule.getStartTimeLT().isBefore(e.getEndTimeLT())
					) || (
						newSchedule.getFinishDay() == e.getBeginDay()
						&& newSchedule.getEndTimeLT().isAfter(e.getStartTimeLT()) 
						&& newSchedule.getEndTimeLT().isBefore(e.getEndTimeLT())
					) || (
						newSchedule.getBeginDay() == e.getFinishDay()
						&& newSchedule.getStartTimeLT().isBefore(e.getEndTimeLT()) 
						&& newSchedule.getStartTimeLT().isAfter(e.getStartTimeLT())
					)
        		);
        if(overlaps) throw ve; // */
        
		// No borrar hasta probar que funcione adecuadamente. 
		///* 
        for (Schedule e : schedules) {
        	System.out.println(e);
            if (newSchedule.getBeginDay() == e.getBeginDay()) {
                if (newSchedule.getEndTimeLT().isAfter(e.getStartTimeLT()) &&
                    newSchedule.getStartTimeLT().isBefore(e.getEndTimeLT()))
                    throw ve;
            } else if (newSchedule.getFinishDay() == e.getBeginDay()) {
                if (newSchedule.getEndTimeLT().isAfter(e.getStartTimeLT()) &&
                    newSchedule.getEndTimeLT().isBefore(e.getEndTimeLT()))
                    throw ve2;
            } else if (newSchedule.getBeginDay() == e.getFinishDay()) {
                if (newSchedule.getStartTimeLT().isBefore(e.getEndTimeLT()) &&
                    newSchedule.getStartTimeLT().isAfter(e.getStartTimeLT()))
                    throw ve3;
            }
        } // */
        schedules.add(newSchedule);
        
        return schedules;
    }
	
	
}
