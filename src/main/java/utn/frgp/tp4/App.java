package utn.frgp.tp4;

import java.util.List;

import entity.User;
import generator.Generator;
import logic.IUserLogic;
import logicImpl.UserLogicImpl;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void generateUsers(int q) {
		for(int i = 0; i < q; i++) {
			Generator.generateAndSaveRandomUser();
		}
	}
	
    public static void main( String[] args ) {
    	IUserLogic users_repo = new UserLogicImpl();
    	System.out.println("Se generarán y guardarán diez registros. ");
    	generateUsers(10);
    	System.out.println("Se generaron diez registros. ");
    	List<User> users = users_repo.list(1, 30);
    	for(User user : users) {
    		System.out.println(user);
    	}
    }
}
