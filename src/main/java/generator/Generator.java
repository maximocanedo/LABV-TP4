package generator;

import com.github.javafaker.Faker;

import entity.Paciente;
import entity.User;
import logic.IPacienteLogic;
import logic.IUserLogic;
import logicImpl.PacienteLogicImpl;
import logicImpl.UserLogicImpl;

public class Generator {

	private static IUserLogic users = new UserLogicImpl();
	private static IPacienteLogic pacientes = new PacienteLogicImpl();
	
	public static Paciente generateRandomPaciente() {
		Faker faker = new Faker();
		Paciente paciente = new Paciente();
		paciente.setNombre(faker.name().firstName());
		paciente.setApellido(faker.name().lastName());
		paciente.setDni(45489657);
		paciente.setTelefono(faker.phoneNumber().cellPhone());
		paciente.setDireccion(faker.address().streetAddress());
		paciente.setLocalidad(faker.address().city());
		paciente.setProvincia(faker.address().state());
		paciente.setFechaNacimiento(faker.date().birthday());
		paciente.setCorreo(faker.internet().emailAddress());
		return paciente;
	}
	
	public static User generateRandomUser() {
		Faker faker = new Faker();
		User user = new User();
		user.setName(faker.name().fullName());
		user.setUsername(faker.name().username());
		user.setPassword("12345678"); // Todos los usuarios generados automáticamente usarán esta contraseña.
		user.setActiveState(true);
		return user;
	}
	
	public static User generateAndSaveRandomUser() {
		User random = generateRandomUser();
		users.signup(random);
		return random;
	}
	
	public static Paciente generateAndSaveRandomPaciente() {
		Paciente random = generateRandomPaciente();
		pacientes.signupPaciente(random);
		return random;
	}
	
}
