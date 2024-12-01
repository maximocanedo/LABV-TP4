package web.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import web.entity.AppointmentStatus;

public interface IReportsDAO {
	Map<Integer, Integer> countAppointmentsByDayBetweenDates(Date startDate, Date endDate, AppointmentStatus status);
}
