package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import daoImpl.DataManager;
import daoImpl.UserDAOImpl;

@Configuration
public class DAOConfig {
	
	@Bean
	public DataManager dataManager() {
		return new DataManager();
	}

	@Bean
	public UserDAOImpl usersrepository() {
		return new UserDAOImpl();
	}

}
