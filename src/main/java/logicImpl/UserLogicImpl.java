package logicImpl;

import java.util.List;
import entity.Optional;

import org.mindrot.jbcrypt.BCrypt;

import daoImpl.UserDAOImpl;
import entity.User;
import exceptions.NotFoundException;
import logic.IUserLogic;

public class UserLogicImpl implements IUserLogic {
	
	private UserDAOImpl repository;
	
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
    public Optional<User> findByUsername(String username, boolean includeInactive) {
		return hideSensitiveData(repository.findByUsername(username, includeInactive));
	}
	
	@Override
    public void disable(User user) {
		user.setActive(false);
		repository.update(user);
	}
	
	@Override
    public void enable(User user) {
		user.setActive(true);
		repository.update(user);
	}
	
	@Override
    public List<User> list(int page, int size) {
		return repository.list(page, size);
	}
	
	@Override
    public List<User> list(int page, int size, boolean includeInactives) {
		return repository.list(page, size, includeInactives);
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
		repository.update(original);
	}
	
	@Override
    public void update(User user) throws NotFoundException {
		Optional<User> search = repository.findByUsername(user.getUsername());
		if(search.isEmpty()) throw new NotFoundException();
		User original = search.get();
		if(user.getName() != null) original.setName(user.getName());
		if(user.getDoctor() != null) original.setDoctor(user.getDoctor());
		repository.update(user);
	}
	
}
