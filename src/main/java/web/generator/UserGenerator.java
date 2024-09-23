package web.generator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import web.entity.User;
import web.logicImpl.UserLogicImpl;

@Component("userGenerator")
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
        String usn = "u0." + faker.name().username();
        if(usn.length() > 15) usn = usn.substring(0, 14);
        user.setUsername(usn);
        user.setPassword("User$12345678"); // Todos los usuarios generados automáticamente usarán esta contraseña.
        user.setActive(true);
        return user;
	}

	@Override
	public User save(User requiring) {
		User random = generate();
        users.signup(random);
        return random;
	}
	
	public User save(Name n, User requiring) {
		User random = generate();
		random.setName(n.fullName());
        String usn = "u0." + n.username();
        if(usn.length() > 15) usn = usn.substring(0, 14);
        random.setUsername(usn);
		users.signup(random);
		return random;
	}
	
	
}
