package web.logicImpl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.UserDAOImpl;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.User;
import web.entity.input.UserQuery;
import web.exceptions.InvalidCredentialsException;
import web.exceptions.NotFoundException;
import web.generator.PermitTemplate;
import web.logic.ITicketLogic;
import web.logic.IUserLogic;

@Component("users")
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

    public User signup(User user, String templateName, User requiring) {
		return signup(user, PermitTemplate.getByName(templateName), requiring);
	}

	
	@Deprecated
	private Optional<User> hideSensitiveData(Optional<User> u) {
		
		return u;
	}
	
	@Override
    public Optional<User> check(String username, String password) {
		Optional<User> user = usersrepository.findByUsername(username);
		if(user.isEmpty()) throw new InvalidCredentialsException();
		User u = user.get();
		if(BCrypt.checkpw(password, u.getPassword())) {
			user.set((u));
			return user;
		}
		throw new InvalidCredentialsException();
	}
	
	@Override
    public Optional<User> findByUsername(String username, User requiring) {
		permits.require(requiring, Permit.READ_USER_DATA);
		return (usersrepository.findByUsername(username));
	}
	
	@Override
    public Optional<User> findByUsername(String username, boolean includeInactive, User requiring) {
		permits.require(requiring, Permit.READ_USER_DATA);
		return (usersrepository.findByUsername(username, includeInactive));
	}
	
	public User getByUsername(String username, boolean includeInactive, User requiring) {
		Optional<User> opt = findByUsername(username, includeInactive, requiring);
		if(opt.isEmpty()) throw new NotFoundException("User not found. ");
		else return opt.get();
	}
	
	public User getByUsername(String username, User requiring) {
		Optional<User> opt = findByUsername(username, false, requiring);
		if(opt.isEmpty()) throw new NotFoundException("User not found. ");
		else return opt.get();
	}
	
	@Override
    public void disable(User user, User requiring) {
		permits.require(requiring, Permit.DELETE_OR_ENABLE_USER);
		user.setActive(false);
		usersrepository.update(user);
	}
	
	public void disable(String username, User requiring) {
		User user = getByUsername(username, requiring);
		disable(user, requiring);
	}
	
	@Override
    public void enable(User user, User requiring) {
		permits.require(requiring, Permit.DELETE_OR_ENABLE_USER);
		user.setActive(true);
		usersrepository.update(user);
	}
	
	public void enable(String username, User requiring) {
		User user = getByUsername(username, requiring);
		enable(user, requiring);
	}
	
	public List<User> search(UserQuery q, User requiring) {
		permits.require(requiring, Permit.READ_USER_DATA);
		return usersrepository.search(q);
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
		return usersrepository.update(original);
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
