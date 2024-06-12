package generator;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import entity.Appointment;
import entity.AppointmentStatus;
import entity.Doctor;
import entity.Optional;
import entity.Patient;
import entity.User;
import logicImpl.AppointmentLogicImpl;
import logicImpl.DoctorLogicImpl;

@Component
public class AppointmentGenerator implements IEntityGenerator<Appointment> {
	
	@Autowired
    private AppointmentLogicImpl appointments;
	
	@Autowired
    private DoctorLogicImpl doctors;
	
	@Autowired
    private UserGenerator userGenerator;
	
	@Autowired
    private DoctorGenerator doctorGenerator;
	
	@Autowired
    private PatientGenerator patientGenerator;
	
	@Autowired
    private Faker faker;
    
    public AppointmentGenerator() {}
    
	@Override
	public Appointment generate() {
		Patient p = patientGenerator.generate();
		Doctor m = doctorGenerator.generate();
		return generate(p, m);
	}
	
	public Appointment generate(Patient p, Doctor m) {
    	Appointment t = new Appointment();
    	t.setAssignedDoctor(m);
    	t.setPatient(p);
    	t.setRemarks("");
    	t.setStatus(AppointmentStatus.PENDING);
    	t.setDate(faker.date().future(1280, TimeUnit.DAYS));
    	return t;
	}

	@Override
	public Appointment save() {
		Appointment t = generate();
    	appointments.register(t);
    	return t;
	}
	
	public Appointment save(Patient p, Doctor m) {
		Appointment t = generate(p, m);
    	appointments.register(t);
    	return t;
	}

	public Appointment generateForDoctor1234(Patient paciente) {
        
        // Encontrar el médico con legajo 1234
        Optional<Doctor> optionalMedico = doctors.findByFile(1234);
        int attempts = 0;
        while(optionalMedico.isEmpty() && attempts < 4) {
        	User user = userGenerator.save();
            Doctor medico = doctorGenerator.generate(user);
            medico.setFile(1234);
            doctors.add(medico);
            optionalMedico = doctors.findByFile(1234);
            attempts++;
        }
        
        Doctor medico = optionalMedico.get();

        Appointment turno = new Appointment();
        turno.setAssignedDoctor(medico);
        turno.setPatient(paciente);
        turno.setRemarks("");
        turno.setStatus(AppointmentStatus.PENDING);


        Calendar start = Calendar.getInstance();
        start.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        Date startDate = start.getTime();

        Calendar end = Calendar.getInstance();
        end.set(2025, Calendar.JANUARY, 2, 23, 59, 59);
        Date endDate = end.getTime();

        Date randomDate = faker.date().between(startDate, endDate);
        turno.setDate(randomDate);
        try {
        	appointments.register(turno);
        } catch(ConstraintViolationException e) {
        	// Expected error:
        	// El DNI es generado al azar, y puede a veces coincidir con un DNI ya existente.
        }

        return turno;
    }
    
	
	@SuppressWarnings("deprecation")
	public Appointment saveforP6(Patient p, Doctor m) {
    	Appointment t = new Appointment();
    	t.setAssignedDoctor(m);
    	t.setPatient(p);
    	t.setRemarks("");
    	boolean presente = faker.bool().bool();
    	t.setStatus(presente ? AppointmentStatus.PRESENT : AppointmentStatus.ABSENT);
    	Date d = new Date();
    	d.setDate(1);
    	d.setMonth(0);
    	d.setYear(124);
    	Date d2 = new Date();
    	d2.setDate(1);
    	d2.setMonth(2);
    	d2.setYear(124);
    	t.setDate(faker.date().between(d, d2));
    	appointments.register(t);
    	return t;
    	
    }
	
}
