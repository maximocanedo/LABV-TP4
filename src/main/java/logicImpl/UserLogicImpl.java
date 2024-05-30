package logicImpl;

import java.util.List;
import entity.Optional;

import org.mindrot.jbcrypt.BCrypt;

import dao.IUserDAO;
import daoImpl.UserDAOImpl;
import entity.User;
import logic.IUserLogic;

public class UserLogicImpl implements IUserLogic {
	
	private final IUserDAO repository;
	
	public UserLogicImpl() {
		repository = new UserDAOImpl();
	}
	
	private String hash(String clear) {
		return BCrypt.hashpw(clear, BCrypt.gensalt());
	}
	
	@Override
    public void signup(User user) {
		String clearPassword = user.getPassword();
		user.setPassword(hash(clearPassword));
		repository.add(user);
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
		Optional<User> user = repository.findByUsername(username);
		if(user.isEmpty()) return user;
		User u = user.get();
		if(BCrypt.checkpw(password, u.getPassword())) {
			user.set(hideSensitiveData(u));
		}
		return user;
	}
	
	@Override
    public Optional<User> findByUsername(String username) {
		return hideSensitiveData(repository.findByUsername(username));
	}
	
	@Override
    public void disable(User user) {
		user.setActiveState(false);
		repository.update(user);
	}
	
	@Override
    public void enable(User user) {
		user.setActiveState(true);
		repository.update(user);
	}
	
	@Override
    public List<User> list(int page, int size) {
		return repository.list(page, size);
	}
	
	@Override
    public List<User> list() {
		return list(1, 15);
	}
	
	@Override
    public boolean changePassword(String username, String currentPassword, String newPassword) {
		// TODO: Implementar excepciones.
		Optional<User> optional = check(username, currentPassword);
		if(optional.isEmpty()) return false;
		User user = optional.get();
		user.setPassword(hash(newPassword));
		repository.update(user);
		return true;
	}
	
	@Override
    public boolean update(User user) {
		Optional<User> original = repository.findByUsername(user.getUsername());
		if(original.isEmpty()) return false;
		user.setPassword(original.get().getPassword());
		repository.update(user);
		return true;
	}
	
}
