package main;

import entity.Optional;
import entity.User;
import exceptions.InvalidCredentialsException;
import exceptions.NotFoundException;
import generator.Generator;
import logicImpl.TicketLogicImpl;
import logicImpl.UserLogicImpl;
import resources.Context;

public class App {
	
	private Generator generator;
	private Context context;
	private TicketLogicImpl tickets;
	private UserLogicImpl users;
	
	// Constructor, no usar como main. 
	public App() {
		context = new Context(); // Los beans se cargan en este punto.
		generator = context.getBean(Generator.class);
		tickets = context.getBean(TicketLogicImpl.class);
		users = context.getBean(UserLogicImpl.class);
	}

	/**
	 * Función principal. 
	 */
	public void main() {
		String username = "abe.bogan";
		String password = "12345678";
		//String n = "GoogleChrome";
		//String s = "HI32UH42I3U2OP/34";
		
		String e = "";
		try {
			e = users.login(username, password);
		} catch (InvalidCredentialsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		System.out.println(e);
		
    	//generateFakeRecords(10);
	}
	
    public static void main( String[] args ) {
    	App app = new App();
    	app.main();
    }
    
   
	public void generateFakeRecords(int total) {
		generator.generate(total, true);
	}
	
	
}
