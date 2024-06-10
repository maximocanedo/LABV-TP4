package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import logicImpl.UserLogicImpl;

@Configuration
public class LogicConfig {

	@Bean
	public UserLogicImpl users() {
		return new UserLogicImpl();
	}
	
}
