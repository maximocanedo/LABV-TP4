package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import entity.Patient;
import generator.Generator;
import resources.Config;

public class App {
	
	private Generator generator;
	
	public App() {
		generator = new Generator();
	}

    @SuppressWarnings("resource")
	public void main() {
    	// Cargar beans
		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
    	// Carga datos
    	generateFakeRecords(10);
    	
    	// Beans
    	//Doctor doctor = (Doctor) context.getBean("doctor");
        //System.out.println(doctor.toString());
        Patient paciente = (Patient) context.getBean("paciente");
        System.out.println(paciente);
	}
	
    public static void main( String[] args ) {
    	App app = new App();
    	app.main();
    }
    
   
	public void generateFakeRecords(int total) {
		generator.generate(total, true);
	}
	
	
}
