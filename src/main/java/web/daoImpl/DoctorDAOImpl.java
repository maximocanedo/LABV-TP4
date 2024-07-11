package web.daoImpl;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IDoctorDAO;
import web.entity.Doctor;
import web.entity.Optional;
import web.entity.input.DoctorQuery;
import web.entity.view.DoctorMinimalView;
import web.exceptions.NotFoundException;

@Component("doctorsrepository")
public class DoctorDAOImpl implements IDoctorDAO {
    
	@Autowired
	private DataManager dataManager;
	
	public DoctorDAOImpl() {}	
	
    @Override
    public Doctor add(Doctor medico) {
    	dataManager.transact(session -> {
            session.save(medico);
        });
    	return medico;
    }
    
    @Override
    public Optional<Doctor> findById(int id, boolean searchDisabled) {
        final Optional<Doctor> cfMedico = new Optional<>(null);
        dataManager.run(session -> {
            String hql = "FROM Doctor WHERE id = :id" + (searchDisabled ? "" : " AND active = 1");
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            cfMedico.set((Doctor) query.uniqueResult());
        });
        return cfMedico;
    }

    public Optional<DoctorMinimalView> findMinById(int id, boolean searchDisabled) {
        final Optional<DoctorMinimalView> cfMedico = new Optional<>(null);
        dataManager.run(session -> {
            String hql = "FROM DoctorMinimalView WHERE id = :id" + (searchDisabled ? "" : " AND active = 1");
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            cfMedico.set((DoctorMinimalView) query.uniqueResult());
        });
        return cfMedico;
    }
    
    @Override
    public Doctor update(Doctor medico) {
    	dataManager.transact(session -> {
            session.update(medico);
        });
    	return medico;
    }

    public boolean existsByFile(int file) {
		final Optional<Boolean> cfUser = new Optional<Boolean>();
		dataManager.run(session -> {
			String hql = "SELECT COUNT(p) FROM DoctorMinimalView p WHERE p.file = :file";
	        Query query = session.createQuery(hql);
	        query.setParameter("file", file);
	        Long d = (Long) query.uniqueResult();
	        cfUser.set(d > 0);
		});
		return cfUser.get().booleanValue();
	}
    
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listOnlyFileNumbersAndNames() {
		final Optional<List<Object[]>> optionalList = new Optional<>();
		dataManager.run(session -> {
            String hql = "SELECT m.file, m.name, m.surname FROM Doctor m ORDER BY m.file ASC";
            Query query = session.createQuery(hql);
            optionalList.set(query.list());
        });
        return optionalList.get();
	}

