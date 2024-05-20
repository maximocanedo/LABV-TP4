package utn.frgp.tp4;

import java.util.List;

import entity.Medico;
import entity.Paciente;
import entity.Turno;
import entity.User;
import generator.Generator;
import logic.IMedicoLogic;
import logic.IUserLogic;
import logicImpl.MedicoLogicImpl;
import logicImpl.PacienteLogicImpl;
import logicImpl.UserLogicImpl;

/**
 * Hello world!
 *
 */
public class App {
	
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
			Turno t = Generator.generateTurno(p, m);
			System.out.println(t);
		}
		System.out.println("Se generaron " + q + " registros. ");
	}
	
	private static IUserLogic usersRepo = new UserLogicImpl();
	
    public static void main( String[] args ) {
    	// Carga datos
    	generateFakeRecords(10);

    	
    	// Modificar algún registro
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
