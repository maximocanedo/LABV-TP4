package web.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IUserPermitDAO;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.User;
import web.entity.UserPermit;

@Component("userpermitsrepository")
public class UserPermitDAOImpl implements IUserPermitDAO {

	@Autowired
	private DataManager dataManager;
	
	public UserPermitDAOImpl() {}
	
	/* (non-Javadoc)
	 * @see dao.IUserPermitDAO#save(entity.UserPermit)
	 */
	@Override
	public UserPermit save(UserPermit permit) {
		dataManager.transact(session -> {
			session.save(permit);
		});
		return permit;
	}
	
	/* (non-Javadoc)
	 * @see dao.IUserPermitDAO#update(entity.UserPermit)
	 */
	@Override
	public UserPermit update(UserPermit permit) {
		dataManager.transact(session -> {
			session.update(permit);
		});
		return permit;
	}
	
	/* (non-Javadoc)
	 * @see dao.IUserPermitDAO#getPermit(java.lang.String, entity.Permit)
	 */
	@Override
	public Optional<UserPermit> getPermit(String username, Permit permit) {
		final Optional<UserPermit> optPermit = new Optional<UserPermit>();
		dataManager.run(session -> {
			String hql = "SELECT u FROM UserPermit u WHERE u.user.username = :username AND u.action = :action";
	        Query query = session.createQuery(hql);
	        query.setParameter("action", permit);
	        query.setParameter("username", username);
	        optPermit.set((UserPermit) query.uniqueResult());
	        System.out.println((UserPermit) query.uniqueResult());
		});
		return optPermit;
	}
	
	/* (non-Javadoc)
	 * @see dao.IUserPermitDAO#getPermit(entity.User, entity.Permit)
	 */
	@Override
	public Optional<UserPermit> getPermit(User user, Permit permit) {
		return getPermit(user.getUsername(), permit);
	}

	/* (non-Javadoc)
	 * @see dao.IUserPermitDAO#check(java.lang.String, entity.Permit)
	 */
	@Override
	public boolean check(String username, Permit permit) {
		final Optional<Boolean> optPermit = new Optional<Boolean>(false);
		dataManager.run(session -> {
			String hql = "SELECT u.allowed FROM UserPermit u WHERE u.user.username = :username AND u.action = :action";
	        Query query = session.createQuery(hql);
	        query.setParameter("action", permit);
	        query.setParameter("username", username);
	        optPermit.set((Boolean) query.uniqueResult());
		});
		if(optPermit.isEmpty() || optPermit.get() == null) return false;
		return optPermit.get().booleanValue();
	}
	
	/* (non-Javadoc)
	 * @see dao.IUserPermitDAO#check(entity.User, entity.Permit)
	 */
	@Override
	public boolean check(User user, Permit permit) {
		return check(user.getUsername(), permit);
	}
	
	/* (non-Javadoc)
	 * @see dao.IUserPermitDAO#list(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<UserPermit> list(String username) {
		final Optional<List<UserPermit>> optPermit = new Optional<List<UserPermit>>();
		dataManager.run(session -> {
			String hql = "SELECT u FROM UserPermit u WHERE u.user.username = :username";
	        Query query = session.createQuery(hql);
	        query.setParameter("username", username);
	        optPermit.set(query.list());
		});
		return optPermit.get();
	}
	
	/* (non-Javadoc)
	 * @see dao.IUserPermitDAO#list(entity.User)
	 */
	@Override
	public List<UserPermit> list(User user) {
		return list(user.getUsername());
	}
	
	@Override
	public int revokeAll(String username) {
		Optional<Integer> opt = new Optional<Integer>(0);
	    dataManager.run(session -> {
	        String hql = "UPDATE UserPermit u SET u.allowed = :allowed WHERE u.user.username = :username";
	        Query query = session.createQuery(hql);
	        query.setParameter("allowed", false);
	        query.setParameter("username", username);

	        opt.set(query.executeUpdate());
	    });
	    return opt.get();
	}

	@Override
	public int revokeAll(User user) {
		return revokeAll(user.getUsername());
	}
	
	
}
