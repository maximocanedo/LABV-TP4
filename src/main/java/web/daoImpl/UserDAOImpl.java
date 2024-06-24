package web.daoImpl;

import java.util.List;


import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IUserDAO;
import web.entity.Optional;
import web.entity.User;
import web.entity.input.UserQuery;
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
    public Optional<User> findByUsername(String username) {
		return findByUsername(username, false);
	}
	
	@Override
    public Optional<User> findByUsername(String username, boolean includeInactives) {
		final Optional<User> cfUser = new Optional<User>(null);
		dataManager.run(session -> {
			String hql = "FROM User WHERE username = :username" + (includeInactives ? "" : " AND active = 1");
	        Query query = session.createQuery(hql);
	        query.setParameter("username", username);
	        cfUser.set((User) query.uniqueResult());
		});
		return cfUser;
	}
	
	@Override
    public List<User> list() {
		return list(1, 15);
    }
	
	@Override
	public List<User> list(int page, int size, boolean includeInactives) {
		final Optional<List<User>> cfList = new Optional<List<User>>();
		dataManager.run(session -> {
			String sqlQuery = "SELECT username, null as password, name, active FROM users" + (includeInactives ? "" : " AND active = 1");
	        Query q = session.createSQLQuery(sqlQuery).addEntity(User.class);
			q.setFirstResult((page - 1) * size);
            q.setMaxResults(size);
			cfList.set(q.list());
		});
		return cfList.get();
    }
	
	
	public List<User> search(UserQuery q) {
		final Optional<List<User>> opt = new Optional<List<User>>();
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
	
	@Override
	@Deprecated
    public void erase(User user) {
		dataManager.transact(session -> {
			session.delete(user);
		});
	}

	@Override
	public List<User> list(int page, int size) {
		return list(page, size, false);
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