	@Override
	public Doctor findDoctorWithHighestFileNumber() {
		List<Doctor> list = this.listOrderByFileDescending(1, 1);
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> listOnlyFileNumbers(){
		final Optional<List<Integer>> optionalMedicos = new Optional<>();
		dataManager.run(session -> {
            String hql = "SELECT m.file FROM Doctor m";
            Query query = session.createQuery(hql);
            optionalMedicos.set(query.list());
        });
        return optionalMedicos.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Doctor> listOrderByFileDescending(int page, int size) {
		final Optional<List<Doctor>> optionalMedicos = new Optional<>();
		dataManager.run(session -> {
			String hql = "SELECT m FROM Doctor m ORDER BY m.file DESC";
			Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
			optionalMedicos.set(query.list());
		});
		return optionalMedicos.get();
	}

	@Override
	public List<Doctor> listOrderByFileDescending() {
		return listOrderByFileDescending(1, 10);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAppointmentsByDoctorAndDate(int legajo, LocalDate fecha) {
        final Optional<List<Object[]>> optional = new Optional<>();
        dataManager.run(session -> {
            String hql = "SELECT m.file, t.date, t.status " +
                         "FROM Appointment t INNER JOIN t.assignedDoctor m " +
                         "WHERE m.file = :legajo AND t.fecha = :fecha";
            Query query = session.createQuery(hql);
            query.setParameter("legajo", legajo);
            query.setParameter("fecha", java.sql.Date.valueOf(fecha));
            optional.set(query.list());
        });
        return optional.get();
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAppointmentsByDoctorAndDateRange(int legajo, LocalDate fechaInicio, LocalDate fechaFin) {
	    final Optional<List<Object[]>> cfList = new Optional<>();
	    dataManager.run(session -> {
	        String hql = "SELECT m.file, t.date, t.status " +
	                     "FROM Appointment t INNER JOIN t.assignedDoctor m " +
	                     "WHERE m.file = :legajo AND t.date BETWEEN :fechaInicio AND :fechaFin";
	        Query query = session.createQuery(hql);
	        query.setParameter("legajo", legajo);
	        query.setParameter("fechaInicio", java.sql.Date.valueOf(fechaInicio));
	        query.setParameter("fechaFin", java.sql.Date.valueOf(fechaFin));
	        cfList.set(query.list());
	    });
	    return cfList.get();
	}

	@Override
	public Optional<Doctor> findByFile(int file) {
		final Optional<Doctor> optional = new Optional<>();
		dataManager.run(session -> {
			String hql = "SELECT m FROM Doctor m WHERE m.file = :legajo";
			Query query = session.createQuery(hql);
			query.setParameter("legajo", file);
			optional.set((Doctor) query.uniqueResult()); 
		});
		return optional;
	}
	
	public Optional<DoctorMinimalView> findMinByFile(int file) {
		final Optional<DoctorMinimalView> optional = new Optional<>();
		dataManager.run(session -> {
			String hql = "SELECT m FROM DoctorMinimalView m WHERE m.file = :legajo";
			Query query = session.createQuery(hql);
			query.setParameter("legajo", file);
			optional.set((DoctorMinimalView) query.uniqueResult()); 
		});
		return optional;
	}


	@Override
	public Optional<Doctor> findByFile(int file, boolean includeInactives) {
		final Optional<Doctor> optional = new Optional<>();
		dataManager.run(session -> {
			String hql = "SELECT m FROM Doctor m WHERE m.file = :legajo"  + (includeInactives ? "" : " AND active = 1");
			Query query = session.createQuery(hql);
			query.setParameter("legajo", file);
			optional.set((Doctor) query.uniqueResult()); 
		});
		return optional;
	}
	
	public Optional<DoctorMinimalView> findMinByFile(int file, boolean includeInactives) {
		final Optional<DoctorMinimalView> optional = new Optional<>();
		dataManager.run(session -> {
			String hql = "SELECT m FROM DoctorMinimalView m WHERE m.file = :legajo"  + (includeInactives ? "" : " AND active = 1");
			Query query = session.createQuery(hql);
			query.setParameter("legajo", file);
			optional.set((DoctorMinimalView) query.uniqueResult()); 
		});
		return optional;
	}

	@Override
	public Optional<Doctor> findById(int id) {
		return findById(id, false);
	}
	
	private void updateStatus(int id, boolean newStatus) throws NotFoundException {
		Optional<Doctor> search = findById(id, newStatus);
    	if(search.isEmpty()) throw new NotFoundException();
    	dataManager.transact(session -> {
        	Doctor original = search.get();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<DoctorMinimalView> search(DoctorQuery query) {
		final Optional<List<DoctorMinimalView>> doctors = new Optional<List<DoctorMinimalView>>();
		dataManager.run(session -> {
			Query q = query.toQuery(session);
			doctors.set(q.list());
		});
		return doctors.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LocalTime> getFreeTimeForDoctor(int file, Date date) {
		final List<LocalTime> times = new ArrayList<LocalTime>();
		dataManager.run(session -> {
			Query q = session.createSQLQuery("CALL getFreeTimesForDoctor(:file, :date)");
			q.setParameter("file", file);
			q.setParameter("date", date);
			List<Object> result = q.list();
			for(Object x : result) {
				times.add(((Time) x).toLocalTime());
			}
		});
		return times;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Date> getScheduleForDoctor(int file, Date startDate) {
		final List<Date> schedules = new ArrayList<Date>();
		dataManager.run(session -> {
			Query q = session.createSQLQuery("CALL getSchedulesForDoctor(:file, :startDate)");
			q.setParameter("file", file);
			q.setDate("startDate", startDate);
			List<Object> result = q.list();
			for(Object x : result)
				schedules.add(((Date) x));
		});
		return schedules;
	}
	
	@Override
	public List<Date> getScheduleForDoctor(int file, Calendar startDate) {
		Date start = startDate.getTime();
		return getScheduleForDoctor(file, start);
	}
}
