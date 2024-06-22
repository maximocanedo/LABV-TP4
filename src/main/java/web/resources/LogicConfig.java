package web.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import web.logic.ITicketLogic;
import web.logic.IUserPermitLogic;
import web.logicImpl.AppointmentLogicImpl;
import web.logicImpl.DoctorLogicImpl;
import web.logicImpl.PatientLogicImpl;
import web.logicImpl.SpecialtyLogicImpl;
import web.logicImpl.TicketLogicImpl;
import web.logicImpl.UserLogicImpl;
import web.logicImpl.UserPermitLogicImpl;

@Configuration
public class LogicConfig {

	@Bean
	public AppointmentLogicImpl appointments() {
		return new AppointmentLogicImpl();
	}
	
	@Bean
	public ITicketLogic tickets() {
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
