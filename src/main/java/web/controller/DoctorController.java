package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import web.entity.Doctor;
import web.entity.IDoctor;
import web.entity.User;
import web.entity.input.DoctorQuery;
import web.entity.input.FilterStatus;
import web.entity.view.DoctorMinimalView;
import web.logicImpl.DoctorLogicImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = { "Authorization", "Content-Length" })
@RestController
@RequestMapping("/doctors")
public class DoctorController {

	@Autowired
	private DoctorLogicImpl doctors;
	
	@Autowired
	private AuthUtils auth;
	

	@InitBinder
    public void initBinder(HttpServletRequest req, HttpServletResponse res) {
        auth.preHandle(req, res);
    }
	
	
	@GetMapping
	public List<DoctorMinimalView> search(
			@RequestParam(required = false, defaultValue = "") String q, 
			@RequestParam(required = false, defaultValue = "") FilterStatus status,
			@RequestParam(required = false, defaultValue = "") String day,
			@RequestParam(required = false, defaultValue = "") String specialty,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int size,
			HttpServletRequest req, HttpServletResponse res
			) {
		User requiring = auth.require(req, res);
		return (
				doctors.search(
						new DoctorQuery(q, status)
								.filterByDay(day)
								.filterBySpecialty(specialty)
								.paginate(page, size)
						, requiring)
				);
	}

	@PostMapping
	public Doctor create(
				@RequestBody Doctor doctor,
				HttpServletRequest req, HttpServletResponse res
			) {
		User requiring = auth.require(req, res);
		return (doctors.add(doctor, requiring));
	}
	
	@GetMapping("/id/{id}")
	public IDoctor findbyId(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return (doctors.getById(id, requiring));
	}
	
	@GetMapping("/file/{file}")
	public IDoctor findbyFile(@PathVariable int file, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return (doctors.getByFile(file, requiring));
	}
	
	@PatchMapping("/id/{id}")
	public Doctor updateById(@PathVariable int id, @RequestBody Doctor doctor, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		doctor.setId(id);
		return (doctors.update(doctor, requiring));
	}
	
	@PatchMapping("/file/{file}")
	public Doctor updateByFile(@PathVariable int file, @RequestBody Doctor doctor, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		doctor.setFile(file);
		return (doctors.update(doctor, requiring));
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
