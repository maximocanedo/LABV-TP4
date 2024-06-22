package web.logicImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.UserDAOImpl;
import web.daoImpl.UserPermitDAOImpl;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.User;
import web.entity.UserPermit;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;
import web.generator.PermitTemplate;
import web.logic.IUserPermitLogic;

@Component
public class UserPermitLogicImpl implements IUserPermitLogic {

	@Autowired
	private UserPermitDAOImpl userpermitsrepository;
	
	@Autowired
	private UserDAOImpl usersrepository;
	
	public UserPermitLogicImpl() {}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#allow(java.lang.String, entity.Permit)
	 */
	@Override
	public UserPermit allow(String username, Permit permit, User requiring) throws NotFoundException {
		if(requiring.getUsername() != username)
			require(requiring, Permit.GRANT_PERMISSIONS);
		Optional<UserPermit> perm = userpermitsrepository.getPermit(username, permit);
		Optional<User> us = usersrepository.findByUsername(username);
		if(us.isEmpty()) throw new NotFoundException("User not found. ");
		User user = us.get();
		//System.out.println(perm.get());
		if(perm.isEmpty()) {
			UserPermit p = new UserPermit();
			p.setUser(user);
			p.setAction(permit);
			return userpermitsrepository.save(p);
		} else {
			UserPermit p = perm.get();
			p.setAllowed(true);
			return userpermitsrepository.update(p);
		}
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#allow(entity.User, entity.Permit)
	 */
	@Override
	public UserPermit allow(User user, Permit permit, User requiring) throws NotFoundException {
		return allow(user.getUsername(), permit);
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#reject(java.lang.String, entity.Permit)
	 */
	@Override
	public UserPermit reject(String username, Permit permit, User requiring) throws NotFoundException {
		if(requiring.getUsername() != username)
			require(requiring, Permit.GRANT_PERMISSIONS);
		Optional<UserPermit> perm = userpermitsrepository.getPermit(username, permit);
		Optional<User> us = usersrepository.findByUsername(username);
		if(us.isEmpty()) throw new NotFoundException("User not found. ");
		User user = us.get();
		if(perm.isEmpty()) {
			UserPermit p = new UserPermit();
			p.setUser(user);
			p.setAction(permit);
			p.setAllowed(false);
			return userpermitsrepository.save(p);
		} else {
			UserPermit p = perm.get();
			p.setAllowed(false);
			return userpermitsrepository.update(p);
		}
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#reject(entity.User, entity.Permit)
	 */
	@Override
	public UserPermit reject(User user, Permit permit, User requiring) throws NotFoundException {
		return reject(user.getUsername(), permit);
	}

	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#check(java.lang.String, entity.Permit)
	 */
	@Override
	public boolean check(String username, Permit permit) {
		return userpermitsrepository.check(username, permit);
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#check(entity.User, entity.Permit)
	 */
	@Override
	public boolean check(User user, Permit permit) {
		return userpermitsrepository.check(user, permit);
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#list(java.lang.String)
	 */
	@Override
	public List<UserPermit> list(String username) {
		return userpermitsrepository.list(username);
	}

	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#list(entity.User)
	 */
	@Override
	public List<UserPermit> list(User user) {
		return userpermitsrepository.list(user);
	}
	
	public void require(String username, Permit permit) throws NotAllowedException {
		if(!check(username, permit))
			throw new NotAllowedException(permit);
	}
	
	public void require(User user, Permit permit) throws NotAllowedException {
		if(!check(user, permit))
			throw new NotAllowedException(permit);
	}
	
	public void grant(String username, PermitTemplate template, User requiring) {
		for(Permit permit : template.getPermits()) {
			allow(username, permit, requiring);
		}
	}
	
	public void grant(User user, PermitTemplate template, User requiring) {
		for(Permit permit : template.getPermits()) {
			allow(user, permit, requiring);
		}
	}
	
	public int revokeAll(String username, User requiring) {
		if(username != requiring.getUsername())
			require(requiring, Permit.GRANT_PERMISSIONS);
		return userpermitsrepository.revokeAll(username);
	}
	

	public int revokeAll(User target, User requiring) {
		if(target.getUsername() != requiring.getUsername())
			require(requiring, Permit.GRANT_PERMISSIONS);
		return userpermitsrepository.revokeAll(target);
	}
	
	/** # Deprecated methods **/
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#allow(java.lang.String, entity.Permit)
	 */
	@Override
	@Deprecated
	public UserPermit allow(String username, Permit permit) throws NotFoundException {
		Optional<UserPermit> perm = userpermitsrepository.getPermit(username, permit);
		Optional<User> us = usersrepository.findByUsername(username);
		if(us.isEmpty()) throw new NotFoundException("User not found. ");
		User user = us.get();
		//System.out.println(perm.get());
		if(perm.isEmpty()) {
			UserPermit p = new UserPermit();
			p.setUser(user);
			p.setAction(permit);
			return userpermitsrepository.save(p);
		} else {
			UserPermit p = perm.get();
			p.setAllowed(true);
			return userpermitsrepository.update(p);
		}
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#allow(entity.User, entity.Permit)
	 */
	@Override
	@Deprecated
	public UserPermit allow(User user, Permit permit) throws NotFoundException {
		return allow(user.getUsername(), permit);
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#reject(java.lang.String, entity.Permit)
	 */
	@Override
	@Deprecated
	public UserPermit reject(String username, Permit permit) throws NotFoundException {
		Optional<UserPermit> perm = userpermitsrepository.getPermit(username, permit);
		Optional<User> us = usersrepository.findByUsername(username);
		if(us.isEmpty()) throw new NotFoundException("User not found. ");
		User user = us.get();
		if(perm.isEmpty()) {
			UserPermit p = new UserPermit();
			p.setUser(user);
			p.setAction(permit);
			p.setAllowed(false);
			return userpermitsrepository.save(p);
		} else {
			UserPermit p = perm.get();
			p.setAllowed(false);
			return userpermitsrepository.update(p);
		}
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#reject(entity.User, entity.Permit)
	 */
	@Override
	@Deprecated
	public UserPermit reject(User user, Permit permit) throws NotFoundException {
		return reject(user.getUsername(), permit);
	}

	
}
