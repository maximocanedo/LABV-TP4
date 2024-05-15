package daoImpl;

import java.util.List;


import org.hibernate.*;

import dao.IUserDAO;
import daoImpl.DataManager.ContainerFor;
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
	public User getByUsername(String username) {
		final ContainerFor<User> cfUser = new ContainerFor<User>(null);
		DataManager.run(session -> {
			String hql = "FROM User WHERE username = :username";
	        Query query = session.createQuery(hql);
	        query.setParameter("username", username);
	        cfUser.object = (User) query.uniqueResult();
		});
		return cfUser.object;
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
		final ContainerFor<List<User>> cfList = new ContainerFor<List<User>>(null);
		DataManager.run(session -> {
			String sqlQuery = "SELECT username, null as password, name, active FROM users";
	        Query q = session.createSQLQuery(sqlQuery).addEntity(User.class);
			q.setFirstResult((page - 1) * size);
            q.setMaxResults(size);
			cfList.object = q.list();
		});
		return cfList.object;
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
