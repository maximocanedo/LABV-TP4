package web.controller;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.entity.AppointmentStatus;
import web.entity.User;
import web.logicImpl.ReportsLogicImpl;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = { "Authorization", "Content-Length" })
public class ReportsController {
	@Autowired
	private ReportsLogicImpl reports;
	
	@Autowired 
	private AuthUtils auth;
	
	@InitBinder
    public void initBinder(HttpServletRequest req, HttpServletResponse res) {
        auth.preHandle(req, res);
    }
	
	@PostMapping("/countAppointmentsByDayBetweenDates")
	public ResponseEntity<Map<Integer, Integer>> countAppointmentsByDayBetweenDates(
			@RequestParam(required = true) String startDate,
			@RequestParam(required = true) String endDate,
			@RequestParam(required = true) String status,
			HttpServletRequest req, HttpServletResponse res) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		User requiring = auth.require(req, res);
		return new ResponseEntity<>(reports.countAppointmentsByDayBetweenDates(formatter.parse(startDate), formatter.parse(endDate), AppointmentStatus.valueOf(status), requiring), HttpStatus.OK);
	}

}
