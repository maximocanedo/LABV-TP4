package utn.frgp.tp4;

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
    	//App.punto1();
    	//App.punto2();
    	//App.punto3();
    	App.punto4();
    	//App.punto5();
    	//App.punto6();
    }
    
    private static void punto4() {
    	///String IDMedico;
    	IMedicoLogic Medicos = new MedicoLogicImpl();
    	List<String> lista_medicos_P4 = Medicos.TodosMedicosXLegajoP4();
    	for(String IDMedico : lista_medicos_P4) {
    		System.out.println(IDMedico);
    	}
    }
    
    private static void punto5() {
		IMedicoLogic medicos_repo = new MedicoLogicImpl();
    	// Medico medico_P5 = medicos_repo.medicoMayorLegajoP5();
    	//System.out.println("Legajo: " + medico_P5[0] + " Nombre: "+ medico_P5[1] + " Apellido: "+ medico_P5[2]);
	}
	
	private static void punto2() {
		IMedicoLogic medicos_repo = new MedicoLogicImpl();
    	List<Object[]> lista_medicos_P2 = medicos_repo.listMedicosLegajoAscP2();
    	for(Object[] medico : lista_medicos_P2) {
    		System.out.println("Legajo: " + medico[0] + " Nombre: "+ medico[1] + " Apellido: "+ medico[2]);
    	}
	}
	
	@SuppressWarnings("deprecation")
	private static void punto6() {
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
	
	public static void generateFakeRecords(int q) {
		System.out.println("Se guardarán los objetos Especialidad. ");
		Generator.generateAndSaveRecords();
		System.out.println("Se generarán y guardarán " + q + " registros. ");
		for(int i = 0; i < q; i++) {
			User u = Generator.generateAndSaveRandomUser();
			System.out.println(" - " + i + " usuario/s de " + q + ": " + u.getName() + "(@" + u.getUsername() + ")\n");
			Paciente p = Generator.generateAndSaveRandomPaciente();
			System.out.println(" - " + i + " paciente/s de " + q + ": " + p.getNombre() + "; DNI N.º: " + p.getDni());
			Medico m = Generator.generateAndSaveRandomDoctor(u);
			System.out.println(" - " + i + " médico/s de " + q + ": " + m.getNombre() + "; Legajo N.º: " + m.getLegajo());
			Turno t = Generator.generateTurnoPunto6(p, m);
			System.out.println(t);
		}
		System.out.println("Se generaron " + q + " registros. ");
	}
	
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
