package logicImpl;

import java.util.List;
import entity.Optional;
import entity.Permit;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import daoImpl.UserDAOImpl;
import entity.User;
import exceptions.InvalidCredentialsException;
import exceptions.NotFoundException;
import generator.PermitTemplate;
import logic.ITicketLogic;
import logic.IUserLogic;

@Component
public class UserLogicImpl implements IUserLogic {
	
	@Autowired
	private UserDAOImpl usersrepository;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	@Autowired
	private ITicketLogic tickets;
	
	public UserLogicImpl() {}
	
	private String hash(String clear) {
		return BCrypt.hashpw(clear, BCrypt.gensalt());
	}
	
	@Override
    public User signup(User user) {
		String clearPassword = user.getPassword();
		user.setPassword(hash(clearPassword));
		return usersrepository.add(user);
	}
	
	@Override
    public User signup(User user, PermitTemplate template, User requiring) {
		User newUser = signup(user);
		permits.grant(user, template, requiring);
		return newUser;
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
		if(user.isEmpty()) throw new InvalidCredentialsException();
		User u = user.get();
		if(BCrypt.checkpw(password, u.getPassword())) {
			user.set(hideSensitiveData(u));
			return user;
		}
		throw new InvalidCredentialsException();
	}
	
	@Override
    public Optional<User> findByUsername(String username, User requiring) {
		permits.require(requiring, Permit.READ_USER_DATA);
		return hideSensitiveData(usersrepository.findByUsername(username));
	}
	
	@Override
    public Optional<User> findByUsername(String username, boolean includeInactive, User requiring) {
		permits.require(requiring, Permit.READ_USER_DATA);
		return hideSensitiveData(usersrepository.findByUsername(username, includeInactive));
	}
	
	@Override
    public void disable(User user, User requiring) {
		permits.require(requiring, Permit.DELETE_OR_ENABLE_USER);
		user.setActive(false);
		usersrepository.update(user);
	}
	
	@Override
    public void enable(User user, User requiring) {
		permits.require(requiring, Permit.DELETE_OR_ENABLE_USER);
		user.setActive(true);
		usersrepository.update(user);
	}
	
	@Override
    public List<User> list(int page, int size, User requiring) {
		permits.require(requiring, Permit.READ_USER_DATA);
		return usersrepository.list(page, size);
	}
	
	@Override
    public List<User> list(int page, int size, boolean includeInactives, User requiring) {
		permits.require(requiring, Permit.READ_USER_DATA);
		return usersrepository.list(page, size, includeInactives);
	}
	
	@Override
    public List<User> list(User requiring) {
		return list(1, 15, requiring);
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
    public void changePassword(String username, String newPassword, User requiring) throws NotFoundException {
		if(username != requiring.getUsername())
			permits.require(requiring, Permit.RESET_PASSWORD);
		Optional<User> search = findByUsername(username, requiring);
		if(search.isEmpty()) throw new NotFoundException();
		User original = search.get();
		original.setPassword(hash(newPassword));
		usersrepository.update(original);
	}
	
	@Override
    public User update(User user, User requiring) throws NotFoundException {
		if(requiring.getUsername() != user.getUsername())
			permits.require(requiring, Permit.UPDATE_USER_DATA);
		Optional<User> search = usersrepository.findByUsername(user.getUsername());
		if(search.isEmpty()) throw new NotFoundException();
		User original = search.get();
		if(user.getName() != null) original.setName(user.getName());
		if(user.getDoctor() != null) original.setDoctor(user.getDoctor());
		return usersrepository.update(user);
	}

	@Override
	public String login(String username, String password) throws InvalidCredentialsException, NotFoundException {
		Optional<User> user = check(username, password);
		if(user.isEmpty()) {
			throw new InvalidCredentialsException();
		} 
		return tickets.startToken(user.get().getUsername(), "Testing device", "Device #001");
	}
	
	
	
	
	/** # Deprecated methods **/
	
	@Override
	@Deprecated
    public Optional<User> findByUsername(String username) {
		return hideSensitiveData(usersrepository.findByUsername(username));
	}
	
	@Override
	@Deprecated
    public Optional<User> findByUsername(String username, boolean includeInactive) {
		return hideSensitiveData(usersrepository.findByUsername(username, includeInactive));
	}
	
	@Override
	@Deprecated
    public void disable(User user) {
		user.setActive(false);
		usersrepository.update(user);
	}
	
	@Override
	@Deprecated
    public void enable(User user) {
		user.setActive(true);
		usersrepository.update(user);
	}
	
	@Override
	@Deprecated
    public List<User> list(int page, int size) {
		return usersrepository.list(page, size);
	}
	
	@Override
	@Deprecated
    public List<User> list(int page, int size, boolean includeInactives) {
		return usersrepository.list(page, size, includeInactives);
	}
	
	@Override
	@Deprecated
    public List<User> list() {
		return list(1, 15);
	}
	
	@Override
	@Deprecated
    public void update(User user) throws NotFoundException {
		Optional<User> search = usersrepository.findByUsername(user.getUsername());
		if(search.isEmpty()) throw new NotFoundException();
		User original = search.get();
		if(user.getName() != null) original.setName(user.getName());
		if(user.getDoctor() != null) original.setDoctor(user.getDoctor());
		usersrepository.update(user);
	}
	
	
}
