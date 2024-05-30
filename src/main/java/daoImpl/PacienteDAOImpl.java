package daoImpl;

import java.util.List;


import org.hibernate.*;

import dao.IPacienteDAO;
import entity.Optional;
import entity.Paciente;

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
		final Optional<Paciente> cfUser = new Optional<Paciente>();
		DataManager.run(session -> {
			String hql = "FROM Paciente WHERE id = :id";
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
	@SuppressWarnings("unchecked")
	public List<Paciente> list(int page, int size) {
		final Optional<List<Paciente>> cfList = new Optional<List<Paciente>>();
		DataManager.run(session -> {
			String sqlQuery = "SELECT id, nombre, apellido, dni, telefono, direccion, localidad, provincia, fechaNacimiento, correo FROM pacientes";
	        Query q = session.createSQLQuery(sqlQuery).addEntity(Paciente.class);
			q.setFirstResult((page - 1) * size);
            q.setMaxResults(size);
			cfList.set(q.list());
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
	public void erase(Paciente paciente) {
		DataManager.transact(session -> {
			session.delete(paciente);
		});
	}
	
}
