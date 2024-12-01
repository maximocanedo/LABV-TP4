package web.logic;

import java.util.List;

import web.entity.Optional;
import web.entity.Ticket;
import web.entity.User;
import web.entity.input.TicketQuery;
import web.exceptions.NotFoundException;

public interface ITicketLogic {

	Ticket startSession(String username, String deviceAgent, String deviceIdentifier) throws NotFoundException;

	String startToken(String username, String deviceAgent, String deviceIdentifier) throws NotFoundException;

	Ticket validateTokenId(int id);

	Ticket validateRefreshToken(String token);

	String generateAccessToken(String refreshToken);

	User validateAccessToken(String accessToken);

	User getCurrentUser(String accessToken, String refreshToken);

	void logout(int id, User requiring);

	void closeAllSessions(User target, User requiring);

	void closeAllSessions(String username, User requiring);

	/**
     * Agrega un ticket a la base de datos.
     * @param Ticket Ticket a agregar.
	 * @return 
     */
	Ticket add(Ticket Ticket, User requiring);

	/**
     * Busca un ticket por su id.
     * @param id Id del ticket.
     * @return Objeto ticket con los datos
     */
	Optional<Ticket> findById(int id);

	List<Ticket> search(TicketQuery query, User requiring);
	
	/**
     * Actualiza un ticket en la base de datos.
     * @param Ticket ticket con los datos a actualizar.
     */
	Ticket update(Ticket Ticket, User requiring) throws NotFoundException;

}