package web.daoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.dao.IPatientDAO;
import web.entity.Optional;
import web.entity.Patient;
import web.entity.input.PatientQuery;
import web.entity.view.PatientCommunicationView;
import web.exceptions.NotFoundException;

@Component("patientsrepository")
public class PatientDAOImpl implements IPatientDAO {
	
	@Autowired
	private DataManager dataManager;
	
	public PatientDAOImpl() {}
	
	@Override
	public Patient add(Patient paciente) {
		dataManager.transact(session -> {
			session.save(paciente);
		});
		return paciente;
    }
	
	@Override
	public Optional<Patient> findById(int id) {
		return findById(id, false);
	}
	
	@Override
	public Optional<Patient> findById(int id, boolean includeInactives) {
		final Optional<Patient> cfUser = new Optional<Patient>();
		dataManager.run(session -> {
			String hql = "FROM Patient WHERE id = :id" + (includeInactives ? "" : " AND active = 1");
	        Query query = session.createQuery(hql);
	        query.setParameter("id", id);
	        cfUser.set((Patient) query.uniqueResult());
		});
		return cfUser;
	}
	

	@Override
	public Optional<PatientCommunicationView> findComViewById(int id, boolean includeInactives) {
		final Optional<PatientCommunicationView> cfUser = new Optional<PatientCommunicationView>();
		dataManager.run(session -> {
			String hql = "FROM PatientCommunicationView WHERE id = :id" + (includeInactives ? "" : " AND active = 1");
	        Query query = session.createQuery(hql);
	        query.setParameter("id", id);
	        cfUser.set((PatientCommunicationView) query.uniqueResult());
		});
		return cfUser;
	}

	@Override
	public boolean existsByDNI(String dni) {
		final Optional<Boolean> cfUser = new Optional<Boolean>();
		dataManager.run(session -> {
			String hql = "SELECT COUNT(p) FROM Patient p WHERE p.dni = :dni";
	        Query query = session.createQuery(hql);
	        query.setParameter("dni", dni);
	        Long d = (Long) query.uniqueResult();
	        cfUser.set(d > 0);
		});
		return cfUser.get().booleanValue();
	}
	
	@Override
	public Patient update(Patient paciente) {
		dataManager.transact(session -> {
			session.update(paciente);
		});
		return paciente;
	}
	
	private void updateStatus(int id, boolean newStatus) throws NotFoundException {
		Optional<Patient> search = findById(id, newStatus);
    	if(search.isEmpty()) throw new NotFoundException();
    	dataManager.transact(session -> {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientCommunicationView> search(PatientQuery q) {
		final Optional<List<PatientCommunicationView>> opt = new Optional<List<PatientCommunicationView>>();
		dataManager.run(session -> {
			Query query = q.toQuery(session);
			opt.set(query.list());
		});
		return opt.get();
	}
	
}
