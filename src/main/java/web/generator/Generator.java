package web.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import web.entity.Doctor;
import web.entity.IAppointment;
import web.entity.Patient;
import web.entity.User;

@Component("generator")
public class Generator {
	
	private static String DELIMITER = "\n**************************\n";
	
	@Autowired
	private SpecialtyGenerator specialtyGenerator;
	
	@Autowired
	private AppointmentGenerator appointmentGenerator;
	
	@Autowired
	private DoctorGenerator doctorGenerator;
	
	@Autowired
	private PatientGenerator patientGenerator;
	
	@Autowired
	private UserGenerator userGenerator;

    public Generator() {}
    
    public void generate(int total, User requiring) {
    	generate(total, false, requiring);
    }
    
    public void generate(int total, boolean logEveryRecord, User requiring) {
		System.out.println("Se guardarán los objetos Especialidad. ");
		specialtyGenerator.save(requiring);
		System.out.println("Se generarán y guardarán " + total + " registros. ");

		StringBuilder usersLog = new StringBuilder();
		StringBuilder patientsLog = new StringBuilder();
		StringBuilder doctorsLog = new StringBuilder();
		StringBuilder appointmentsLog = new StringBuilder();
		
		Faker faker = new Faker();
		
		for(int i = 0; i < total; i++) {
			// Generar
			Name nn = faker.name();
			User user = userGenerator.save(nn, requiring);
			Patient paciente = patientGenerator.save(requiring);
			Doctor medico = doctorGenerator.save(nn, user, requiring);
			//IAppointment turno = appointmentGenerator.saveforP6(paciente, medico, requiring);
			// Appointment turnoParaPunto3 = appointments.generateForDoctor1234(paciente);
			
			if(logEveryRecord) {
				usersLog.append(user);
				patientsLog.append(paciente);
				doctorsLog.append(medico);
				//appointmentsLog.append(turno);
				// appointmentsLog.append(turnoParaPunto3);
			}
		}
		
		System.out.println("Se generaron " + total + " registros. ");
		
		if(logEveryRecord) {
			System.out.println(DELIMITER);
			System.out.println("Usuarios generados: ");
			System.out.println(usersLog);

			System.out.println(DELIMITER);
			System.out.println("Médicos generados: ");
			System.out.println(doctorsLog);

			System.out.println(DELIMITER);
			System.out.println("Pacientes generados: ");
			System.out.println(patientsLog);

			System.out.println(DELIMITER);
			System.out.println("Turnos generados: ");
			System.out.println(appointmentsLog);
		}
		
    }

}
