package web.logic.validator;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.AppointmentDAOImpl;
import web.daoImpl.DoctorDAOImpl;
import web.daoImpl.PatientDAOImpl;
import web.entity.*;
import web.exceptions.ValidationException;

@Component("appointmentValidator")
public class AppointmentValidator {

	@Autowired
	private DoctorDAOImpl doctorsrepository;
	
	@Autowired
	private PatientDAOImpl patientsrepository;

	@Autowired
	private AppointmentDAOImpl appointmentsrepository;
	
	public Date normalizeTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int mins = c.get(Calendar.MINUTE);
		mins -= mins % 15;
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public LocalTime extractAndNormalize(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(normalizeTime(date));
		return LocalTime.of(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
	}
	
	public Date date(Date date, IDoctor doctor) throws ValidationException {
		Date normalized = normalizeTime(date);
		LocalTime extract = extractAndNormalize(date);
		List<LocalTime> freeTimes = doctorsrepository.getFreeTimeForDoctor(doctor.getFile(), normalized);
		boolean scheduleIsAvailable = freeTimes.parallelStream()
				.anyMatch(time -> extract.equals(time));
		if(date.before(new Date()))
			throw new ValidationException("Selected date/time is in the past. ", "Intente eligiendo otro horario para ser atendido. ");
		if(!scheduleIsAvailable)
			throw new ValidationException("Selected date/time is not available. ", "Intente eligiendo otro horario para ser atendido. ");		
		return date;
	}
	
	public Date dateUpdate(Date date, IDoctor doctor) throws ValidationException {	
		return date(date, doctor);
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

	public void isAssigned(Appointment newAppointment) throws ValidationException {
		if(appointmentsrepository.isAssigned(newAppointment)){
			throw new ValidationException("Appointment already assigned to other patient. ");
		}

	}
	
}
