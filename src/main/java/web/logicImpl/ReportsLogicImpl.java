package web.logicImpl;

import java.util.*;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IReportsDAO;
import web.entity.AppointmentStatus;
import web.entity.Day;
import web.entity.Permit;
import web.entity.User;
import web.exceptions.NotAllowedException;
import web.logic.IReportsLogic;

@Component("reports")
public class ReportsLogicImpl implements IReportsLogic {
	@Autowired
	private IReportsDAO reportsrepository;
	
	@Autowired
	private UserPermitLogicImpl permits;

	@Override
	public Map<String, Integer> countAppointmentsByDayBetweenDates(Date startDate, Date endDate, AppointmentStatus status, User requiring) {
		try {
			requiring = permits.inquire(requiring, Permit.READ_APPOINTMENT);
		} catch(NotAllowedException e) {
			throw e;
		}
		Map<Integer, Integer> queryResult = reportsrepository.countAppointmentsByDayBetweenDates(startDate, endDate, status);
		Map<String, Integer> result = new LinkedHashMap<>();
		queryResult.forEach((k, v) -> {
			result.put(getWeekDaySpanishName(k), v);
		});
		return result;
	}

	@Override
	public String countCancelledByYear(String year, User requiring) {
		try {
			requiring = permits.inquire(requiring, Permit.READ_APPOINTMENT);
		} catch(NotAllowedException e) {
			throw e;
		}
		return reportsrepository.countCancelledByYear(year);
	}

	@Override
	public Map<String, Integer> countAppointmentsBySpecialtyMonthByMonth(String specialty, User requiring) {
		try {
			requiring = permits.inquire(requiring, Permit.READ_APPOINTMENT);
		} catch(NotAllowedException e) {
			throw e;
		}
		Map<Integer, Integer> queryResult = reportsrepository.countAppointmentsBySpecialtyMonthByMonth(specialty);
		Map<String, Integer> result = new LinkedHashMap<>();
		queryResult.forEach((k, v) -> {
			result.put(getMonthSpanishName(k), v);
		});
		return result;
	}
	
	public String getWeekDaySpanishName(int day) {
	    String[] days = {
	        "Domingo", "Lunes", "Martes", "Miércoles", 
	        "Jueves", "Viernes", "Sábado"
	    };

	    if (day >= 1 && day <= 7) {
	        return days[day - 1];
	    } else {
	        return "?";
	    }
	}

	
	public String getMonthSpanishName(int month) {
	    String[] months = {
	        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
	        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
	    };
	    
	    if (month >= 1 && month <= 12) {
	        return months[month - 1];
	    } else {
	        return "?";
	    }
	}
	
}
