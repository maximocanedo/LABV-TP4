package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import logicImpl.AppointmentLogicImpl;
import logicImpl.PatientLogicImpl;
import logicImpl.UserLogicImpl;
import logicImpl.DoctorLogicImpl;

@Configuration
public class LogicConfig {

	@Bean
	public AppointmentLogicImpl Appointment() {
		return new AppointmentLogicImpl();
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
	
}
