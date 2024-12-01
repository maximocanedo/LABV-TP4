package web.main;
import web.entity.User;
import web.generator.Generator;
import web.logicImpl.UserLogicImpl;
import web.resources.Context;


public class App {
	
	private Generator generator;
	private UserLogicImpl users;
	private Context context;
	
	// Constructor, no usar como main. 
	public App() {
		context = new Context(); // Los beans se cargan en este punto.
		generator = context.getBean(Generator.class);
		users = context.getBean(UserLogicImpl.class);
	}

	/**
	 * Función principal. 
	 */
	public void main() {
		String username = "alfonso.walker";
		String password = "12345678";
		
		System.out.println("Iniciado con éxito. ");
		
		if(users == null) System.out.println("Es null!");
		User root = users.getRootUser();
		generator.generate(32, true, root);
	}
	
    public static void main( String[] args ) {
    	App app = new App();
    	app.main();
    }
    
   
	public void generateFakeRecords(int total, User requiring) {
		// generator.generate(total, true, requiring);
	}
	
	
}
