package main;

import generator.Generator;
import resources.Context;

public class App {

    private Generator generator;
    private Context context;

    public App() {
        // Cargar beans
        context = new Context();
        generator = context.getBean(Generator.class);  // Obtener Generator desde Spring
    }

    public void main() {		
        // Carga datos
        generateFakeRecords(10);

        // Beans
        //Doctor doctor = (Doctor) context.getBean("doctor");
        //System.out.println(doctor.toString());
        //Patient paciente = (Patient) context.getBean("paciente");
        //System.out.println(paciente);
    }

    public static void main(String[] args) {
        App app = new App();
        app.main();
    }

    public void generateFakeRecords(int total) {
        generator.generate(total, true);
    }
}
