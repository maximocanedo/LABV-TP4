package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import web.entity.IUser;
import web.entity.Permit;
import web.entity.User;
import web.entity.UserPermit;
import web.entity.input.*;
import web.entity.view.UserView;
import web.generator.PermitTemplate;
import web.logicImpl.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = { "Authorization", "Content-Length" })
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
	
	@InitBinder
    public void initBinder(HttpServletRequest req, HttpServletResponse res) {
        auth.preHandle(req, res);
    }
	
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
			@RequestParam(required = false, defaultValue = "false") boolean checkUnassigned,
			@RequestParam(required = false, defaultValue = "false") boolean fromSelector,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int size,
			HttpServletRequest req, HttpServletResponse res
			) {
		User requiring = auth.require(req, res);
		UserQuery query = new UserQuery(q, status).paginate(page, size).filterByUnassigned(checkUnassigned);
		System.out.println("selector users? " + fromSelector);
		if(fromSelector) users.searchForSelector(query, requiring);
		return users.search(query, requiring);
	}
	
	/** # Acciones con terceros **/
		
	@GetMapping("/u/{username:.+}")
    public IUser findUser(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
        return users.getByUsername(username, false, requiring);
    }

	@RequestMapping(value = "/u/{username:.+}", method = RequestMethod.HEAD)
    public void existsByUsername(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		boolean ok = users.exists(username);
		if(ok) res.setStatus(200);
		else res.setStatus(404);
    }
	
	@PutMapping("/u/{username:.+}")
	public User update(@PathVariable String username, @RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		user.setUsername(username);
		return (users.update(user, requiring));
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
	
	@PostMapping("/u/{username:.+}")
	public ResponseEntity<?> enable(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		users.enable(username, requiring);
		return ResponseEntity.status(200).build();
	}
	
	/** # Acciones con el usuario actual **/
	
	@GetMapping("/me")
    public User getCurrentUser(HttpServletRequest req, HttpServletResponse res) {
		return (auth.require(req, res));
    }
	
	@PutMapping("/me")
	public User updateCurrentUser(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		user.setUsername(requiring.getUsername());
		return (users.update(user, requiring));
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
	public UserPermit grantOne(@PathVariable String username, @PathVariable Permit permit, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return (userpermits.allow(username, permit, requiring));
	}
	
	@PostMapping("/u/{username:.+}/deny/p/{permit}")
	public UserPermit denyOne(@PathVariable String username, @PathVariable Permit permit, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return (userpermits.reject(username, permit, requiring));		
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
