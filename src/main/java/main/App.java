package main;

import entity.User;
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
		
		String refreshToken = "";
		String accessToken = "";
		try {
			//refreshToken = users.login(username, password);
			accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIzIiwiaWF0IjoxNzE4OTY2NDE0LCJleHAiOjE3MTg5NjcwMTR9.SqKSKgHZWIeK2IPWIE-FXsMIwf0F9m5IUaRwUGPbQ9VvFv9bdT7nQNxWFW4wl1PH";
			//tickets.generateAccessToken(refreshToken);
			User u = tickets.validateAccessToken(accessToken);
			System.out.println(u);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		System.out.println(
			"Refresh token: " + refreshToken + "\nAccess Token: " + accessToken
		);
		
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
