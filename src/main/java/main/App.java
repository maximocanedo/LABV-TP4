package main;

import entity.Optional;
import entity.Patient;
import entity.Permit;
import entity.User;
import generator.Generator;
import logic.ITicketLogic;
import logicImpl.PatientLogicImpl;
import logicImpl.TicketLogicImpl;
import logicImpl.UserLogicImpl;
import logicImpl.UserPermitLogicImpl;
import resources.Context;

public class App {
	
	private Generator generator;
	private Context context;
	private ITicketLogic tickets;
	private UserLogicImpl users;
	private PatientLogicImpl patients;
	private UserPermitLogicImpl permits;
	
	// Constructor, no usar como main. 
	public App() {
		context = new Context(); // Los beans se cargan en este punto.
		generator = context.getBean(Generator.class);
		tickets = context.getBean(TicketLogicImpl.class);
		users = context.getBean(UserLogicImpl.class);
		patients = context.getBean(PatientLogicImpl.class);
		permits = context.getBean(UserPermitLogicImpl.class);
	}

	/**
	 * Función principal. 
	 */
	public void main() {
		String username = "abe.bogan";
		String password = "12345678";
		permits.reject(username, Permit.DISABLE_PATIENT);
		
		String refreshToken = "";
		String accessToken = "";
		try {
			refreshToken = users.login(username, password);
			//accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIzIiwiaWF0IjoxNzE4OTY2NDE0LCJleHAiOjE3MTg5NjcwMTR9.SqKSKgHZWIeK2IPWIE-FXsMIwf0F9m5IUaRwUGPbQ9VvFv9bdT7nQNxWFW4wl1PH";
			accessToken = tickets.generateAccessToken(refreshToken);
			User u = tickets.validateAccessToken(accessToken);
			patients.disable(1, u);
			//if(p.isPresent()) System.out.println(p.get());
			System.out.println(u);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		/*System.out.println(
			"Refresh token: " + refreshToken + "\nAccess Token: " + accessToken
		);*/
		
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
