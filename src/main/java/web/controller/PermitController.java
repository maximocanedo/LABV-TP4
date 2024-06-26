package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.entity.Permit;
import web.entity.User;
import web.entity.UserPermit;
import web.entity.output.ResponseContainer;
import web.generator.PermitTemplate;
import web.logicImpl.UserPermitLogicImpl;

@RestController
@RequestMapping("/access/")
public class PermitController {

	@Autowired
	private UserPermitLogicImpl userpermits;
	
	@Autowired
	private AuthUtils auth;
	
	@GetMapping("/{username:.+}")
	public ResponseContainer<List<UserPermit>> getPermissionsForUser(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		// User requiring = auth.require(req, res); // No requiere permiso especial consultar los permisos de un usuario. 
		return ResponseContainer.of(userpermits.list(username));
	}
	
	@DeleteMapping("/{username:.+}")
	public ResponseContainer<Integer> revokeAll(@PathVariable String username, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return ResponseContainer.of(userpermits.revokeAll(username, requiring));
	}
	
	@PostMapping("/{username:.+}")
	public String applyTemplate(@PathVariable String username, @RequestParam String template, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		userpermits.grant(username, PermitTemplate.getByName(template), requiring);
		return "";
	}
	
	// Permisos individuales
	
	@RequestMapping(value = "/{username:.+}/{permit}", method = RequestMethod.HEAD)
	public ResponseEntity<?> checkPermission(@PathVariable String username, @PathVariable Permit permit) {
		return ResponseEntity.status(
			userpermits.check(username, permit) ? 200 : 403	
		).build();
	}
	
	@PostMapping("/{username:.+}/{permit}")
	public ResponseContainer<UserPermit> grant(@PathVariable String username, @PathVariable Permit permit, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return ResponseContainer.of(
			userpermits.allow(username, permit, requiring)	
		);
	}
	
	@DeleteMapping("/{username:.+}/{permit}")
	public ResponseContainer<UserPermit> deny(@PathVariable String username, @PathVariable Permit permit, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return ResponseContainer.of(
			userpermits.reject(username, permit, requiring)	
		);
	}
	
}
