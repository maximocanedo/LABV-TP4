package web.logicImpl;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import web.daoImpl.TicketDAOImpl;
import web.daoImpl.UserDAOImpl;
import web.entity.Optional;
import web.entity.Permit;
import web.entity.Ticket;
import web.entity.User;
import web.exceptions.CommonException;
import web.exceptions.InvalidTokenException;
import web.exceptions.NotFoundException;
import web.logic.ITicketLogic;

@Component("tickets")
public class TicketLogicImpl implements ITicketLogic {

	@Autowired
	private UserDAOImpl usersrepository;
	
	@Autowired
	private TicketDAOImpl ticketsrepository;
	
	@Autowired
	private UserPermitLogicImpl permits;
	
	private String secretKey = "Q2xhdmVTZWNyZXRhUXVlRGViZXLDoVNlckFzZWd1cmFkYUFudGVzRGVMYUVudHJlZ2FEZWxUUA==";
	
	public TicketLogicImpl() {}

    private String getSecretKey() {
        return secretKey;
    }

    private byte[] getSecretKeyBytes() {
        return Decoders.BASE64.decode(this.getSecretKey());
    }

    private SecretKey getSessionKey() {
        return Keys.hmacShaKeyFor(this.getSecretKeyBytes());
    }
    
    private String generateToken(Ticket ticket) {
        return Jwts.builder()
                .subject(ticket.getUser().getUsername())
                .issuedAt(ticket.getCreationDate())
                .claim("sessionId", ticket.getId())
                .expiration(ticket.getExpirationDate())
                .signWith(this.getSessionKey())
                .compact();
    }
    
    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#startSession(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public Ticket startSession(String username, String deviceAgent, String deviceIdentifier) throws NotFoundException {
        Optional<User> optionalUser = usersrepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new NotFoundException();
        User user = optionalUser.get();
        if(!user.isActive())
            throw new NotFoundException();
        long currentTime = System.currentTimeMillis();
        Date now = new Date(currentTime);
        long expMillis = currentTime + (24 * 60 * 60 * 1000);
        Date exp = new Date(expMillis);
        Ticket session = new Ticket();
        session.setActive(true);
        session.setUser(user);
        session.setCreationDate(now);
        session.setExpirationDate(exp);
        session.setVersion(0);
        session.setDeviceAgent(deviceAgent);
        session.setDeviceIdentifier(deviceIdentifier);
        return ticketsrepository.add(session);
    }
    
    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#startToken(java.lang.String, java.lang.String, java.lang.String)
	 */
    @Override
	public String startToken(String username, String deviceAgent, String deviceIdentifier) throws NotFoundException {
    	Ticket ticket = startSession(username, deviceAgent, deviceIdentifier);
    	//System.out.println("Ticket de startToken: \n" + ticket);
    	return generateToken(ticket);
    }

    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#validateTokenId(int)
	 */
    @Override
	public Ticket validateTokenId(int id) {
    	Optional<Ticket> optionalTicket = ticketsrepository.getById(id);
    	//System.out.println(optionalTicket.get());
        if(optionalTicket.isEmpty())
            throw new InvalidTokenException("Referenced session info doesn't exist.");
        Ticket ticket = optionalTicket.get();
        if(!ticket.isActive())
            throw new InvalidTokenException("Session was closed.");
        return ticket;
    }
        
    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#validateRefreshToken(java.lang.String)
	 */
    @Override
	public Ticket validateRefreshToken(String token) {
    	JwtParser builder = Jwts.parser()
    			.verifyWith(this.getSessionKey())
                .build();
                
        Claims claims = null;
        
	    try {
	    	claims = builder
	        		.parseSignedClaims(token)
	                .getPayload();
	    } catch (SignatureException e) {
	    	throw new InvalidTokenException();
	    }

        Date expiration = claims.getExpiration();
        int id = claims.get("sessionId", java.lang.Integer.class);

        Ticket ticket = validateTokenId(id);
        User user = ticket.getUser();

        if(System.currentTimeMillis() > expiration.getTime())
            throw new InvalidTokenException("Session expired.");
        if(!user.isActive())
            throw new InvalidTokenException("User trying to authenticate does not exist.");

        return ticket;
    }
	
    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#generateAccessToken(java.lang.String)
	 */
    @Override
	public String generateAccessToken(String refreshToken) {
        Ticket ticket = validateRefreshToken(refreshToken);
        long currentTime = System.currentTimeMillis();
        Date now = new Date(currentTime);
        long expMillis = currentTime + (10 * 60 * 1000);
        Date exp = new Date(expMillis);
        return Jwts.builder()
                .subject(ticket.getId() + "")
                .issuedAt(now)
                .expiration(exp)
                .signWith(this.getSessionKey())
                .compact();
    }
    
    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#validateAccessToken(java.lang.String)
	 */
    @Override
	public User validateAccessToken(String accessToken) {
        Claims claims = Jwts.parser()
                .verifyWith(this.getSessionKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
        int id = Integer.parseInt(claims.getSubject());
        Optional<Ticket> optionalTicket = ticketsrepository.getById(id);
        if(optionalTicket.isEmpty())
        	throw new InvalidTokenException("Session was closed.");
        Ticket ticket = optionalTicket.get();
        Date exp = claims.getExpiration();
        if(System.currentTimeMillis() > exp.getTime())
            throw new InvalidTokenException("Session expired.");
        return ticket.getUser();
    }
    
    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#getCurrentUser(java.lang.String, java.lang.String)
	 */
    @Override
	public User getCurrentUser(String accessToken, String refreshToken) {
    	try {
    		User u = validateAccessToken(accessToken);
    		return u;
    	} catch(InvalidTokenException e) {
    		accessToken = generateAccessToken(refreshToken);
    		try {
    			User u = validateAccessToken(accessToken);
        		return u;
    		} catch(CommonException ex) {
    			throw ex;
    		}
    	} catch(CommonException e) {
    		throw e;
    	}
    }
    
    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#logout(int, entity.User)
	 */
    @Override
	public void logout(int id, User requiring) {
    	Ticket ticket = validateTokenId(id);
    	if(ticket.getUser().getUsername() != requiring.getUsername()) 
    		permits.require(requiring, Permit.CLOSE_USER_SESSIONS);
    	ticketsrepository.logout(id);
    }
    
    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#closeAllSessions(entity.User, entity.User)
	 */
    @Override
	public void closeAllSessions(User target, User requiring) {
    	if(target.getUsername() != requiring.getUsername()) 
    		permits.require(requiring, Permit.CLOSE_USER_SESSIONS);
    	ticketsrepository.closeAllSessions(target);
    }
    
    /* (non-Javadoc)
	 * @see logicImpl.ITicketLogic#closeAllSessions(java.lang.String, entity.User)
	 */
    @Override
	public void closeAllSessions(String username, User requiring) {
    	if(username != requiring.getUsername()) 
    		permits.require(requiring, Permit.CLOSE_USER_SESSIONS);
    	ticketsrepository.closeAllSessions(username);
    }
    
    
}
