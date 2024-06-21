package logicImpl;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import daoImpl.TicketDAOImpl;
import daoImpl.UserDAOImpl;
import entity.Optional;
import entity.Ticket;
import entity.User;
import exceptions.InvalidTokenException;
import exceptions.NotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class TicketLogicImpl {

	@Autowired
	private UserDAOImpl usersrepository;
	
	@Autowired
	private TicketDAOImpl ticketsrepository;
	
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
    
    public String startToken(String username, String deviceAgent, String deviceIdentifier) throws NotFoundException {
    	Ticket ticket = startSession(username, deviceAgent, deviceIdentifier);
    	//System.out.println("Ticket de startToken: \n" + ticket);
    	return generateToken(ticket);
    }

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
    
}
