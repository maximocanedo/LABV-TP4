package generator;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.User;

public class Generator {
	
	private static String DELIMITER = "\n**************************\n";
	
	private SpecialtyGenerator specialties;
	private AppointmentGenerator appointments;
	private DoctorGenerator doctors;
	private PatientGenerator patients;
	private UserGenerator users;

	// TODO Reemplazar por beanss.
    public Generator() {
    	specialties = new SpecialtyGenerator();
    	appointments = new AppointmentGenerator();
    	doctors = new DoctorGenerator();
    	patients = new PatientGenerator();
    	users = new UserGenerator();
    }
    
    public void generate(int total) {
    	generate(total, false);
    }
    
    public void generate(int total, boolean logEveryRecord) {
		System.out.println("Se guardarán los objetos Especialidad. ");
		specialties.save();
		System.out.println("Se generarán y guardarán " + total + " registros. ");

		StringBuilder usersLog = new StringBuilder();
		StringBuilder patientsLog = new StringBuilder();
		StringBuilder doctorsLog = new StringBuilder();
		StringBuilder appointmentsLog = new StringBuilder();
		
		
		for(int i = 0; i < total; i++) {
			// Generar
			User user = users.save();
			Patient paciente = patients.save();
			Doctor medico = doctors.save(user);
			Appointment turno = appointments.saveforP6(paciente, medico);
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
