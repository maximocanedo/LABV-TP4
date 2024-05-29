package daoImpl;

import java.util.List;


import org.hibernate.*;

import dao.IUserDAO;
import entity.Optional;
import entity.User;

public class UserDAOImpl implements IUserDAO {
	
	public UserDAOImpl() {}
		
	/* (non-Javadoc)
	 * @see daoImpl.IUserDAO#add(entity.User)
	 */
	@Override
	public void add(User user) {
		DataManager.transact(session -> {
			session.save(user);
		});
    }
	
	/* (non-Javadoc)
	 * @see daoImpl.IUserDAO#getByUsername(java.lang.String)
	 */
	@Override
	public Optional<User> getByUsername(String username) {
		final Optional<User> cfUser = new Optional<User>(null);
		DataManager.run(session -> {
			String hql = "FROM User WHERE username = :username";
	        Query query = session.createQuery(hql);
	        query.setParameter("username", username);
	        cfUser.set((User) query.uniqueResult());
		});
		return cfUser;
	}
	
	
	
	/* (non-Javadoc)
	 * @see daoImpl.IUserDAO#list()
	 */
	@Override
	public List<User> list() {
		return list(1, 15);
    }
	
	/* (non-Javadoc)
	 * @see daoImpl.IUserDAO#list(int, int)
	 */
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
	
	/* (non-Javadoc)
	 * @see daoImpl.IUserDAO#update(entity.User)
	 */
	@Override
	public void update(User user) {
		DataManager.transact(session -> {
			session.update(user);
		});
	}
	
	/* (non-Javadoc)
	 * @see daoImpl.IUserDAO#erase(entity.User)
	 */
	@Override
	public void erase(User user) {
		DataManager.transact(session -> {
			session.delete(user);
		});
	}
	
	
	
}
