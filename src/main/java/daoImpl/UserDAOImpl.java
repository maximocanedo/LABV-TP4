package daoImpl;

import java.util.List;


import org.hibernate.*;

import dao.IUserDAO;
import entity.Optional;
import entity.User;

public class UserDAOImpl implements IUserDAO {
	
	public UserDAOImpl() {}
		
	@Override
	public void add(User user) {
		DataManager.transact(session -> {
			session.save(user);
		});
    }
	
	@Override
    public Optional<User> findByUsername(String username) {
		final Optional<User> cfUser = new Optional<User>(null);
		DataManager.run(session -> {
			String hql = "FROM User WHERE username = :username";
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
    @SuppressWarnings("unchecked")
	public List<User> list(int page, int size) {
		final Optional<List<User>> cfList = new Optional<List<User>>();
		DataManager.run(session -> {
			String sqlQuery = "SELECT username, null as password, name, active FROM users";
	        Query q = session.createSQLQuery(sqlQuery).addEntity(User.class);
			q.setFirstResult((page - 1) * size);
            q.setMaxResults(size);
			cfList.set(q.list());
		});
		return cfList.get();
    }
	
	@Override
    public void update(User user) {
		DataManager.transact(session -> {
			session.update(user);
		});
	}
	
	@Override
    public void erase(User user) {
		DataManager.transact(session -> {
			session.delete(user);
		});
	}
	
	
	
}
