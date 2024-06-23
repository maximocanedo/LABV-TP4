package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
	private TicketLogicImpl tickets;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
		String refreshToken = users.login(credentials.getUsername(), credentials.getPassword());
		return ResponseEntity.ok(refreshToken);
	}
	
		
	@GetMapping("/u/{username:.+}")
    public ResponseEntity<?> getUnprotectedUser(@PathVariable String username) {
        Optional<User> ou = users.findByUsername(username);
        if (ou.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else return ResponseEntity.ok(ou.get());
    }
	
	
	
}
