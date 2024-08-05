package web.logicImpl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IReportsDAO;
import web.entity.AppointmentStatus;
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
	public Map<Integer, Integer> countAppointmentsByDayBetweenDates(Date startDate, Date endDate, AppointmentStatus status) {
		/*try {
			requiring = permits.inquire(requiring, Permit.READ_APPOINTMENT);
		} catch(NotAllowedException e) {
			throw e;
		}*/
		return reportsrepository.countAppointmentsByDayBetweenDates(startDate, endDate, status);
	}

}
