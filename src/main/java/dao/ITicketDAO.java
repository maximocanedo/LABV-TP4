package dao;

import entity.Optional;
import entity.Ticket;

public interface ITicketDAO {

	Ticket add(Ticket ticket);
	
	Optional<Ticket> getById(int id);
	
	Ticket update(Ticket ticket);
	
	void remove(Ticket ticket);
	
}
