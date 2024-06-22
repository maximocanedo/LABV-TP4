package web.dao;

import java.util.List;

import web.entity.Optional;
import web.entity.Permit;
import web.entity.User;
import web.entity.UserPermit;

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