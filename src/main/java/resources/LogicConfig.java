package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import logicImpl.PatientLogicImpl;
import logicImpl.UserLogicImpl;

@Configuration
public class LogicConfig {

	@Bean
	public UserLogicImpl users() {
		return new UserLogicImpl();
	}
	
	@Bean
	private PatientLogicImpl patients() {
		return new PatientLogicImpl();
	}
	
}
