package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import daoImpl.AppointmentDAOImpl;
import daoImpl.DataManager;
import daoImpl.PatientDAOImpl;
import daoImpl.UserDAOImpl;
import daoImpl.DoctorDAOImpl;

@Configuration
public class DAOConfig {
	@Bean
	public AppointmentDAOImpl Appointmentpository() {
		return new AppointmentDAOImpl();
	}
	
	@Bean
	public DataManager dataManager() {
		return new DataManager();
	}

	@Bean
	public UserDAOImpl usersrepository() {
		return new UserDAOImpl();
	}
	
	@Bean
	public DoctorDAOImpl doctorrepository() {
		return new DoctorDAOImpl();
	}
	
	@Bean
	public PatientDAOImpl patientsrepository() {
		return new PatientDAOImpl();
	}

}
