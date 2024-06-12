package generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.User;

@Component
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

	// TODO Reemplazar por beans.
    public Generator() {}
    
    public void generate(int total) {
    	generate(total, false);
    }
    
    public void generate(int total, boolean logEveryRecord) {
		System.out.println("Se guardarán los objetos Especialidad. ");
		specialtyGenerator.save();
		System.out.println("Se generarán y guardarán " + total + " registros. ");

		StringBuilder usersLog = new StringBuilder();
		StringBuilder patientsLog = new StringBuilder();
		StringBuilder doctorsLog = new StringBuilder();
		StringBuilder appointmentsLog = new StringBuilder();
		
		
		for(int i = 0; i < total; i++) {
			// Generar
			User user = userGenerator.save();
			Patient paciente = patientGenerator.save();
			Doctor medico = doctorGenerator.save(user);
			Appointment turno = appointmentGenerator.saveforP6(paciente, medico);
			// Appointment turnoParaPunto3 = appointments.generateForDoctor1234(paciente);
			
			if(logEveryRecord) {
				usersLog.append(user);
				patientsLog.append(paciente);
				doctorsLog.append(medico);
				appointmentsLog.append(turno);
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
