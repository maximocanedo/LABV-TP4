package web.generator;

import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import web.entity.Appointment;
import web.entity.AppointmentStatus;
import web.entity.Doctor;
import web.entity.IAppointment;
import web.entity.Optional;
import web.entity.Patient;
import web.entity.User;
import web.logicImpl.AppointmentLogicImpl;
import web.logicImpl.DoctorLogicImpl;

@Component("appointmentGenerator")
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
	public Appointment save(User requiring) {
		Appointment t = generate();
    	appointments.register(t, requiring);
    	return t;
	}
	
	public IAppointment save(Patient p, Doctor m, User requiring) {
		Appointment t = generate(p, m);
    	appointments.register(t, requiring);
    	return t;
	}

	public IAppointment generateForDoctor1234(Patient paciente, User requiring) {
        
        // Encontrar el médico con legajo 1234
        Optional<Doctor> optionalMedico = doctors.findByFile(1234, requiring);
        int attempts = 0;
        while(optionalMedico.isEmpty() && attempts < 4) {
        	User user = userGenerator.save(requiring);
            Doctor medico = doctorGenerator.generate(user);
            medico.setFile(1234);
            doctors.add(medico, requiring);
            optionalMedico = doctors.findByFile(1234, requiring);
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
        	appointments.register(turno, requiring);
        } catch(ConstraintViolationException e) {
        	// Expected error:
        	// El DNI es generado al azar, y puede a veces coincidir con un DNI ya existente.
        }

        return turno;
    }
    
	
	public IAppointment saveforP6(Patient p, Doctor m, User requiring) {
    	Appointment t = new Appointment();
    	t.setAssignedDoctor(m);
    	t.setPatient(p);
    	t.setRemarks("");
    	boolean presente = faker.bool().bool();
    	t.setStatus(presente ? AppointmentStatus.PRESENT : AppointmentStatus.ABSENT);
    	Date d = new Builder().setDate(2024, 0, 1).build().getTime();
    	Date d2 = new Builder().setDate(2024, 2, 2).build().getTime();
    	t.setDate(faker.date().between(d, d2));
    	appointments.register(t, requiring);
    	return t;
    	
    }
	
}
