package daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.ITicketDAO;
import entity.Optional;
import entity.Ticket;
import entity.User;
import exceptions.NotFoundException;

@Component
public class TicketDAOImpl implements ITicketDAO {
	
	@Autowired
	private DataManager dataManager;
	
	public TicketDAOImpl() {}

	@Override
	public Ticket add(Ticket ticket) {
		dataManager.transact(session -> {
			session.save(ticket);
		});
		return ticket;
	}

	@Override
	public Optional<Ticket> getById(int id) {
		final Optional<Ticket> cfUser = new Optional<Ticket>(null);
		dataManager.run(session -> {
			String hql = "FROM Ticket WHERE id = :id AND active = 1";
	        Query query = session.createQuery(hql);
	        query.setParameter("id", id);
	        cfUser.set((Ticket) query.uniqueResult());
		});
		return cfUser;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Ticket> getSessions(String username, int page, int size) {
		final Optional<List<Ticket>> opt = new Optional<List<Ticket>>(null);
		dataManager.run(session -> {
			String q = "SELECT t FROM Ticket WHERE t.user = :username";
			Query query = session.createQuery(q);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            opt.set(query.list());
		});
		return opt.get();
	}
	
	@Override
	public List<Ticket> getSessions(User user, int page, int size) {
		return getSessions(user.getUsername(), page, size);
	}
	
	@Override
	public int closeAllSessions(String username) {
		Optional<Integer> opt = new Optional<Integer>(0);
		dataManager.run(session -> {
			String q = "UPDATE Ticket t SET t.active = 0 WHERE t.user = :username";
			Query query = session.createQuery(q);
			query.setParameter("username", username);
			opt.set(query.executeUpdate());
		});
		return opt.get().intValue();
	}
	
	@Override
	public int closeAllSessions(User user) {
		return closeAllSessions(user.getUsername());
	}
	
	@Override
	public Ticket update(Ticket ticket) {
		dataManager.transact(session -> {
			session.update(ticket);
		});
		return ticket;
	}

	@Override
	public void remove(Ticket ticket) {
		ticket.setActive(false);
		dataManager.transact(session -> {
			session.update(ticket);
		});
	}

	@Override
	public Ticket logout(int id) throws NotFoundException {
		Optional<Ticket> opt = getById(id);
		if(opt.isEmpty()) throw new NotFoundException();
		Ticket ticket = opt.get();
		ticket.setActive(false);
		return update(ticket);
	}

	@Override
	public Ticket logout(Ticket ticket) throws NotFoundException {
		return logout(ticket.getId());
	}

	
	
}