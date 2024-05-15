package generator;

import com.github.javafaker.Faker;

import entity.User;
import logic.IUserLogic;
import logicImpl.UserLogicImpl;

public class Generator {

	private static IUserLogic users = new UserLogicImpl();
	
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
	
}
