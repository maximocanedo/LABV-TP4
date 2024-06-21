package dao;

import java.util.List;

import entity.Optional;
import entity.Permit;
import entity.User;
import entity.UserPermit;

public interface IUserPermitDAO {

	UserPermit save(UserPermit permit);

	UserPermit update(UserPermit permit);

	Optional<UserPermit> getPermit(String username, Permit permit);

	Optional<UserPermit> getPermit(User user, Permit permit);

	boolean check(String username, Permit permit);

	boolean check(User user, Permit permit);

	List<UserPermit> list(String username);

	List<UserPermit> list(User user);

	int revokeAll(String username);
	
	int revokeAll(User user);

}