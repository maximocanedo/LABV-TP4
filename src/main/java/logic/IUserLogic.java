package logic;

import java.util.List;

import entity.User;

public interface IUserLogic {

	void signup(User user);

	User check(String username, String password);

	User findByUsername(String username);

	void disable(User user);

	void enable(User user);

	List<User> list(int page, int size);

	List<User> list();

	boolean changePassword(String username, String currentPassword, String newPassword);

}