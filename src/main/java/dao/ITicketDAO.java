package dao;

import java.util.List;

import entity.Optional;
import entity.Ticket;
import entity.User;
import exceptions.NotFoundException;

public interface ITicketDAO {

	Ticket add(Ticket ticket);
	
	Optional<Ticket> getById(int id);
	
	List<Ticket> getSessions(String username, int page, int size);
	
	List<Ticket> getSessions(User user, int page, int size);
	
	Ticket update(Ticket ticket);
	
	void remove(Ticket ticket);
	
	int closeAllSessions(String username);
	
	int closeAllSessions(User user);
	
	Ticket logout(int id) throws NotFoundException;
	
	Ticket logout(Ticket ticket) throws NotFoundException;
	
}
