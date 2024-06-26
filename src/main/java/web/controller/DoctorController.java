package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.entity.Doctor;
import web.entity.IDoctor;
import web.entity.User;
import web.entity.input.DoctorQuery;
import web.entity.input.FilterStatus;
import web.entity.output.ResponseContainer;
import web.entity.view.DoctorMinimalView;
import web.logicImpl.DoctorLogicImpl;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

	@Autowired
	private DoctorLogicImpl doctors;
	
	@Autowired
	private AuthUtils auth;
	
	@GetMapping
	public ResponseContainer<List<DoctorMinimalView>> search(
			@RequestParam(required = false, defaultValue = "") String q, 
			@RequestParam(required = false, defaultValue = "") FilterStatus status,
			@RequestParam(required = false, defaultValue = "") String day,
			@RequestParam(required = false, defaultValue = "") String specialty,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int size,
			HttpServletRequest req, HttpServletResponse res
			) {
		User requiring = auth.require(req, res);
		return ResponseContainer.of(
				doctors.search(
						new DoctorQuery(q, status)
								.filterByDay(day)
								.filterBySpecialty(specialty)
								.paginate(page, size)
						, requiring)
				);
	}
	
	/**
	 * Endpoint: Crear doctor.
	 * TODO: Validar entrada de información.
	 */
	@PostMapping
	public ResponseContainer<Doctor> create(
				@RequestBody Doctor doctor,
				HttpServletRequest req, HttpServletResponse res
			) {
		User requiring = auth.require(req, res);
		return ResponseContainer.of(doctors.add(doctor, requiring));
	}
	
	@GetMapping("/id/{id}")
	public ResponseContainer<IDoctor> findbyId(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return ResponseContainer.of(doctors.getById(id, requiring));
	}
	
	@GetMapping("/file/{file}")
	public ResponseContainer<IDoctor> findbyFile(@PathVariable int file, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return ResponseContainer.of(doctors.getByFile(file, requiring));
	}
	
	@PatchMapping("/id/{id}")
	public ResponseContainer<Doctor> updateById(@PathVariable int id, @RequestBody Doctor doctor, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		doctor.setId(id);
		return ResponseContainer.of(doctors.update(doctor, requiring));
	}
	
	@PatchMapping("/file/{file}")
	public ResponseContainer<Doctor> updateByFile(@PathVariable int file, @RequestBody Doctor doctor, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		doctor.setFile(file);
		return ResponseContainer.of(doctors.update(doctor, requiring));
	}

	@DeleteMapping("/id/{id}")
	public void disableById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		doctors.disable(id, requiring);
	}
	
	@PostMapping("/id/{id}")
	public void enableById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		doctors.enable(id, requiring);
	}
	
	
}
