package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.entity.Optional;
import web.entity.Patient;
import web.entity.User;
import web.entity.input.FilterStatus;
import web.entity.input.UserQuery;
import web.logicImpl.PatientLogicImpl;

@RestController
@RequestMapping("/patients")
public class PatientController {
		@Autowired
		private PatientLogicImpl patients;
		
		@Autowired 
		private AuthUtils auth;
		
		// Acciones Generales
		/*
		@GetMapping
		public List<Patient> search(
				@RequestParam(required = false, defaultValue = "") String q, 
				@RequestParam(required = false, defaultValue = "") FilterStatus status,
				@RequestParam(required = false, defaultValue = "1") int page,
				@RequestParam(required = false, defaultValue = "15") int size,
				HttpServletRequest req, HttpServletResponse res
				) {
			User requiring = auth.require(req, res);
			return patients.list(new UserQuery(q, status).paginate(page, size), requiring);
		}
		*/
		
		// Acciones con Terceros
		@GetMapping("/p/{id}")
		public Optional<Patient> findById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
			User requiring = auth.require(req, res);
	        return patients.findById(id, requiring);
		}
		
		
		@PatchMapping("/p/{id}")
		public Patient update(@PathVariable int id, @RequestBody Patient patient, HttpServletRequest req, HttpServletResponse res) {
			User requiring = auth.require(req, res);
			patient.setId(id);
			return patients.update(patient, requiring);
		}
		
		
		// Acciones Paciente Actual
		

}
