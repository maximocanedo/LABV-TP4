package web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.exceptions.ValidationException;

@Component("specialtyValidator")
public class SpecialtyValidator {
	
	@Autowired
	private UserValidator userValidator;
	
	public String name(String name) throws ValidationException {
		return userValidator.name(name);
	}
	
	public String description(String description) throws ValidationException {
		if(description.length() > 254) throw new ValidationException("Invalid description. ", "El límite para este campo es de 254 caracteres. ");
		return description;
	}
	
}
