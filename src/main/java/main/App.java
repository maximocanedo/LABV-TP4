package main;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.User;
import generator.Generator;
import logic.IAppointmentLogic;
import logic.IDoctorLogic;
import logicImpl.AppointmentLogicImpl;
import logicImpl.DoctorLogicImpl;
import logicImpl.UserLogicImpl;
import resources.Config;


/**
 * Hello world!
 *
 */
public class App {
	
    private UserLogicImpl users;
	private Generator generator;
	
	public App() {
		users = new UserLogicImpl();
		generator = new Generator();
	}
	
	public void main() {
    	// Carga datos
        @SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    	generateFakeRecords(10);
    	//App.punto1();
    	//App.punto2();
    	//App.punto3();
    	//App.punto4();
    	//App.punto5();
    	//App.punto6();
        
       // Doctor doctor = (Doctor) context.getBean("doctor");
        
        //System.out.println(doctor.toString());
        
        Patient paciente = (Patient) context.getBean("paciente");
        
        System.out.println(paciente.toString());
		
	}
	
    public static void main( String[] args ) {
    	App app = new App();
    	app.main();
    }
    
   
	public void generateFakeRecords(int total) {
		System.out.println("Se guardarán los objetos Especialidad. ");
		generator.generateAndSaveRecords();
		System.out.println("Se generarán y guardarán " + total + " registros. ");
		for(int i = 0; i < total; i++) {
			User user = generator.generateAndSaveRandomUser();
			System.out.println(" - " + i + " usuario/s de " + total + ": " + user.getName() + "(@" + user.getUsername() + ")\n");
			Patient paciente = generator.generateAndSaveRandomPaciente();
			System.out.println(" - " + i + " paciente/s de " + total + ": " + paciente.getName() + "; DNI N.º: " + paciente.getDni());
			Doctor medico = generator.generateAndSaveRandomDoctor(user);
			System.out.println(medico);
			System.out.println(" - " + i + " médico/s de " + total + ": " + medico.getName() + "; Legajo N.º: " + medico.getFile());
			Appointment turno = generator.generateTurnoPunto6(paciente, medico);
			System.out.println(turno);
			Appointment turnoParaPunto3 = generator.generateTurnoForDoctor1234(paciente);
			System.out.println(turnoParaPunto3);
		}
		System.out.println("Se generaron " + total + " registros. ");
	}
	
	
}
