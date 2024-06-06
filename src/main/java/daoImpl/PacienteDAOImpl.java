package daoImpl;

import java.util.List;


import org.hibernate.*;

import dao.IPacienteDAO;
import entity.Optional;
import entity.Paciente;
import exceptions.NotFoundException;

public class PacienteDAOImpl implements IPacienteDAO {
	
	public PacienteDAOImpl() {}
		
	@Override
	public void add(Paciente paciente) {
		DataManager.transact(session -> {
			session.save(paciente);
		});
    }
	
	@Override
	public Optional<Paciente> findById(int id) {
		return findById(id, false);
	}
	
	@Override
	public Optional<Paciente> findById(int id, boolean includeInactives) {
		final Optional<Paciente> cfUser = new Optional<Paciente>();
		DataManager.run(session -> {
			String hql = "FROM Paciente WHERE id = :id" + (includeInactives ? "" : " AND active");
	        Query query = session.createQuery(hql);
	        query.setParameter("id", id);
	        cfUser.set((Paciente) query.uniqueResult());
		});
		return cfUser;
	}
		
	@Override
	public List<Paciente> list() {
		return list(1, 15);
    }
	
	@Override
	public List<Paciente> list(int page, int size) {
		return list(page, size, false);
    }
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Paciente> list(int page, int size, boolean includeInactives) {
		final Optional<List<Paciente>> cfList = new Optional<List<Paciente>>();
		DataManager.run(session -> {
			String hql = "FROM pacientes" + (includeInactives ? "" : " AND active");
			Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            cfList.set(((List<Paciente>) query.list()));
		});
		return cfList.get();
    }
	
	@Override
	public void update(Paciente paciente) {
		DataManager.transact(session -> {
			session.update(paciente);
		});
	}
	
	@Override
	@Deprecated
	public void erase(Paciente paciente) {
		DataManager.transact(session -> {
			session.delete(paciente);
		});
	}
	
	private void updateStatus(int id, boolean newStatus) throws NotFoundException {
		Optional<Paciente> search = findById(id, newStatus);
    	if(search.isEmpty()) throw new NotFoundException();
        DataManager.transact(session -> {
        	Paciente original = search.get();
        	original.setActiveStatus(newStatus);
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
