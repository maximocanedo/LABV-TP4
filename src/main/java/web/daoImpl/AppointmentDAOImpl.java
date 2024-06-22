package web.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IAppointmentDAO;
import web.entity.Appointment;
import web.entity.AppointmentStatus;
import web.entity.Optional;
import web.exceptions.NotFoundException;
@Component("appointmentsrepository")
public class AppointmentDAOImpl implements IAppointmentDAO {
	@Autowired
	private DataManager dataManager;
	
	public AppointmentDAOImpl() {}
	
	@Override
    public void add(Appointment turno) {
		dataManager.transact(session -> {
			session.save(turno);
		});
	}

	@Override
    public Optional<Appointment> findById(int id) {
		return findById(id, false);
	}
	
	@Override
    public Optional<Appointment> findById(int id, boolean includeInactives) {
		final Optional<Appointment> turno = new Optional<>();
		dataManager.run(session -> {
			String hql = "FROM Appointment WHERE id = :id" + (includeInactives ? "" : " AND active");
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			turno.set((Appointment) query.uniqueResult());
		});
		return turno;
	}

	@Override
    public List<Appointment> list() {
		return list(1, 15);
	}
	
	@Override
    public List<Appointment> list(int page, int size) {
		return list(page, size, false);
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<Appointment> list(int page, int size, boolean includeInactives) {
		final Optional<List<Appointment>> optional = new Optional<>();
		dataManager.run(session -> {
			String hql = "FROM Appointment" + (includeInactives ? "" : " WHERE active");
			Query query = session.createQuery(hql);
			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);
			optional.set(query.list());
		});
		return optional.get();
	}

	@Override
    public void update(Appointment turno) {
		dataManager.transact(session -> {
			session.update(turno);
		});
	}

	@Override
	@Deprecated
    public void erase(Appointment turno) {
		dataManager.transact(session -> {
			session.delete(turno);
		});
	}
	
	private void updateStatus(int id, boolean newStatus) throws NotFoundException {
		Optional<Appointment> search = findById(id, newStatus);
    	if(search.isEmpty()) throw new NotFoundException();
    	dataManager.transact(session -> {
        	Appointment original = search.get();
        	original.setActive(newStatus);
            session.update(original);
        });
	}
	
	@Override
	public void disable(int id) throws NotFoundException {
		updateStatus(id, false);
	}
	
	@Override
	public void enable(int id) throws NotFoundException {
		updateStatus(id, true);
	}

	@Override
	public int countPresencesBetween(Date date1, Date date2) {
		final Optional<Integer> r = new Optional<Integer>(0);
		dataManager.run(session -> {
			String queryString = "SELECT COUNT(*) FROM Appointment t WHERE t.status = :e AND t.date BETWEEN :d1 AND :d2";
			Query query = session.createQuery(queryString);
			query.setParameter("e", AppointmentStatus.PRESENT);
			query.setParameter("d1", date1);
			query.setParameter("d2", date2);
			Long l = (Long) query.uniqueResult();
			r.set(l.intValue());
		});
		return r.get();
	}

	@Override
	public int countAbsencesBetween(Date date1, Date date2) {
		final Optional<Integer> r = new Optional<Integer>(0);
		dataManager.run(session -> {
			String queryString = "SELECT COUNT(*) FROM Appointment t WHERE t.status = :e AND t.date BETWEEN :d1 AND :d2";
			Query query = session.createQuery(queryString);
			query.setParameter("e", AppointmentStatus.ABSENT);
			query.setParameter("d1", date1);
			query.setParameter("d2", date2);
			Long l = (Long) query.uniqueResult();
			r.set(l.intValue());
		});
		return r.get();
	}
}
