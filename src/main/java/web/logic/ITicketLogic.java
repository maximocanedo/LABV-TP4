package web.logic;

import web.entity.Ticket;
import web.entity.User;
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

}