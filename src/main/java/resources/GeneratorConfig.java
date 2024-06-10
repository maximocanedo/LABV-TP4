package resources;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

import generator.Generator;
import generator.UserGenerator;

@Configuration
public class GeneratorConfig {
	
	@Bean
	public UserGenerator userGenerator() {
		return new UserGenerator();
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
}
