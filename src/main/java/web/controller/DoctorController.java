package web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import web.entity.Doctor;
import web.entity.IDoctor;
import web.entity.Schedule;
import web.entity.User;
import web.entity.input.DoctorQuery;
import web.entity.input.FilterStatus;
import web.entity.view.DoctorMinimalView;
import web.exceptions.ValidationException;
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
			@RequestParam(required = false, defaultValue = "false") boolean fromSelector,
			@RequestParam(required = false, defaultValue = "false") boolean checkUnassigned,
			@RequestParam(required = false, defaultValue = "") String specialty,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int size,
			HttpServletRequest req, HttpServletResponse res
			) {
		User requiring = auth.require(req, res);
		DoctorQuery query = new DoctorQuery(q, status)
								.filterByDay(day)
								.filterBySpecialty(specialty)
								.filterByUnassigned(checkUnassigned)
								.paginate(page, size);
		if(fromSelector) return doctors.searchForSelector(query, requiring);
		return doctors.search(query, requiring);
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
	

	@RequestMapping(value = "/file/{file}", method = RequestMethod.HEAD)
	public void existsbyFile(@PathVariable int file, HttpServletRequest req, HttpServletResponse res) {
		boolean exists = doctors.existsByFile(file);
		res.setStatus(exists ? 200 : 404);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.HEAD)
	public void existsbyId(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		boolean exists = doctors.existsById(id);
		res.setStatus(exists ? 200 : 404);
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
	public ResponseEntity<?> disableById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		doctors.disable(id, requiring);
		return ResponseEntity.status(200).build();
	}
	
	@PostMapping("/id/{id}")
	public ResponseEntity<?> enableById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		doctors.enable(id, requiring);
		return ResponseEntity.status(200).build();
	}

	
	@DeleteMapping("/id/{id}/schedules/{schedule}")
	public Set<Schedule> deleteSchedule(@PathVariable int id, @PathVariable int schedule, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return doctors.removeScheduleById(id, schedule, requiring);
	}
	
	@DeleteMapping("/file/{file}/schedules/{schedule}")
	public Set<Schedule> deleteScheduleByFile(@PathVariable int file, @PathVariable int schedule, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return doctors.removeSchedule(file, schedule, requiring);
	}
	
	@PostMapping(value = "/id/{id}/schedules", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Set<Schedule> addSchedule(@PathVariable int id, @RequestBody Schedule schedule, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return doctors.addScheduleById(id, schedule, requiring);
	}
	
	@PostMapping(value = "/file/{file}/schedules", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Set<Schedule> addScheduleByFile(@PathVariable int file, @RequestBody Schedule schedule, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		return doctors.addSchedule(file, schedule, requiring);
	}
	
	
	@GetMapping("/file/{file}/datesAvailable")
	public List<Date> getDatesAvailableForDoctorByFile(@PathVariable int file, @RequestParam(defaultValue = "", name = "from") String from, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		Date startDate;

        if (from.isEmpty()) {
            startDate = new Date();
        } else {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                startDate = formatter.parse(from);
            } catch (ParseException e) {
                startDate = new Date();
            }
        }
		if (startDate.before(new Date())) {
			throw new ValidationException("Selected date is in the past. ", "Intente eligiendo otro día para ver los horarios disponibles. ");
		}
		return doctors.getSchedulesForDoctor(file, startDate, requiring);
	}
	
	@GetMapping("/file/{file}/schedules")
	public List<LocalTime> getSchedulesForDoctorByFile(@PathVariable int file, @RequestParam(defaultValue = "", name = "for") String from, HttpServletRequest req, HttpServletResponse res) {
		User requiring = auth.require(req, res);
		Date date;
		if(from.isEmpty()) {
			date = new Date();
		} else {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				date = formatter.parse(from);
			} catch (ParseException e) {
				date = new Date();
			}
		}
		if (date.before(new Date())) {
			throw new ValidationException("Selected date is in the past. ", "Intente eligiendo otro día para ver los horarios disponibles. ");
		}
		return doctors.getFreeTimeForDoctor(file, date, requiring);
	}
	
	
}
