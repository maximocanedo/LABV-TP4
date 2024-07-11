package web.main;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import web.daoImpl.DoctorDAOImpl;
import web.entity.User;
import web.generator.Generator;
import web.resources.Context;

public class App {
	
	private Generator generator;
	private Context context;
	private DoctorDAOImpl doctorsrepository;
	
	// Constructor, no usar como main. 
	public App() {
		context = new Context(); // Los beans se cargan en este punto.
		generator = context.getBean(Generator.class);
		doctorsrepository = context.getBean(DoctorDAOImpl.class);
	}

	/**
	 * Función principal. 
	 */
	public void main() {
		//String username = "alfonso.walker";
		//String password = "12345678";
		
		//String refreshToken = "";
		//String accessToken = "";
		try {
			Calendar c = new Calendar.Builder().setDate(2024, 0, 15).build();
			Calendar c2 = new Calendar.Builder().setDate(2024, 0, 10).build();
			
			List<LocalTime> l = doctorsrepository.getFreeTimeForDoctor(3739, c.getTime());
			List<Date> d = doctorsrepository.getScheduleForDoctor(3739, c2);
			
			for(LocalTime t : l ) {
				System.out.println("DISPONIBLE: " + t);
			}
			
			for(Date dt : d) 
				System.out.println("Fecha disponible: " + dt);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
    public static void main( String[] args ) {
    	App app = new App();
    	app.main();
    }
    
   
	public void generateFakeRecords(int total, User requiring) {
		generator.generate(total, true, requiring);
	}
	
	
}
