package web.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import web.logicImpl.*;

@Configuration
public class LogicConfig {
	
	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Registrar el módulo de JavaTime
        mapper.registerModule(new JavaTimeModule());
        // Configurar para que Jackson no intente serializar con el nuevo formato
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
	
	
/*
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
