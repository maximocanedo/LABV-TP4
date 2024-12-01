package web.daoImpl;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.entity.Doctor;
import web.entity.Schedule;

@Component("schedulesrepository")
public class ScheduleDAOImpl {
	
	@Autowired
	private DataManager dataManager;

	public void delete(int id) {
		dataManager.transact(session -> {
			Query q = session.createSQLQuery("DELETE FROM doctor_schedules WHERE schedule = :id");
			q.setParameter("id", id);
			q.executeUpdate();
			Query r = session.createQuery("DELETE FROM Schedule s WHERE s.id = :id");
			r.setParameter("id", id);
			r.executeUpdate();
		});
	}

	public Schedule save(Schedule schedule) {
		dataManager.transact(session -> {
			session.save(schedule);
		});
		return schedule;
	}

	public void link(Schedule schedule, Doctor doctor) {
		dataManager.transact(session -> {
			Query q = session.createSQLQuery("INSERT INTO doctor_schedules (doctor, schedule) VALUES (:doc, :sch)");
			q.setParameter("doc", doctor.getId());
			q.setParameter("sch", schedule.getId());
			q.executeUpdate();
		});
	}
	
}
