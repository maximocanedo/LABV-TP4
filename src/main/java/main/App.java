package main;

import generator.Generator;
import resources.Context;

public class App {
	
	private Generator generator;
	private Context context;
	
	// Constructor, no usar como main. 
	public App() {
		context = new Context(); // Los beans se cargan en este punto.
		generator = context.getBean(Generator.class);
	}

	/**
	 * Función principal. 
	 */
	public void main() {
    	generateFakeRecords(10);
	}
	
    public static void main( String[] args ) {
    	App app = new App();
    	app.main();
    }
    
   
	public void generateFakeRecords(int total) {
		generator.generate(total, true);
	}
	
	
}
