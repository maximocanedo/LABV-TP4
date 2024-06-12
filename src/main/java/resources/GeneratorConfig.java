package resources;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

import generator.Generator;
import generator.PatientGenerator;
import generator.UserGenerator;
import generator.AppointmentGenerator;

@Configuration
public class GeneratorConfig {
	@Bean
	public AppointmentGenerator AppointmentGeneratorGenerator() {
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
	
	@Bean
	public AppointmentGenerator AppointmentGenerator() {
		return new AppointmentGenerator();
	}
}
