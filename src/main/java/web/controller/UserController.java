package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import web.entity.Optional;
import web.entity.User;
import web.logicImpl.UserLogicImpl;

@Component
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserLogicImpl users;
	
	
	@GetMapping
	public Optional<User> getUser() {
		return users.findByUsername("abe.bogan");
	}
	
}
