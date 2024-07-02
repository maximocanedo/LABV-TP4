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

import web.entity.IUser;
import web.entity.Permit;
import web.entity.User;
import web.entity.UserPermit;
import web.entity.input.FilterStatus;
import web.entity.input.SignUpRequest;
import web.entity.input.UserCredentials;
import web.entity.input.UserQuery;
import web.entity.output.ResponseContainer;
import web.entity.view.UserView;
import web.generator.PermitTemplate;
import web.logicImpl.UserLogicImpl;
import web.logicImpl.UserPermitLogicImpl;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserLogicImpl users;
	
	@Autowired
	private UserPermitLogicImpl userpermits;
	
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
	public User signup(@RequestBody SignUpRequest signUpRequest, @RequestParam(required = false) String role, HttpServletRequest req, HttpServletResponse res) {
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setUsername(signUpRequest.getUsername());
		user.setDoctor(signUpRequest.getDoctor());
		user.setPassword(signUpRequest.getPassword());
		user.setActive(true);
		if(role != null) {
			User requiring = auth.require(req, res);
			return users.signup(user, role, requiring);
		} return users.signup(user);
	}
	
	@GetMapping
	public List<UserView> search(
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
    public ResponseContainer<IUser> findUser(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
        return ResponseContainer.of(users.getByUsername(username, false, requiring));
    }
	
	@PutMapping("/u/{username:.+}")
	public ResponseContainer<User> update(@PathVariable String username, @RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		user.setUsername(username);
		return ResponseContainer.of(users.update(user, requiring));
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
    public ResponseContainer<User> getCurrentUser(HttpServletRequest req, HttpServletResponse res) {
		return ResponseContainer.of(auth.require(req, res));
    }
	
	@PutMapping("/me")
	public ResponseContainer<User> updateCurrentUser(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		user.setUsername(requiring.getUsername());
		return ResponseContainer.of(users.update(user, requiring));
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
	
	/** Permisos **/
	
	@PostMapping("/u/{username:.+}/grant/p/{permit}")
	public ResponseContainer<UserPermit> grantOne(@PathVariable String username, @PathVariable Permit permit, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return ResponseContainer.of(userpermits.allow(username, permit, requiring));
	}
	@PostMapping("/u/{username:.+}/deny/p/{permit}")
	public ResponseContainer<UserPermit> denyOne(@PathVariable String username, @PathVariable Permit permit, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return ResponseContainer.of(userpermits.reject(username, permit, requiring));		
	}
	@PostMapping("/u/{username:.+}/grant/all")
	public void grantAll(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		userpermits.grant(username, PermitTemplate.ROOT, requiring);
	}
	@PostMapping("/u/{username:.+}/deny/all")
	public void denyAll(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		userpermits.revokeAll(username, requiring);
	}
	@PostMapping("/u/{username:.+}/grant/t/{template}")
	public void grantTemplate(@PathVariable String username, @PathVariable String template, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		userpermits.grant(username, PermitTemplate.getByName(template), requiring);		
	}
	
	
	
}
