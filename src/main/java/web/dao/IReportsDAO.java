package web.dao;

import java.util.Date;
import java.util.Map;

import web.entity.AppointmentStatus;

public interface IReportsDAO {
	Map<Integer, Integer> countAppointmentsByDayBetweenDates(Date startDate, Date endDate, AppointmentStatus status);
	String countCancelledByYear(String year);
}
