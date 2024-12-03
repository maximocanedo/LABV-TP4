package web.daoImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.type.DateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IReportsDAO;
import web.entity.AppointmentStatus;

@Component("reportsrepository")
public class ReportsDAOImpl implements IReportsDAO {
	@Autowired
	private DataManager dataManager;

	@Override
	public Map<Integer, Integer> countAppointmentsByDayBetweenDates(Date startDate, Date endDate, AppointmentStatus status) {
		final Map<Integer, Integer> result = new HashMap<>();
		for (int i = 1; i <= 7; i++) {
			result.put(i, 0);
		}
		dataManager.run(session -> {
			String hql = "SELECT DAYOFWEEK(a.date) as Dia, COUNT(*) as CantidadTurnos FROM appointments a WHERE (a.date BETWEEN :startDate AND :endDate) AND a.status = :appointmentStatus GROUP BY Dia ORDER BY Dia ASC";
	        Query query = session.createSQLQuery(hql);
	        query.setParameter("startDate", startDate, DateType.INSTANCE);
	        query.setParameter("endDate", endDate, DateType.INSTANCE);
	        query.setParameter("appointmentStatus", status.name());
	        List<Object[]> rows = query.list();
	        for(Object[] row : rows){
	        	result.put(Integer.parseInt(row[0].toString()), Integer.parseInt(row[1].toString()));
	        }
		});
		return result;
	}

	@Override
	public String countCancelledByYear(String year) {
		final String[] result = {"0"}; 
		dataManager.run(session -> {
			String hql = "SELECT COUNT(*) FROM appointments a WHERE a.status = 'CANCELLED' AND YEAR(a.date) = :year";
	        Query query = session.createSQLQuery(hql);
	        query.setParameter("year", year);
	        result[0] = ((BigInteger)query.uniqueResult()).toString();
		});
		return result[0];
	}
	
}
