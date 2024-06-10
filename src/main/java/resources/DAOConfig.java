package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import daoImpl.AppointmentDAOImpl;
import daoImpl.DataManager;
import daoImpl.DoctorDAOImpl;
import daoImpl.PatientDAOImpl;
import daoImpl.SpecialtyDAOImpl;
import daoImpl.UserDAOImpl;

@Configuration
public class DAOConfig {
	
	@Bean
	public AppointmentDAOImpl appointmentsrepository() {
		return new AppointmentDAOImpl();
	}
	
	@Bean
	public DataManager dataManager() {
		return new DataManager();
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
	}
	
	@Bean
	public UserDAOImpl usersrepository() {
		return new UserDAOImpl();
	}
	
}
