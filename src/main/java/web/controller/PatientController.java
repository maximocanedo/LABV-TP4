package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.entity.IPatient;
import web.entity.Patient;
import web.entity.User;
import web.entity.input.FilterStatus;
import web.entity.input.PatientQuery;
import web.entity.view.PatientCommunicationView;
import web.logicImpl.PatientLogicImpl;

@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = { "Authorization", "Content-Length" })
public class PatientController {
		@Autowired
		private PatientLogicImpl patients;
		
		@Autowired 
		private AuthUtils auth;
		
		// Acciones Generales

		@InitBinder
	    public void initBinder(HttpServletRequest req, HttpServletResponse res) {
	        auth.preHandle(req, res);
	    }
		
		@GetMapping
		public List<PatientCommunicationView> search(
				@RequestParam(required = false, defaultValue = "") String q, 
				@RequestParam(required = false, defaultValue = "") FilterStatus status,
				@RequestParam(required = false, defaultValue = "1") int page,
				@RequestParam(required = false, defaultValue = "15") int size,
				HttpServletRequest req, HttpServletResponse res
				) {
			User requiring = auth.require(req, res);
			return patients.search(
							new PatientQuery(q, status)
									.paginate(page, size)
							, requiring);
		}
		
		@PostMapping
		public Patient create(@RequestBody Patient patient, HttpServletRequest req, HttpServletResponse res) {
			User requiring = auth.require(req, res);
			return patients.add(patient, requiring);
		}
		
		
		// Acciones con Terceros
		@GetMapping("/id/{id}")
		public IPatient findById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
			User requiring = auth.require(req, res);
	        return patients.getById(id, requiring);
		}
		
		
		@PatchMapping("/id/{id}")
		public Patient update(@PathVariable int id, @RequestBody Patient patient, HttpServletRequest req, HttpServletResponse res) {
			User requiring = auth.require(req, res);
			patient.setId(id);
			return patients.update(patient, requiring);
		}
		
		@PostMapping("/id/{id}")
		public void enable(@PathVariable int id, @RequestBody Patient patient, HttpServletRequest req, HttpServletResponse res) {
			User requiring = auth.require(req, res);
			patients.enable(id, requiring);
		}
		
		@DeleteMapping("/id/{id}")
		public ResponseEntity<?> disable(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
			User requiring = auth.require(req, res);
			patients.disable(id, requiring);
			return ResponseEntity.status(200).build();
		}

}
