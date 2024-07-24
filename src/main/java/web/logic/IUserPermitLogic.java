package web.logic;

import java.util.List;

import web.entity.Permit;
import web.entity.User;
import web.entity.UserPermit;
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
	
	public User require(User requiring, Permit... permits) throws ServerException, NotFoundException;
	
	public User requireAll(User requiring, Permit... permits) throws ServerException, NotFoundException;

	UserPermit allowWithoutAsking(String username, Permit permit) throws NotFoundException;

}