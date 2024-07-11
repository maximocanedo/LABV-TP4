package web.validator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.DoctorDAOImpl;
import web.daoImpl.PatientDAOImpl;
import web.entity.AppointmentStatus;
import web.entity.*;
import web.exceptions.ValidationException;

@Component("appointmentValidator")
public class AppointmentValidator {

	@Autowired
	private DoctorDAOImpl doctorsrepository;
	
	@Autowired
	private PatientDAOImpl patientsrepository;
	
	public Date date(Date date) throws ValidationException {
		// TODO Implementar el procedimiento. Validar que el horario esté libre.
		return date;
	}
	
	public String remarks(String remarks) throws ValidationException {
		return remarks;
	}
	
	public AppointmentStatus status(AppointmentStatus status) throws ValidationException {
		return status;
	}
	
	public Doctor doctor(IDoctor doctor) throws ValidationException {
		Optional<Doctor> doc = doctorsrepository.findByFile(doctor.getFile());
		if(doc.isEmpty())
			throw new ValidationException("Doctor not found. ");
		return doc.get();
	}
	
	public Patient patient(IPatient patient) throws ValidationException {
		Optional<Patient> pat = patientsrepository.findById(patient.getId());
		if(pat.isEmpty())
			throw new ValidationException("Patient not found. ");
		return pat.get();
	}
	
}
