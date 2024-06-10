package resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import generator.AppointmentGenerator;
import generator.DoctorGenerator;
import generator.Generator;
import generator.PatientGenerator;
import generator.SpecialtyGenerator;
import generator.UserGenerator;

@Configuration
public class GeneratorConfig {
	
	@Bean 
    public SpecialtyGenerator specialtyGenerator() {
        return new SpecialtyGenerator();
    }

    @Bean
    public AppointmentGenerator appointmentGenerator() {
        return new AppointmentGenerator();
    }

    @Bean
    public DoctorGenerator doctorGenerator() {
        return new DoctorGenerator();
    }

    @Bean
    public PatientGenerator patientGenerator() {
        return new PatientGenerator();
    }

    @Bean
    public UserGenerator userGenerator() {
        return new UserGenerator();
    }
    
    @Bean
    public Generator generator() {
        return new Generator();
    }
    
}
