package web.logicImpl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.DoctorDAOImpl;
import web.daoImpl.UserDAOImpl;
import web.entity.Doctor;
import web.entity.IUser;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.User;
import web.entity.input.UserQuery;
import web.entity.view.UserView;
import web.exceptions.InvalidCredentialsException;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;
import web.generator.PermitTemplate;
import web.logic.ITicketLogic;
import web.logic.IUserLogic;
import web.logic.validator.UserValidator;

@Component("users")
public class UserLogicImpl implements IUserLogic {
	
	@Autowired
	private UserDAOImpl usersrepository;
	
	@Autowired
	private DoctorDAOImpl doctorsrepository;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	@Autowired
	private ITicketLogic tickets;
	
	public UserLogicImpl() {}
	
	private String hash(String clear) {
		return BCrypt.hashpw(clear, BCrypt.gensalt());
	}
	
	@Deprecated
	public User getRootUser() {
		User root = usersrepository.getByUsername("root");
		return root;
	}
	
	@Override
    public User signup(User user) {
		user.setName(userValidator.name(user.getName()));
		user.setUsername(userValidator.username(user.getUsername()));
		if(user.getDoctor() != null) {
			user.setDoctor(userValidator.doctor(user.getDoctor()));
		}
		String clearPassword = userValidator.password(user.getPassword(), user.getName(), user.getUsername());
		user.setPassword(hash(clearPassword));
		user = usersrepository.add(user);
		permits.grantDefoPermits(user, PermitTemplate.DOCTOR);
		return user;
	}
	
	@Override
    public User signup(User user, PermitTemplate template, User requiring) {
		requiring = permits.require(requiring, Permit.GRANT_PERMISSIONS);
		User newUser = signup(user);
		permits.grant(user, template, requiring);
		return newUser;
	}

	@Override
    public User signup(User user, String templateName, User requiring) {
		return signup(user, PermitTemplate.getByName(templateName), requiring);
	}
	
	@Override
    public Optional<User> check(String username, String password) {
		Optional<User> user = usersrepository.findByUsername(username);
		if(user.isEmpty()) throw new InvalidCredentialsException();
		User u = user.get();
		boolean ok = BCrypt.checkpw(password, u.getPassword());
		if(ok) {
			user.set((u));
			return user;
		} else throw new InvalidCredentialsException();
	}
	
	@Override
	public boolean checkUsernameAvailability(String username) {
		return usersrepository.checkUsernameAvailability(username);
	}
	
	public boolean exists(String username) {
		return usersrepository.exists(username);
	}
	
	@Override
    public Optional<User> findByUsername(String username, User requiring) {
		requiring = permits.inquireUser(requiring, username, Permit.READ_USER_DATA);
		return (usersrepository.findByUsername(username));
	}
	
	@Override
    public Optional<User> findByUsername(String username, boolean includeInactive, User requiring) {
		requiring = permits.inquireUser(requiring, username, Permit.READ_USER_DATA);
		includeInactive = includeInactive && requiring.can(Permit.DELETE_OR_ENABLE_USER);
		return (usersrepository.findByUsername(username, includeInactive));
	}
	
	@Override
	public IUser getByUsername(String username, boolean includeInactive, User requiring) throws NotFoundException, NotAllowedException {
		requiring = permits.inquireUser(requiring, username, Permit.READ_USER_DATA);
		IUser opt = null;
		includeInactive = includeInactive && requiring.can(Permit.DELETE_OR_ENABLE_USER);
		if(requiring.can(Permit.READ_DOCTOR)) {
			opt = usersrepository.getByUsername(username, includeInactive);
		} else opt = usersrepository.getBasicByUsername(username, includeInactive);
		if(opt == null) throw new NotFoundException("User not found. ");
		return opt;
	}
	
	@Override
	public User getByUsername(String username, User requiring) throws NotFoundException, NotAllowedException {
		Optional<User> opt = findByUsername(username, false, requiring);
		if(opt.isEmpty()) throw new NotFoundException("User not found. ");
		else return opt.get();
	}
	
	@Override
	public boolean checkUsernameAvailability(String username, User requiring) {
		return usersrepository.checkUsernameAvailability(username);
	}

	@Override
	public List<UserView> search(UserQuery q, User requiring) {
		permits.inquire(requiring, Permit.READ_USER_DATA);
		return usersrepository.search(q);
	}
	
	public List<UserView> searchForSelector(UserQuery q, User requiring) {
		return usersrepository.search(q);
	}
	
	@Override
    public void disable(User user, User requiring) {
		permits.inquireUser(requiring, user, Permit.DELETE_OR_ENABLE_USER);
		user.setActive(false);
		usersrepository.update(user);
	}
	
	@Override
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

	@Override
	public void enable(String username, User requiring) {
		User user = usersrepository.getByUsername(username, true);
		enable(user, requiring);
	}
	
	@Override
    public void changePassword(String username, String currentPassword, String newPassword) throws NotFoundException {
		Optional<User> search = check(username, currentPassword);
		if(search.isEmpty()) throw new NotFoundException();
		User original = search.get();
		newPassword = userValidator.password(newPassword, original.getName(), original.getUsername());
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
		newPassword = userValidator.password(newPassword, original.getName(), original.getUsername());
		original.setPassword(hash(newPassword));
		usersrepository.update(original);
	}
	
	@Override
    public User update(User user, User requiring) throws NotFoundException {
		permits.inquireUser(requiring, user, Permit.UPDATE_USER_DATA);
		Optional<User> search = usersrepository.findByUsername(user.getUsername());
		if(search.isEmpty()) throw new NotFoundException();
		User original = search.get();
		if(user.getName() != null) 
			original.setName(userValidator.name(user.getName()));
		if(user.getDoctor() != null) {
			if(user.getDoctor().getId() == -1 || user.getDoctor().getFile() == -1 
				|| (user.getDoctor().getId() == 0 && user.getDoctor().getFile() == 0)) {
				doctorsrepository.unassignUser(original.getDoctor());
				user.setDoctor(null);
			} else {
				Doctor d = (userValidator.doctor(user.getDoctor()));
				Doctor updated = doctorsrepository.assignUser(d, user);
				user.setDoctor(updated);				
			}
		}
		return usersrepository.update(original);
	}

	/**
	 * TODO: Implementar dispositivo e ID.
	 */
	@Override
	public String login(String username, String password) throws InvalidCredentialsException, NotFoundException {
		return login(username, password, "Testing device", "Device #001");
	}
	
	@Override
	public String login(String username, String password, String deviceName, String deviceId) throws InvalidCredentialsException, NotFoundException {
		Optional<User> user = check(username, password);
		if(user.isEmpty()) {
			throw new InvalidCredentialsException();
		} 
		return tickets.startToken(user.get().getUsername(), deviceName, deviceId);
	}
}
