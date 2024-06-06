package daoImpl;

import java.util.List;


import org.hibernate.*;

import dao.IPatientDAO;
import entity.Optional;
import entity.Patient;
import exceptions.NotFoundException;

public class PatientDAOImpl implements IPatientDAO {
	
	public PatientDAOImpl() {}
		
	@Override
	public void add(Patient paciente) {
		DataManager.transact(session -> {
			session.save(paciente);
		});
    }
	
	@Override
	public Optional<Patient> findById(int id) {
		return findById(id, false);
	}
	
	@Override
	public Optional<Patient> findById(int id, boolean includeInactives) {
		final Optional<Patient> cfUser = new Optional<Patient>();
		DataManager.run(session -> {
			String hql = "FROM Patient WHERE id = :id" + (includeInactives ? "" : " AND active");
	        Query query = session.createQuery(hql);
	        query.setParameter("id", id);
	        cfUser.set((Patient) query.uniqueResult());
		});
		return cfUser;
	}
		
	@Override
	public List<Patient> list() {
		return list(1, 15);
    }
	
	@Override
	public List<Patient> list(int page, int size) {
		return list(page, size, false);
    }
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Patient> list(int page, int size, boolean includeInactives) {
		final Optional<List<Patient>> cfList = new Optional<List<Patient>>();
		DataManager.run(session -> {
			String hql = "FROM Patient" + (includeInactives ? "" : " WHERE active");
			Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            cfList.set(((List<Patient>) query.list()));
		});
		return cfList.get();
    }
	
	@Override
	public void update(Patient paciente) {
		DataManager.transact(session -> {
			session.update(paciente);
		});
	}
	
	@Override
	@Deprecated
	public void erase(Patient paciente) {
		DataManager.transact(session -> {
			session.delete(paciente);
		});
	}
	
	private void updateStatus(int id, boolean newStatus) throws NotFoundException {
		Optional<Patient> search = findById(id, newStatus);
    	if(search.isEmpty()) throw new NotFoundException();
        DataManager.transact(session -> {
        	Patient original = search.get();
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
	
}
