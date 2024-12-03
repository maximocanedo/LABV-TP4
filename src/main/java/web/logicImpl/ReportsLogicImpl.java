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

	public String getWeekDaySpanishName(int day) {
		switch (day) {
			case 1:
				return "Domingo";
			case 2:
				return "Lunes";
			case 3:
				return "Martes";
			case 4:
				return "Miércoles";
			case 5:
				return "Jueves";
			case 6:
				return "Viernes";
			case 7:
				return "Sábado";
			default:
				return "?";
		}
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
}
