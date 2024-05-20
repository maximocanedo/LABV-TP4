package logicImpl;

import java.util.List;

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
	
	/* (non-Javadoc)
	 * @see logic.IUserLogic#signup(entity.User)
	 */
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
	
	/* (non-Javadoc)
	 * @see logic.IUserLogic#check(java.lang.String, java.lang.String)
	 */
	@Override
	public User check(String username, String password) {
		User user = repository.getByUsername(username);
		if(user == null) return null;
		if(BCrypt.checkpw(password, user.getPassword())) {
			return hideSensitiveData(user);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserLogic#findByUsername(java.lang.String)
	 */
	@Override
	public User findByUsername(String username) {
		return hideSensitiveData(repository.getByUsername(username));
	}
	
	
	/* (non-Javadoc)
	 * @see logic.IUserLogic#disable(entity.User)
	 */
	@Override
	public void disable(User user) {
		user.setActiveState(false);
		repository.update(user);
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserLogic#enable(entity.User)
	 */
	@Override
	public void enable(User user) {
		user.setActiveState(true);
		repository.update(user);
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserLogic#list(int, int)
	 */
	@Override
	public List<User> list(int page, int size) {
		return repository.list(page, size);
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserLogic#list()
	 */
	@Override
	public List<User> list() {
		return list(1, 15);
	}
	
	
	/* (non-Javadoc)
	 * @see logic.IUserLogic#changePassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean changePassword(String username, String currentPassword, String newPassword) {
		// TODO: Implementar excepciones.
		User user = check(username, currentPassword);
		if(user == null) return false;
		user.setPassword(hash(newPassword));
		repository.update(user);
		return true;
	}
	
	/** (non-Javadoc)
	 * @see logic.IUserLogic#update(User)
	 */
	public boolean update(User user) {
		User original = repository.getByUsername(user.getUsername());
		if(original == null) return false;
		user.setPassword(original.getPassword());
		repository.update(user);
		return true;
	}
	
	
		
}
