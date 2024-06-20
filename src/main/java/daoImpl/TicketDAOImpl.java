package daoImpl;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.ITicketDAO;
import entity.Optional;
import entity.Ticket;

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
			String hql = "FROM Ticket WHERE id = :id AND active";
	        Query query = session.createQuery(hql);
	        query.setParameter("username", id);
	        cfUser.set((Ticket) query.uniqueResult());
		});
		return cfUser;
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

}
