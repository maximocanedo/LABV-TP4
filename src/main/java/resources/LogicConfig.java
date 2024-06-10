package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import logicImpl.*;

@Configuration
public class LogicConfig {
	
	@Bean
	public AppointmentLogicImpl appointments() {
		return new AppointmentLogicImpl();
	}
	
	@Bean
	public DoctorLogicImpl doctors() {
		return new DoctorLogicImpl();
	}
	
	@Bean
	public PatientLogicImpl patients() {
		return new PatientLogicImpl();
	}
	
	@Bean
	public SpecialtyLogicImpl specialties() {
		return new SpecialtyLogicImpl();
	}
	
	@Bean
	public UserLogicImpl users() {
		return new UserLogicImpl();
	}
	
}
