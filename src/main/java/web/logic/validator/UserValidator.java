package web.logic.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.DoctorDAOImpl;
import web.entity.Doctor;
import web.entity.Optional;
import web.exceptions.ValidationException;
import web.logicImpl.UserLogicImpl;

@Component("userValidator")
public class UserValidator {

	@Autowired
	private UserLogicImpl users;
	
	@Autowired
	private DoctorDAOImpl doctorsrepository;
	
	private static final String USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9._]{4,14}$";
	//private static final String NAMES_PATTERN = "^[a-zA-Z]+(([\\'\\,\\.\\-][a-zA-Z])?[a-zA-Z]*)*$";
	/**
	 * Mínimo ocho caracteres.
	 * Al menos una mayúscula y una minúscula del alfabeto.
	 * Al menos un dígito y un caracter especial.
	 */
	private static final String PASS_PATTERN = "/^(?=.*?[A-ZÑÇ])(?=.*?[a-zñç])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/";
	
	public String username(String username) throws ValidationException {
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		Matcher matcher = pattern.matcher(username);
		if(!matcher.matches()) {
			throw new ValidationException("Validation error. ", "El nombre de usuario debe empezar con una letra, tener entre cinco y quince caracteres alfanúmericos, guiones bajos y/o puntos. ");
		}
		if(!users.checkUsernameAvailability(username))
			throw new ValidationException("Username not available. ", "El nombre de usuario especificado no está disponible. Intente con otro. ");
		return username;
	}
	
	public String name(String name) throws ValidationException {
		return name;
		/*
		Pattern pattern = Pattern.compile(NAMES_PATTERN);
		Matcher matcher = pattern.matcher(name);
		if(!matcher.matches()) {
			throw new ValidationException("Invalid name. ", "El nombre/apellido ingresado no es válido. ");
		}
		return name; */
	}
	
	public String password(String password, String name, String username) throws ValidationException {
		Pattern pattern = Pattern.compile(PASS_PATTERN);
		Matcher matcher = pattern.matcher(password);
		if(!matcher.matches())
			throw new ValidationException("Invalid password. ", "La contraseña, de al menos ocho caracteres, debe contener al menos una mayúscula, una minúscula, un dígito y un caracter especial. ");
		ValidationException e = new ValidationException("Invalid password. ", "No " + name + ", tu contraseña no puede tener tu nombre, ni tu nombre de usuario. ");
		if(password.toUpperCase().contains(name.toUpperCase()) 
				|| password.toUpperCase().contains(username.toUpperCase()) 
				|| password.equalsIgnoreCase(name) 
				|| password.equalsIgnoreCase(username))
			throw e;
		for(String word : name.toUpperCase().split(" ")) {
			if(password.toUpperCase().contains(word)) throw e;
		}
		for(String word : username.toUpperCase().split(" ")) {
			if(password.toUpperCase().contains(word)) throw e;
		}
		return password;
	}
	
	public Doctor doctor(Doctor doctor) {
		Optional<Doctor> od = new Optional<Doctor>(null);
		if(doctor.getFile() > 0) od = doctorsrepository.findByFile(doctor.getFile());
		else if(doctor.getId() > 0) od = doctorsrepository.findById(doctor.getId());
		else throw new ValidationException("Could not identify referenced doctor. ", "Envíe un dato identificador válido. ");
		if(od.isEmpty() || !od.get().isActive())
			throw new ValidationException("Referenced doctor does not exist. ", "");
		return od.get();
	}
	
}
