package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import logic.IUserPermitLogic;
import logicImpl.AppointmentLogicImpl;
import logicImpl.PatientLogicImpl;
import logicImpl.SpecialtyLogicImpl;
import logicImpl.TicketLogicImpl;
import logicImpl.UserLogicImpl;
import logicImpl.UserPermitLogicImpl;
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
	public UserPermitLogicImpl permits() {
		return new UserPermitLogicImpl();
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
