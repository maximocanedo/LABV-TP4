package web.validator;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.DoctorDAOImpl;
import web.daoImpl.SpecialtyDAOImpl;
import web.daoImpl.UserDAOImpl;
import web.entity.Optional;
import web.entity.Specialty;
import web.entity.User;
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
	
	public String sex(String sex) throws ValidationException {
		sex = sex.toUpperCase();
		if(!sex.equals(FEMALE) && !sex.equals(MALE)) {
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
	
	
	
}
