package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import logicImpl.AppointmentLogicImpl;
import logicImpl.PatientLogicImpl;
import logicImpl.SpecialtyLogicImpl;
import logicImpl.TicketLogicImpl;
import logicImpl.UserLogicImpl;
import logicImpl.DoctorLogicImpl;

@Configuration
public class LogicConfig {

	@Bean
	public AppointmentLogicImpl appointments() {
		return new AppointmentLogicImpl();
	}
	
	@Bean
	public TicketLogicImpl tickets() {
		return new TicketLogicImpl();
	}
	
	@Bean
	public UserLogicImpl users() {
		return new UserLogicImpl();
	}
	
	@Bean
	public PatientLogicImpl patients() {
		return new PatientLogicImpl();
	}
	
	@Bean
	public DoctorLogicImpl doctors() {
		return new DoctorLogicImpl();
	}
	
	@Bean
	public SpecialtyLogicImpl specialties() {
		return new SpecialtyLogicImpl();
	}
	
}
