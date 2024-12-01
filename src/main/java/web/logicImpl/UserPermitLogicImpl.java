package web.logicImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.daoImpl.AppointmentDAOImpl;
import web.daoImpl.UserDAOImpl;
import web.daoImpl.UserPermitDAOImpl;
import web.entity.Appointment;
import web.entity.IDoctor;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.User;
import web.entity.UserPermit;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;
import web.exceptions.ServerException;
import web.generator.PermitTemplate;
import web.logic.IUserPermitLogic;

@Component("userpermits")
public class UserPermitLogicImpl implements IUserPermitLogic {

	@Autowired
	private UserPermitDAOImpl userpermitsrepository;
	
	@Autowired
	private AppointmentDAOImpl appointmentsrepository;
	
	@Autowired
	private UserDAOImpl usersrepository;
	
	public UserPermitLogicImpl() {}
	
	@Override
	public User require(User requiring, Permit... permits) throws ServerException, NotFoundException {
		if(requiring == null) throw new ServerException();
		//if(requiring.getAllowedPermits() == null) {
			Optional<User> _u = usersrepository.findByUsername(requiring.getUsername());
			if(_u.isEmpty()) throw new NotFoundException("User not found. ");
			requiring = _u.get();
		//}
		for(Permit action : permits) {
			if(requiring.can(action)) return requiring;		
		}
		throw new NotAllowedException(permits);
	}
	
	public User inquire(User requiring, Permit... permits) throws ServerException, NotFoundException {
		try {
			requiring = require(requiring, permits);
		} catch(NotAllowedException expected) {
			return requiring;
		} catch(ServerException e) {
			throw e;
		} catch(NotFoundException e) {
			throw e;
		}
		return requiring;
	}
	
	public User inquireDoctor(User requiring, IDoctor doctor, Permit... permits) throws ServerException, NotFoundException {
		try {
			requiring = require(requiring, permits);
		} catch(NotAllowedException expected) {
			if(requiring.getDoctor() == null || !requiring.getDoctor().isActive()) throw expected;
			if(requiring.getDoctor().getFile() == doctor.getFile() || requiring.getDoctor().getId() == doctor.getId()) {
				return requiring;
			} throw expected;
		} catch(ServerException e) {
			throw e;
		} catch(NotFoundException e) {
			throw e;
		}
		return requiring;
	}
	
	public boolean ap(User u, int id, NotAllowedException notAllowedException) {
		Optional<Appointment> a = appointmentsrepository.findById(id);
		if(a.isEmpty()) throw new NotFoundException("Appointment not found. ");
		Appointment appointment = a.get();
		if(u.getDoctor() != null && (u.getDoctor().getFile() == appointment.getAssignedDoctor().getFile() || u.getDoctor().getId() == appointment.getAssignedDoctor().getId())) {
			return true;
		} else throw notAllowedException;
	}
	
	public User inquireAppointment(User requiring, int id, Permit... permits) throws ServerException, NotFoundException {
		try {
			requiring = require(requiring, permits);
		} catch(NotAllowedException expected) {
			if(ap(requiring, id, expected))
				return requiring;
			else throw expected;
		} catch(ServerException e) {
			throw e;
		} catch(NotFoundException e) {
			throw e;
		}
		return requiring;
	}
	
	public User inquireDoctorById(User requiring, int id, Permit... permits) throws ServerException, NotFoundException {
		try {
			requiring = require(requiring, permits);
		} catch(NotAllowedException expected) {
			if(requiring.getDoctor() == null || !requiring.getDoctor().isActive()) throw expected;
			if(requiring.getDoctor().getId() == id) {
				return requiring;
			} throw expected;
		} catch(ServerException e) {
			throw e;
		} catch(NotFoundException e) {
			throw e;
		}
		return requiring;
	}
	
	public User inquireUser(User requiring, String username, Permit... permits) throws ServerException, NotFoundException {
		try {
			requiring = require(requiring, permits);
		} catch(NotAllowedException expected) {
			if(requiring == null || !requiring.isActive()) throw expected;
			if(requiring.getUsername().equals(username)) {
				return requiring;
			} throw expected;
		} catch(ServerException e) {
			throw e;
		} catch(NotFoundException e) {
			throw e;
		}
		return requiring;
	}
	
	public User inquireUser(User requiring, User requested, Permit... permits) throws ServerException, NotFoundException {
		return inquireUser(requiring, requested.getUsername(), permits);
	}
	
	public User inquireDoctorByFile(User requiring, int file, Permit... permits) throws ServerException, NotFoundException {
		try {
			requiring = require(requiring, permits);
		} catch(NotAllowedException expected) {
			if(requiring.getDoctor() == null || !requiring.getDoctor().isActive()) throw expected;
			if(requiring.getDoctor().getFile() == file) {
				return requiring;
			} throw expected;
		} catch(ServerException e) {
			throw e;
		} catch(NotFoundException e) {
			throw e;
		}
		return requiring;
	}

	@Override
	public User requireAll(User requiring, Permit... permits) throws ServerException, NotFoundException {
		if(requiring == null) throw new ServerException();
		if(!requiring.loadedPermissions()) {
			Optional<User> _u = usersrepository.findByUsername(requiring.getUsername());
			if(_u.isEmpty()) throw new NotFoundException("User not found. ");
			requiring = _u.get();
		}
		List<Permit> notAllowed = new ArrayList<Permit>();
		for(Permit action : permits) {
			if(!requiring.can(action)) notAllowed.add(action);
		}
		if(notAllowed.size() == permits.length) return requiring;
		throw new NotAllowedException((Permit[]) notAllowed.toArray());
	}
	
	/* (non-Javadoc)
	 * @see logic.IUserPermitLogic#allow(java.lang.String, entity.Permit)
	 */
	@Override
	public UserPermit allow(String username, Permit permit, User requiring) throws NotFoundException {
		if(requiring.getUsername() != "root")
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
			p.setAllowed(true);
			return userpermitsrepository.save(p);
		} else {
			UserPermit p = perm.get();
			p.setAllowed(true);
			return userpermitsrepository.update(p);
		}
	}
	
	@Override
	public UserPermit allowWithoutAsking(String username, Permit permit) throws NotFoundException {
		Optional<UserPermit> perm = userpermitsrepository.getPermit(username, permit);
		Optional<User> us = usersrepository.findByUsername(username);
		if(us.isEmpty()) throw new NotFoundException("User not found. ");
		User user = us.get();
		//System.out.println(perm.get());
		if(perm.isEmpty()) {
			UserPermit p = new UserPermit();
			p.setUser(user);
			p.setAction(permit);
			p.setAllowed(true);
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
		return allow(user.getUsername(), permit, requiring);
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
		return reject(user.getUsername(), permit, requiring);
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
	
	public void grant(String username, PermitTemplate template, User requiring) {
		for(Permit permit : template.getPermits()) {
			allow(username, permit, requiring);
		}
	}
	
	public void grantDefoPermits(User user, PermitTemplate template) {
		for(Permit permit : template.getPermits()) {
			allowWithoutAsking(user.getUsername(), permit);
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
	
}
