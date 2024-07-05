package web.daoImpl;

import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IUserDAO;
import web.entity.Optional;
import web.entity.User;
import web.entity.input.UserQuery;
import web.entity.view.UserView;
import web.exceptions.NotFoundException;

@SuppressWarnings("unchecked")
@Component("usersrepository")
public class UserDAOImpl implements IUserDAO {
	
	@Autowired
	public DataManager dataManager;
	
	public UserDAOImpl() {}
		
	@Override
	public User add(User user) {
		dataManager.transact(session -> {
			session.save(user);
		});
		return user;
    }
	
	@Override
    public Optional<User> findByUsername(String username, boolean includeInactives) {
		final Optional<User> cfUser = new Optional<User>(null);
		dataManager.run(session -> {
			session.clear();
			
			String hql = "FROM User WHERE username = :username" + (includeInactives ? "" : " AND active = 1");
	        Query query = session.createQuery(hql);
	        query
	        	.setParameter("username", username)
	        	.setCacheable(false)
	        	.setCacheMode(CacheMode.IGNORE);
	        User x = (User) query.uniqueResult();
	        if(x != null) {
	        	Hibernate.initialize(x.getAllowedPermits());
	        }
	        cfUser.set(x);
        	session.evict(x);
		});
		return cfUser;
	}
	
	@Override
    public Optional<User> findByUsername(String username) {
		return findByUsername(username, false);
	}
	
	public User getByUsername(String username, boolean includeInactives) {
		return findByUsername(username, includeInactives).get();
	}
	
	public User getByUsername(String username) {
		return findByUsername(username).get();
	}
	
	public UserView getBasicByUsername(String username, boolean includeInactives) {
		return findBasicByUsername(username, includeInactives).get();
	}
	
	public UserView getBasicByUsername(String username) {
		return findBasicByUsername(username, false).get();
	}


	@Override
    public Optional<UserView> findBasicByUsername(String username, boolean includeInactives) {
		final Optional<UserView> cfUser = new Optional<UserView>(null);
		dataManager.run(session -> {
			String hql = "FROM UserView WHERE username = :username" + (includeInactives ? "" : " AND active = 1");
	        Query query = session.createQuery(hql);
	        query.setParameter("username", username);
	        UserView x = (UserView) query.uniqueResult();
	        cfUser.set(x);
		});
		return cfUser;
	}

	@Override
	public boolean checkUsernameAvailability(String username) {
		final Optional<Boolean> cfUser = new Optional<Boolean>(false);
		dataManager.run(session -> {
			String hql = "SELECT COUNT(u) FROM User WHERE username = :username";
	        Query query = session.createQuery(hql);
	        query.setParameter("username", username);
	        int c = (Integer) query.uniqueResult();
	        cfUser.set(c == 0);
		});
		return cfUser.get().booleanValue();
	}
	

	@Override
	public List<UserView> search(UserQuery q) {
		final Optional<List<UserView>> opt = new Optional<List<UserView>>();
		dataManager.run(session -> {
			Query query = q.toQuery(session);
			opt.set(query.list());
		});
		return opt.get();
	}
	
	@Override
    public User update(User user) {
		dataManager.transact(session -> {
			session.update(user);
		});
		return user;
	}

	private void updateStatus(String username, boolean newStatus) throws NotFoundException {
		Optional<User> search = findByUsername(username, newStatus);
    	if(search.isEmpty()) throw new NotFoundException();
    	dataManager.transact(session -> {
        	User original = search.get();
        	original.setActive(newStatus);
            session.update(original);
        });
	}
	
	@Override
	public void disable(String username) throws NotFoundException {
		updateStatus(username, false);	
	}

	@Override
	public void enable(String username) throws NotFoundException {
		updateStatus(username, true);	
	}
	
	
	
}
