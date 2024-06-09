package resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import daoImpl.UserDAOImpl;
import entity.Day;
import entity.Doctor;
import entity.Patient;
import entity.Schedule;
import entity.Specialty;
import entity.User;
import logicImpl.UserLogicImpl;

@Configuration
public class Config {
	
	@Bean 
	public UserDAOImpl userRepository() {
		return new UserDAOImpl();
	}
	
	@Bean
	public UserLogicImpl users() {
		return new UserLogicImpl();
	}
	
	@Bean
	public Doctor doctor() throws ParseException {
		Doctor doctor = new Doctor();
		
		/*
		doctor.setId(1);
		doctor.setFile(12132);
		doctor.setName("Juan");
		doctor.setSurname("Perez");
		doctor.setSex("Masculino");
		doctor.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1980-08-30"));
		doctor.setAddress("Avenida Siempre Viva");
		doctor.setLocalty("General Pacheco");
		doctor.setEmail("juanperez@example.com");
		doctor.setPhone("+5491126659874");
		Specialty especialidad = new Specialty();
		especialidad.setId(1);
		especialidad.setName("Cardiologia");
		doctor.setSpecialty(especialidad);
		User usuario = new User();
		usuario.setUsername("jperez");
		usuario.setName("Juan Perez");
		usuario.setPassword("password");
		Schedule schedule = new Schedule();
		schedule.setId(1);
		schedule.setBeginDay(Day.MONDAY);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		schedule.setStartTime(LocalDateTime.parse("2024-05-08 17:00:00",fmt).toLocalTime());
		schedule.setEndTime(LocalDateTime.parse("2024-05-08 21:00:00",fmt).toLocalTime());
		Set<Schedule> schedules = new HashSet<Schedule>();
		schedules.add(schedule);
		doctor.setSchedules(schedules);
		return doctor;
		//*/
		return null;
	}
	@Bean
	public Patient paciente() throws ParseException {
		Patient paciente = new Patient();
		paciente.setId(1);
		paciente.setName("Pedro");
		paciente.setSurname("Escalas");
		paciente.setDni(37855964);
		paciente.setPhone("+5491136659897");
		paciente.setAddress("Avenida Santa Fe 2036");
		paciente.setLocalty("Vicente Lopez");
		paciente.setProvince("Buenos Aires");
		paciente.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1999-09-26"));
		paciente.setEmail("pedroescalas@example.com");
		return paciente;
	}
}
