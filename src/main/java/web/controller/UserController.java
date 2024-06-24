package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.entity.User;
import web.entity.input.FilterStatus;
import web.entity.input.UserCredentials;
import web.entity.input.UserQuery;
import web.logicImpl.UserLogicImpl;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserLogicImpl users;
	
	@Autowired 
	private AuthUtils auth;
	
	/** # Acciones generales **/
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
		String refreshToken = users.login(credentials.getUsername(), credentials.getPassword());
		HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Refresh " + refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                             .headers(headers)
                             .build(); 
	}
	
	@PostMapping
	public User signup(@RequestBody User user, @RequestParam(required = false) String role, HttpServletRequest req, HttpServletResponse res) {
		if(role != null) {
			User requiring = auth.require(req, res);
			return users.signup(user, role, requiring);
		} return users.signup(user);
	}
	
	@GetMapping
	public List<User> search(
			@RequestParam(required = false, defaultValue = "") String q, 
			@RequestParam(required = false, defaultValue = "") FilterStatus status,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int size,
			HttpServletRequest req, HttpServletResponse res
			) {
		User requiring = auth.require(req, res);
		return users.search(new UserQuery(q, status).paginate(page, size), requiring);
	}
	
	/** # Acciones con terceros **/
		
	@GetMapping("/u/{username:.+}")
    public User findUser(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
        return users.getByUsername(username, requiring);
    }
	
	@PutMapping("/u/{username:.+}")
	public User update(@PathVariable String username, @RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		user.setUsername(username);
		return users.update(user, requiring);
	}
	
	@PostMapping("/u/{username:.+}/reset-password")
	public ResponseEntity<?> changePassword(@PathVariable String username, @RequestBody UserCredentials credentials, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		users.changePassword(username, credentials.getNewPassword(), requiring);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/u/{username:.+}")
	public ResponseEntity<?> disable(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		users.disable(username, requiring);
		return ResponseEntity.status(200).build();
	}
	
	/** # Acciones con el usuario actual **/
	
	@GetMapping("/me")
    public User getCurrentUser(HttpServletRequest req, HttpServletResponse res) {
		return auth.require(req, res);
    }
	
	@PutMapping("/me")
	public User updateCurrentUser(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		user.setUsername(requiring.getUsername());
		return users.update(user, requiring);
	}
	
	@PostMapping("/me/reset-password")
	public ResponseEntity<?> changeMyPassword(@RequestBody UserCredentials credentials, HttpServletRequest req, HttpServletResponse res) {
		users.changePassword(credentials.getUsername(), credentials.getPassword(), credentials.getNewPassword());
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/me")
	public ResponseEntity<?> disableCurrentUser(HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		users.disable(requiring, requiring);
		return ResponseEntity.status(200).build();
	}
	
	
}
