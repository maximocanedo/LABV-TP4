package web.logic;

import java.util.Date;
import java.util.Map;

import web.entity.AppointmentStatus;
import web.entity.User;

public interface IReportsLogic {
	Map<String, Integer> countAppointmentsByDayBetweenDates(Date startDate, Date endDate, AppointmentStatus status, User requiring);
	String countCancelledByYear(String year, User requiring);
	Map<String, Integer> countAppointmentsBySpecialtyMonthByMonth(String specialty, User requiring);
}
