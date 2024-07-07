package web.validator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.PatientDAOImpl;
import web.entity.Day;
import web.entity.Schedule;
import web.exceptions.ValidationException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

@Component("patientValidator")
public class PatientValidator {

	@Autowired
	private PatientDAOImpl patientsrepository;
	
	@Autowired
	private UserValidator userValidator;
	
	/**
	 * Validador de correo electrónico.
	 * <br /><hr />
	 * <b>¿Por qué se usa un regex simple y no uno más complejo para los correos electrónicos?</b><br />
	 * Porque en lugar de preguntarse <i>¿Cómo valido un correo electrónico con un regex?</i> hay que preguntarse <i><u>¿Cómo me puedo asegurar de que el correo que me da el usuario pueda ser usado para comunicarse con él?</u></i>.
	 * <br />
	 * Está previsto incluir validación por correo electrónico, y con eso basta para lograr tener una vía de comunicación con el usuario.
	 * <br /><br />
	 * <a href="https://stackoverflow.com/questions/48055431/can-it-cause-harm-to-validate-email-addresses-with-a-regex">Can it cause harm to validate email addresses with a regex? (StackOverflow)</a>
	 * 
	 */
	public static String EMAIL_PATTERN = ".+@.+\\..+";
	
	public String name(String name) throws ValidationException {
		return userValidator.name(name);
	}
	
	public String surname(String surname) throws ValidationException {
		return userValidator.name(surname);
	}
	
	public String dni(String dni) throws ValidationException {
		// Tratar DNI
		dni = dni.replaceAll("[^0-9]", "");
		if(dni.length() < 4 || dni.length() > 12) 
			throw new ValidationException("DNI inválido. ", "Ingrese un número de DNI válido. ");
		if(patientsrepository.existsDNI(dni))
			throw new ValidationException("Patient already in system. ", "Existe un registro en el sistema con ese DNI. ");
		return dni;
	}
	
	public String phone(String phone) throws ValidationException {
		try {
			phone = formatPhoneNumber(phone, null);
		} catch (NumberParseException e) {
			throw new ValidationException("Invalid phone number. ", "Ingrese un número de teléfono válido. ");
		}
		return phone;
	}
	
	public String address(String address) throws ValidationException {
		return address;
	}
	
	public String localty(String localty) throws ValidationException {
		return localty;
	}
	
	public String province(String province) throws ValidationException {
		return province;
	}
	
	public Date birth(Date birth) throws ValidationException {
		if(birth.after(new Date(System.currentTimeMillis())))
			throw new ValidationException("Patient is not born. ", "Ingrese una fecha de nacimiento anterior al momento actual. ");
		return birth;
	}
	
	public String email(String email) throws ValidationException {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches())
			throw new ValidationException("Invalid email address. ", "Ingrese una dirección de correo electrónico válida. ");
		return email;
	}
	
	
	private String formatPhoneNumber(String phoneNumber, String defaultRegion) throws NumberParseException {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber parsedNumber = phoneNumberUtil.parse(phoneNumber, defaultRegion);

        if (phoneNumberUtil.isValidNumber(parsedNumber)) {
            return phoneNumberUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
        } else {
        	throw new ValidationException("Invalid phone number. ", "Ingrese un número de teléfono válido. ");
        }
    }
	
}
