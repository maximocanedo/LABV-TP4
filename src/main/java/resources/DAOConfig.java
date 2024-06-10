package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import daoImpl.UserDAOImpl;

@Configuration
public class DAOConfig {

	@Bean
	public UserDAOImpl usersrepository() {
		return new UserDAOImpl();
	}

}
