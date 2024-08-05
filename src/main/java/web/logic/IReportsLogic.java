package web.logic;

import java.util.Date;
import java.util.Map;

import web.entity.AppointmentStatus;
import web.entity.User;

public interface IReportsLogic {
	Map<Integer, Integer> countAppointmentsByDayBetweenDates(Date startDate, Date endDate, AppointmentStatus status, User requiring);
}
