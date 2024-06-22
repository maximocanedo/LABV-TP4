package web.generator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import web.entity.User;
import web.logicImpl.UserLogicImpl;

@Component
public class UserGenerator implements IEntityGenerator<User> {

	@Autowired
    private UserLogicImpl users;
	
	@Autowired
    private Faker faker;
    
    public UserGenerator() {}
    
	@Override
	public User generate() {
        User user = new User();
        user.setName(faker.name().fullName());
        user.setUsername(faker.name().username());
        user.setPassword("12345678"); // Todos los usuarios generados automáticamente usarán esta contraseña.
        user.setActive(true);
        return user;
	}

	@Override
	public User save() {
		User random = generate();
        users.signup(random);
        return random;
	}
	
	
}
