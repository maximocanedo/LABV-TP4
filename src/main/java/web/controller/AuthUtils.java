package web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.entity.User;
import web.exceptions.InvalidTokenException;
import web.logicImpl.TicketLogicImpl;

@Component("auth")
public class AuthUtils {
	
	@Autowired
	private TicketLogicImpl tickets;
	
	public AuthUtils() {}

	/**
	 * Requerir autenticación del usuario.
	 * @param req Petición HTTP.
	 * @param res Respuesta HTTP.
	 * @return Usuario autenticado.
	 */
	public User require(HttpServletRequest req, HttpServletResponse res) {
		if(req.getHeader("Authorization") == null || req.getHeader("Authorization").split(" ").length != 2) {
			throw new InvalidTokenException();
		}
		String fullToken = req.getHeader("Authorization");
        String tokenType = fullToken.split(" ")[0];
        String token = fullToken.split(" ")[1];
        String accessToken;

        if (tokenType.equals("Refresh")) {
            accessToken = tickets.generateAccessToken(token);
            res.setHeader("Authorization", "Bearer " + accessToken);
        } else {
            accessToken = token;
        }
        
        return tickets.validateAccessToken(accessToken);
	}
	
	/**
	 * Solicitar usuario autenticado. En caso de error no lanza ninguna excepción.
	 * @param req Petición HTTP.
	 * @param res Respuesta HTTP.
	 * @return Usuario autenticado, o null.
	 */
	public User inquire(HttpServletRequest req, HttpServletResponse res) {
		try {
			return require(req, res);
		} catch(Exception expected) {
			return null;
		}
	}
	
}
