package web.main;
import web.entity.User;
import web.generator.Generator;
import web.logic.ITicketLogic;
import web.logicImpl.PatientLogicImpl;
import web.logicImpl.TicketLogicImpl;
import web.logicImpl.UserLogicImpl;
import web.logicImpl.UserPermitLogicImpl;
import web.resources.Context;

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
		
		String refreshToken = "";
		String accessToken = "";
		try {
			refreshToken = users.login(username, password);
			accessToken = tickets.generateAccessToken(refreshToken);
			User me = tickets.validateAccessToken(accessToken);
			
			System.out.println(me);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
    public static void main( String[] args ) {
    	App app = new App();
    	app.main();
    }
    
   
	public void generateFakeRecords(int total) {
		generator.generate(total, true);
	}
	
	
}
