package web.resources;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import web.daoImpl.*;

@Configuration
public class DAOConfig {
	
		

	@Bean
	public DataManager dataManager() {
		return new DataManager();
	}
	/* 
	@Bean
	public AppointmentDAOImpl appointmentsrepository() {
		return new AppointmentDAOImpl();
	}
	
	@Bean
	public TicketDAOImpl ticketsrepository() {
		return new TicketDAOImpl();
	}
	

	@Bean
	public UserDAOImpl usersrepository() {
		return new UserDAOImpl();
	}
	
	@Bean
	public UserPermitDAOImpl userpermitsrepository() {
		return new UserPermitDAOImpl();
	}
	
	@Bean
	public DoctorDAOImpl doctorsrepository() {
		return new DoctorDAOImpl();
	}
	
	@Bean
	public PatientDAOImpl patientsrepository() {
		return new PatientDAOImpl();
	}

	@Bean
	public SpecialtyDAOImpl specialtiesrepository() {
		return new SpecialtyDAOImpl();
	}//*/
	
}
