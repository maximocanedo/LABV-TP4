package main;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.User;
import generator.Generator;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("resources/beans.xml");

        Generator generator = context.getBean(Generator.class);
        generateFakeRecords(generator, 10);
        ((ClassPathXmlApplicationContext) context).close();
    }

    public static void generateFakeRecords(Generator generator, int total) {
        System.out.println("Se guardarán los objetos Especialidad. ");
        Generator.generateAndSaveRecords();
        System.out.println("Se generarán y guardarán " + total + " registros. ");
        for (int i = 0; i < total; i++) {
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
}
