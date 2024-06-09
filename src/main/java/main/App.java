package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import entity.Doctor;
import entity.Patient;
import entity.Appointment;
import entity.User;
import generator.Generator;
import logic.IDoctorLogic;
import logic.IAppointmentLogic;
import logic.IUserLogic;
import logicImpl.DoctorLogicImpl;
import logicImpl.PatientLogicImpl;
import logicImpl.AppointmentLogicImpl;
import logicImpl.UserLogicImpl;


/**
 * Hello world!
 *
 */
public class App {
	
	private static IUserLogic usersRepo = new UserLogicImpl();
	
    public static void main( String[] args ) {
    	// Carga datos
    	//generateFakeRecords(10);
    	//App.punto1();
    	//App.punto2();
    	//App.punto3();
    	//App.punto4();
    	//App.punto5();
    	//App.punto6();
        @SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("resources/beans.xml");
        
        //Doctor doctor = (Doctor) context.getBean("doctor");
        
        //doctor.toString();
        
        Patient paciente = (Patient) context.getBean("paciente");
        
        System.out.println(paciente.toString());

    }
    
    private static void punto1() {
    	System.out.println("\n\n-> INICIO PUNTO 1\n\n");
    	DoctorLogicImpl doctorsRepo = new DoctorLogicImpl();
    	List<Doctor> doctors = doctorsRepo.listOrderByFileDescending(1, 10);
    	for(Doctor m : doctors) {
    		System.out.println(m);
    	}
    }
    
	private static void punto2() {
    	System.out.println("\n\n-> INICIO PUNTO 2\n\n");
		IDoctorLogic medicos_repo = new DoctorLogicImpl();
    	List<Object[]> lista_medicos_P2 = medicos_repo.listOnlyFileNumbersAndNames();
    	for(Object[] medico : lista_medicos_P2) {
    		System.out.println("Legajo: " + medico[0] + " Nombre: "+ medico[1] + " Apellido: "+ medico[2]);
    	}
	}
	
	private static void punto3() {
    	System.out.println("\n\n-> INICIO PUNTO 3\n\n");
		IDoctorLogic medicos_repo = new DoctorLogicImpl();

		LocalDate fecha = LocalDate.of(2024, 12, 31);
		LocalDate fecha2 = LocalDate.of(2025, 1, 2);

		List<Object[]> turnosEnRango = medicos_repo.getAppointmentsByDoctorAndDateRange(1234, fecha, fecha2);
        for (Object[] turno : turnosEnRango) {
            System.out.println("Legajo: " + turno[0] + ", Fecha de Alta: " + turno[1] + ", Estado: " + turno[2]);
        }
	}
    
    private static void punto4() {
    	System.out.println("\n\n-> INICIO PUNTO 4\n\n");
    	IDoctorLogic Medicos = new DoctorLogicImpl();
    	List<Integer> lista_medicos_P4 = Medicos.listOnlyFileNumbers();
    	for(Integer LMedico : lista_medicos_P4) {
    		System.out.println(LMedico);
    	}
    }
    
    private static void punto5() {
    	System.out.println("\n\n-> INICIO PUNTO 5\n\n");
    	DoctorLogicImpl logic = new DoctorLogicImpl();
    	Doctor m = logic.findDoctorWithHighestFileNumber();
    	System.out.println(m);
	}
	
	@SuppressWarnings("deprecation")
	private static void punto6() {
    	System.out.println("\n\n-> INICIO PUNTO 6\n\n");
		Date d = new Date();
    	d.setDate(1);
    	d.setMonth(0);
    	d.setYear(124);
    	Date d2 = new Date();
    	d2.setDate(1);
    	d2.setMonth(2);
    	d2.setYear(124);
    	IAppointmentLogic turnos = new AppointmentLogicImpl();
    	int presentes = turnos.countPresencesBetween(d, d2);
    	int ausentes = turnos.countAbsencesBetween(d, d2);
    	double total = presentes + ausentes;
    	double p_p = Math.round((presentes / total) * 10000.0) / 100.0;
    	double a_p = Math.round((ausentes / total) * 10000.0) / 100.0;
    	
    	System.out.println("Presentes: " + presentes + " (" + p_p + "% del total)\nAusentes: " + ausentes + " (" + a_p + "% del total)\nTotal: " + total);
    	
	}
	
	public static void generateFakeRecords(int total) {
		System.out.println("Se guardarán los objetos Especialidad. ");
		Generator.generateAndSaveRecords();
		System.out.println("Se generarán y guardarán " + total + " registros. ");
		for(int i = 0; i < total; i++) {
			User user = Generator.generateAndSaveRandomUser();
			System.out.println(" - " + i + " usuario/s de " + total + ": " + user.getName() + "(@" + user.getUsername() + ")\n");
			Patient paciente = Generator.generateAndSaveRandomPaciente();
			System.out.println(" - " + i + " paciente/s de " + total + ": " + paciente.getName() + "; DNI N.º: " + paciente.getDni());
			Doctor medico = Generator.generateAndSaveRandomDoctor(user);
			System.out.println(medico);
			System.out.println(" - " + i + " médico/s de " + total + ": " + medico.getName() + "; Legajo N.º: " + medico.getFile());
			Appointment turno = Generator.generateTurnoPunto6(paciente, medico);
			System.out.println(turno);
			Appointment turnoParaPunto3 = Generator.generateTurnoForDoctor1234(paciente);
			System.out.println(turnoParaPunto3);
		}
		System.out.println("Se generaron " + total + " registros. ");
	}
	
	/**
	 * Método main usado en el TP4.
	 */
	@SuppressWarnings("unused")
	private static void TP4_Main() {
    	User user = usersRepo.list(1, 1).get(0);
    	System.out.println(user);
    	user.setName("Roberto Castañeda");
    	try {
    		usersRepo.update(user);
    		System.out.println("Se cambió correctamente el nombre. ");
    		System.out.println(user);
        	// Borrar un registro
        	usersRepo.disable(user);
        	System.out.println("Se deshabilitó correctamente el usuario. ");
    	} catch (Exception e) {
    		System.out.println("Error al intentar cambiar el nombre. ");
    	}
    	
    	// Listar médicos
    	IDoctorLogic medicos_repo = new DoctorLogicImpl();
    	List<Doctor> medicos = medicos_repo.list(1, 10);
    	for(Doctor medico : medicos) {
    		System.out.println(medico);
    	}
    	
    	PatientLogicImpl paciente_repo = new PatientLogicImpl();
    	List<Patient> pacientes = paciente_repo.list(1, 10);
    	for(Patient paciente : pacientes) {
    		System.out.println(paciente);
    	}
	}
	
}
