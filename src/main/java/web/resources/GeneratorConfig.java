package web.resources;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

import web.generator.*;

@Configuration
public class GeneratorConfig {
	
	/* @Bean
	public AppointmentGenerator appointmentGenerator() {
		return new AppointmentGenerator();
	}
	
	@Bean
	public UserGenerator userGenerator() {
		return new UserGenerator();
	}
	
	@Bean
	public PatientGenerator patientGenerator() {
		return new PatientGenerator();		
	}
	
	@Bean
	public DoctorGenerator doctorGenerator() {
		return new DoctorGenerator();		
	}
	
	@Bean
	public SpecialtyGenerator specialtyGenerator() {
		return new SpecialtyGenerator();
	}// */

	@Bean
	public Generator generator() {
		return new Generator();
	}
	
	@Bean
	public Faker faker() {
		return new Faker();
	}
	
	@Bean
	public Random random() {
		return new Random();
	}
	
}
