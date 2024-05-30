package utn.frgp.tp4;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import entity.Medico;
import entity.Paciente;
import entity.Turno;
import entity.User;
import generator.Generator;
import logic.IMedicoLogic;
import logic.ITurnoLogic;
import logic.IUserLogic;
import logicImpl.MedicoLogicImpl;
import logicImpl.PacienteLogicImpl;
import logicImpl.TurnoLogicImpl;
import logicImpl.UserLogicImpl;

/**
 * Hello world!
 *
 */
public class App {
	
	private static IUserLogic usersRepo = new UserLogicImpl();
	
    public static void main( String[] args ) {
    	// Carga datos
    	generateFakeRecords(10);
    	App.punto1();
    	App.punto2();
    	App.punto3();
    	App.punto4();
    	App.punto5();
    	App.punto6();
    }
    
    private static void punto1() {
    	System.out.println("\n\n-> INICIO PUNTO 1\n\n");
    	MedicoLogicImpl doctorsRepo = new MedicoLogicImpl();
    	List<Medico> doctors = doctorsRepo.listOrderByFileDescending(1, 10);
    	for(Medico m : doctors) {
    		System.out.println(m);
    	}
    }
    
	private static void punto2() {
    	System.out.println("\n\n-> INICIO PUNTO 2\n\n");
		IMedicoLogic medicos_repo = new MedicoLogicImpl();
    	List<Object[]> lista_medicos_P2 = medicos_repo.listOnlyFileNumbersAndNames();
    	for(Object[] medico : lista_medicos_P2) {
    		System.out.println("Legajo: " + medico[0] + " Nombre: "+ medico[1] + " Apellido: "+ medico[2]);
    	}
	}
	
	private static void punto3() {
    	System.out.println("\n\n-> INICIO PUNTO 3\n\n");
		IMedicoLogic medicos_repo = new MedicoLogicImpl();

		LocalDate fecha = LocalDate.of(2024, 12, 31);
		LocalDate fecha2 = LocalDate.of(2025, 1, 2);

		List<Object[]> turnosEnRango = medicos_repo.getAppointmentsByDoctorAndDateRange(1234, fecha, fecha2);
        for (Object[] turno : turnosEnRango) {
            System.out.println("Legajo: " + turno[0] + ", Fecha de Alta: " + turno[1] + ", Estado: " + turno[2]);
        }
	}
    
    private static void punto4() {
    	System.out.println("\n\n-> INICIO PUNTO 4\n\n");
    	IMedicoLogic Medicos = new MedicoLogicImpl();
    	List<Integer> lista_medicos_P4 = Medicos.listOnlyFileNumbers();
    	for(Integer LMedico : lista_medicos_P4) {
    		System.out.println(LMedico);
    	}
    }
    
    private static void punto5() {
    	System.out.println("\n\n-> INICIO PUNTO 5\n\n");
    	MedicoLogicImpl logic = new MedicoLogicImpl();
    	Medico m = logic.findDoctorWithHighestFileNumber();
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
    	ITurnoLogic turnos = new TurnoLogicImpl();
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
			Paciente paciente = Generator.generateAndSaveRandomPaciente();
			System.out.println(" - " + i + " paciente/s de " + total + ": " + paciente.getNombre() + "; DNI N.º: " + paciente.getDni());
			Medico medico = Generator.generateAndSaveRandomDoctor(user);
			System.out.println(" - " + i + " médico/s de " + total + ": " + medico.getNombre() + "; Legajo N.º: " + medico.getLegajo());
			Turno turno = Generator.generateTurnoPunto6(paciente, medico);
			System.out.println(turno);
			Turno turnoParaPunto3 = Generator.generateTurnoForDoctor1234(paciente);
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
    	boolean s = usersRepo.update(user);
    	if(s) {
    		System.out.println("Se cambió correctamente el nombre. ");
    		System.out.println(user);
    	} else {
    		System.out.println("Error al intentar cambiar el nombre. ");
    	}
    	// Borrar un registro
    	usersRepo.disable(user);
    	System.out.println("Se deshabilitó correctamente el usuario. ");
    	
    	// Listar médicos
    	IMedicoLogic medicos_repo = new MedicoLogicImpl();
    	List<Medico> medicos = medicos_repo.list(1, 10);
    	for(Medico medico : medicos) {
    		System.out.println(medico);
    	}
    	
    	PacienteLogicImpl paciente_repo = new PacienteLogicImpl();
    	List<Paciente> pacientes = paciente_repo.list(1, 10);
    	for(Paciente paciente : pacientes) {
    		System.out.println(paciente);
    	}
	}
	
}
