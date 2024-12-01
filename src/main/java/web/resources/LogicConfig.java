package web.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import web.logicImpl.SpecialtyLogicImpl;

import web.logicImpl.*;
import web.logic.validator.*;

@Configuration
public class LogicConfig {
	
	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
	
	/*@Bean
	public AppointmentValidator appointmentValidator() {
		return new AppointmentValidator();
	}
	
	@Bean 
	public DoctorValidator doctorValidator() {
		return new DoctorValidator();
	}
	
	@Bean
	public PatientValidator patientValidator() {
		return new PatientValidator();
	}
	
	@Bean
	public ScheduleValidator scheduleValidator() {
		return new ScheduleValidator();
	}
	
	@Bean
	public SpecialtyValidator specialtyValidator() {
		return new SpecialtyValidator();
	}
	
	@Bean
	public UserValidator userValidator() {
		return new UserValidator();
	}
	
///*
	@Bean
	public AppointmentLogicImpl appointments() {
		return new AppointmentLogicImpl();
	}
	
	@Bean
	public TicketLogicImpl tickets() {
		return new TicketLogicImpl();
	}
	
	@Bean
	public UserLogicImpl users() {
		return new UserLogicImpl();
	}
	
	@Bean
	public UserPermitLogicImpl permits() {
		return new UserPermitLogicImpl();
	}
	
	@Bean
	public PatientLogicImpl patients() {
		return new PatientLogicImpl();
	}
	
	@Bean
	public DoctorLogicImpl doctors() {
		return new DoctorLogicImpl();
	}
	
	@Bean
	public SpecialtyLogicImpl specialties() {
		return new SpecialtyLogicImpl();
	}
	//*/
}
