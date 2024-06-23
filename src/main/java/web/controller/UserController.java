package web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.entity.Optional;
import web.entity.User;
import web.entity.input.UserCredentials;
import web.logicImpl.TicketLogicImpl;
import web.logicImpl.UserLogicImpl;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserLogicImpl users;
	
	@Autowired 
	private AuthUtils auth;
	
	@Autowired
	private TicketLogicImpl tickets;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
		String refreshToken = users.login(credentials.getUsername(), credentials.getPassword());
		HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Refresh " + refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .build(); 
	}
	
	@GetMapping("/legacy")
    public ResponseEntity<User> getMeser(HttpServletRequest request) {
        String fullToken = request.getHeader("Authorization");
        String tokenType = fullToken.split(" ")[0];
        String token = fullToken.split(" ")[1];
        String accessToken = "";
        HttpHeaders headers = new HttpHeaders();
        if(tokenType.equals("Refresh")) {
        	// Es refresh token
        	accessToken = tickets.generateAccessToken(token);
        	headers.add("Authorization", "Bearer " + accessToken);
        } else accessToken = token;
        User me = tickets.validateAccessToken(accessToken);
        return ResponseEntity.status(200)
        					.headers(headers)
        					.body(me);
       // Optional<User> ou = users.findByUsername(username);
    }
	
	@GetMapping("/me")
    public String getCurrentUser(HttpServletRequest req, HttpServletResponse res) {
		return auth.require(req, res);
       // Optional<User> ou = users.findByUsername(username);
    }
		
	@GetMapping("/ui/{username:.+}")
    public ResponseEntity<?> getUnprotectedUser(@PathVariable String username) {
        Optional<User> ou = users.findByUsername(username);
        if (ou.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.ok(ou.get());
    }
	
	
	
}
