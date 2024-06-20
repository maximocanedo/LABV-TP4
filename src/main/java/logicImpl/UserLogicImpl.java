package logicImpl;

import java.util.List;
import entity.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import daoImpl.UserDAOImpl;
import entity.User;
import exceptions.InvalidCredentialsException;
import exceptions.NotFoundException;
import logic.IUserLogic;

@Component
public class UserLogicImpl implements IUserLogic {
	
	@Autowired
	private UserDAOImpl usersrepository;
	
	@Autowired
	private TicketLogicImpl tickets;
	
	public UserLogicImpl() {}
	
	private String hash(String clear) {
		return BCrypt.hashpw(clear, BCrypt.gensalt());
	}
	
	@Override
    public void signup(User user) {
		String clearPassword = user.getPassword();
		user.setPassword(hash(clearPassword));
		usersrepository.add(user);
	}
	
	private User hideSensitiveData(User user) {
		user.setPassword(null);
		return user;
	}
	
	private Optional<User> hideSensitiveData(Optional<User> u) {
		if(u.isPresent()) {
			u.set(this.hideSensitiveData(u.get()));
		}
		return u;
	}
	
	@Override
    public Optional<User> check(String username, String password) {
		Optional<User> user = usersrepository.findByUsername(username);
		if(user.isEmpty()) return user;
		User u = user.get();
		if(BCrypt.checkpw(password, u.getPassword())) {
			user.set(hideSensitiveData(u));
		}
		return user;
	}
	
	
	
	
	
	@Override
    public Optional<User> findByUsername(String username) {
		return hideSensitiveData(usersrepository.findByUsername(username));
	}
	
	@Override
    public Optional<User> findByUsername(String username, boolean includeInactive) {
		return hideSensitiveData(usersrepository.findByUsername(username, includeInactive));
	}
	
	@Override
    public void disable(User user) {
		user.setActive(false);
		usersrepository.update(user);
	}
	
	@Override
    public void enable(User user) {
		user.setActive(true);
		usersrepository.update(user);
	}
	
	@Override
    public List<User> list(int page, int size) {
		return usersrepository.list(page, size);
	}
	
	@Override
    public List<User> list(int page, int size, boolean includeInactives) {
		return usersrepository.list(page, size, includeInactives);
	}
	
	@Override
    public List<User> list() {
		return list(1, 15);
	}
	
	@Override
    public void changePassword(String username, String currentPassword, String newPassword) throws NotFoundException {
		Optional<User> search = check(username, currentPassword);
		if(search.isEmpty()) throw new NotFoundException();
		User original = search.get();
		original.setPassword(hash(newPassword));
		usersrepository.update(original);
	}
	
	@Override
    public void update(User user) throws NotFoundException {
		Optional<User> search = usersrepository.findByUsername(user.getUsername());
		if(search.isEmpty()) throw new NotFoundException();
		User original = search.get();
		if(user.getName() != null) original.setName(user.getName());
		if(user.getDoctor() != null) original.setDoctor(user.getDoctor());
		usersrepository.update(user);
	}

	@Override
	public String login(String username, String password) throws InvalidCredentialsException, NotFoundException {
		Optional<User> user = check(username, password);
		if(user.isEmpty()) {
			throw new InvalidCredentialsException();
		}
		return tickets.startToken(user.get().getUsername(), "Testing device", "Device #001");
	}

	@Override
	public String renewAccessToken(String refreshToken) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
