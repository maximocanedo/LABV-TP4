package logic;

import java.util.List;
import entity.Optional;

import entity.User;

public interface IUserLogic {

	void signup(User user);

	Optional<User> check(String username, String password);

	Optional<User> findByUsername(String username);

	void disable(User user);

	void enable(User user);

	List<User> list(int page, int size);

	List<User> list();

	boolean changePassword(String username, String currentPassword, String newPassword);
	
	boolean update(User user);

}