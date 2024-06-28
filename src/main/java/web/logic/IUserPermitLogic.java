package web.logic;

import java.util.List;

import web.entity.Permit;
import web.entity.User;
import web.entity.UserPermit;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;
import web.exceptions.ServerException;

public interface IUserPermitLogic {
	
	UserPermit allow(String username, Permit permit, User requiring) throws NotFoundException;

	UserPermit allow(User user, Permit permit, User requiring) throws NotFoundException;

	UserPermit reject(String username, Permit permit, User requiring) throws NotFoundException;

	UserPermit reject(User user, Permit permit, User requiring) throws NotFoundException;

	boolean check(String username, Permit permit);

	boolean check(User user, Permit permit);

	List<UserPermit> list(String username);

	List<UserPermit> list(User user);
	
	//void require(String username, Permit permit) throws NotAllowedException;
	
	public User require(User requiring, Permit... permits) throws ServerException, NotFoundException;
	
	public User requireAll(User requiring, Permit... permits) throws ServerException, NotFoundException;
	
	//void require(User user, Permit permit) throws NotAllowedException;
	
	/** # Deprecated methods **/

	@Deprecated
	UserPermit allow(String username, Permit permit) throws NotFoundException;

	@Deprecated
	UserPermit allow(User user, Permit permit) throws NotFoundException;

	@Deprecated
	UserPermit reject(String username, Permit permit) throws NotFoundException;

	@Deprecated
	UserPermit reject(User user, Permit permit) throws NotFoundException;

}