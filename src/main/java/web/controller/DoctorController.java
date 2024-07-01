package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.entity.Doctor;
import web.entity.Patient;
import web.entity.User;
import web.entity.input.DoctorQuery;
import web.entity.input.FilterStatus;
import web.entity.input.PatientQuery;
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
	
}
