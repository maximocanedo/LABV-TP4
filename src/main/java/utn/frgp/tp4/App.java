package utn.frgp.tp4;

import java.util.List;

import entity.Paciente;
import entity.User;
import generator.Generator;
import logic.IUserLogic;
import logicImpl.PacienteLogicImpl;
import logicImpl.UserLogicImpl;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void generateUsers(int q) {
		for(int i = 0; i < q; i++)
			Generator.generateAndSaveRandomUser();
			Generator.generateAndSaveRandomPaciente();
	}
	
    public static void main( String[] args ) {
    	//UserLogicImpl users_repo = new UserLogicImpl();
    	//System.out.println("Se generarán y guardarán diez registros. ");
    	generateUsers(10);
    	//System.out.println("Se generaron diez registros. ");
    	/*List<User> users = users_repo.list(1, 30);
    	for(User user : users) {
    		System.out.println(user);
    	}*/
    	//User user = users_repo.findByUsername("abby.hessel");
    	//if(user == null) {
    	//	System.out.println("@abby.hessel not found.");
    	//	return;
    	//}
    	//System.out.println(user);
    	//user.setName("Alessandra Caspio");
    	//users_repo.update(user);

    	//users_repo.disable(user);
    	//User user2 = users_repo.findByUsername("abby.hessel");
    	//if(user2 == null) {
    	//	System.out.println("@abby.hessel not found.");
    	//	return;
    	//}
    	//System.out.println(user2);
    	PacienteLogicImpl paciente_repo = new PacienteLogicImpl();
    	List<Paciente> pacientes = paciente_repo.list(1, 30);
    	for(Paciente paciente : pacientes) {
    		System.out.println(paciente);
    	}
    }
}
