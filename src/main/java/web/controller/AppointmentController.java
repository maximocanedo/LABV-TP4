package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.entity.Appointment;
import web.entity.IAppointment;
import web.entity.User;
import web.entity.input.AppointmentQuery;
import web.entity.input.FilterStatus;
import web.entity.view.AppointmentMinimalView;
import web.logicImpl.AppointmentLogicImpl;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	@Autowired
	private AppointmentLogicImpl appointments;
	
	@Autowired 
	private AuthUtils auth;
	
	public List<AppointmentMinimalView> search(
			@RequestParam(required = false, defaultValue = "") String q, 
			@RequestParam(required = false, defaultValue = "") FilterStatus status,
			@RequestParam(required = false, defaultValue = "") String appointmentStatus,
			@RequestParam(required = false, defaultValue = "") String date,
			@RequestParam(required = false, defaultValue = "") String limit,
			@RequestParam(required = false, defaultValue = "") String doctorFile,
			@RequestParam(required = false, defaultValue = "") String doctorId,
			@RequestParam(required = false, defaultValue = "") String patientDni,
			@RequestParam(required = false, defaultValue = "") String patientId,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "15") String size,
			HttpServletRequest req, HttpServletResponse res
			) {
		User requiring = auth.require(req, res);
		return appointments.search(
						new AppointmentQuery(q, status)
								.paginate(page, size)
								.filterByStatus(appointmentStatus)
								.filterByDate(date, limit)
								.filterByDoctor(doctorFile, doctorId)
								.filterByPatient(patientDni, patientId)
						, requiring);
	}
	
	@PostMapping
	public Appointment create(@RequestBody Appointment Appointment, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return appointments.register(Appointment, requiring);
	}
	
	// Acciones con Terceros
	@GetMapping("/id/{id}")
	public IAppointment findById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
        return appointments.getById(id, requiring);
	}

	@PatchMapping("/id/{id}")
	public Appointment update(@PathVariable int id, @RequestBody Appointment Appointment, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		Appointment.setId(id);
		return appointments.update(Appointment, requiring);
	}	
	
	@DeleteMapping("/id/{id}")
	public ResponseEntity<?> disable(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		appointments.disable(id, requiring);
		return ResponseEntity.status(200).build();
	}
	
	@PostMapping("/id/{id}")
	public ResponseEntity<?> enable(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		appointments.enable(id, requiring);
		return ResponseEntity.status(200).build();
	}
	
}
