package daoImpl;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;

import dao.IMedicoDAO;
import entity.Optional;
import exceptions.NotFoundException;
import entity.Medico;

public class MedicoDAOImpl implements IMedicoDAO {
    
    @Override
    public void add(Medico medico) {
        DataManager.transact(session -> {
            session.save(medico);
        });
    }
    
    @Override
    public Optional<Medico> findById(int id, boolean searchDisabled) {
        final Optional<Medico> cfMedico = new Optional<>(null);
        DataManager.run(session -> {
            String hql = "FROM Medico WHERE id = :id" + (searchDisabled ? "" : " AND active");
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            cfMedico.set((Medico) query.uniqueResult());
        });
        return cfMedico;
    }
    
    @Override
    public List<Medico> list() {
        return list(1, 15);
    }
    
    @Override
    public List<Medico> list(int page, int size) {
        return list(page, size, false);
    }
    

	@SuppressWarnings("unchecked")
	@Override
	public List<Medico> list(int page, int size, boolean includeInactiveRecords) {
		 final Optional<List<Medico>> optionalList = new Optional<>();
	        DataManager.run(session -> {
	            String hql = "FROM Medico" + (includeInactiveRecords ? "" : " WHERE active");
	            Query query = session.createQuery(hql);
	            query.setFirstResult((page - 1) * size);
	            query.setMaxResults(size);
	            optionalList.set(query.list());
	        });
	        return optionalList.get();
	}
    
    @Override
    public void update(Medico medico) throws NotFoundException {
    	Optional<Medico> search = findById(medico.getId());
    	if(search.isEmpty()) throw new NotFoundException();
        DataManager.transact(session -> {
        	Medico original = search.get();
        	if (medico.getNombre() != null) original.setNombre(medico.getNombre());
            if (medico.getApellido() != null) original.setApellido(medico.getApellido());
            if (medico.getSexo() != null) original.setSexo(medico.getSexo());
            if (medico.getFechaNacimiento() != null) original.setFechaNacimiento(medico.getFechaNacimiento());
            if (medico.getDireccion() != null) original.setDireccion(medico.getDireccion());
            if (medico.getLocalidad() != null) original.setLocalidad(medico.getLocalidad());
            if (medico.getCorreo() != null) original.setCorreo(medico.getCorreo());
            if (medico.getTelefono() != null) original.setTelefono(medico.getTelefono());
            if (medico.getEspecialidad() != null) original.setEspecialidad(medico.getEspecialidad());
            if (medico.getUser() != null) original.setUser(medico.getUser());
            session.update(original);
        });
    }
    
    @Override
    @Deprecated
    public void erase(Medico medico) {
        DataManager.transact(session -> {
            session.delete(medico);
        });
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listOnlyFileNumbersAndNames() {
		final Optional<List<Object[]>> optionalList = new Optional<>();
        DataManager.run(session -> {
            String hql = "SELECT m.legajo, m.nombre, m.apellido FROM Medico m ORDER BY m.legajo ASC";
            Query query = session.createQuery(hql);
            optionalList.set(query.list());
        });
        return optionalList.get();
	}

	@Override
	public Medico findDoctorWithHighestFileNumber() {
		List<Medico> list = this.listOrderByFileDescending(1, 1);
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> listOnlyFileNumbers(){
		final Optional<List<Integer>> optionalMedicos = new Optional<>();
        DataManager.run(session -> {
            String hql = "SELECT m.legajo FROM Medico m";
            Query query = session.createQuery(hql);
            optionalMedicos.set(query.list());
        });
        return optionalMedicos.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medico> listOrderByFileDescending(int page, int size) {
		final Optional<List<Medico>> optionalMedicos = new Optional<>();
		DataManager.run(session -> {
			String hql = "SELECT m FROM Medico m ORDER BY legajo DESC";
			Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
			optionalMedicos.set(query.list());
		});
		return optionalMedicos.get();
	}

	@Override
	public List<Medico> listOrderByFileDescending() {
		return listOrderByFileDescending(1, 10);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAppointmentsByDoctorAndDate(int legajo, LocalDate fecha) {
        final Optional<List<Object[]>> optional = new Optional<>();
        DataManager.run(session -> {
            String hql = "SELECT m.legajo, t.fecha, t.estado " +
                         "FROM Turno t INNER JOIN t.medico m " +
                         "WHERE m.legajo = :legajo AND t.fecha = :fecha";
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
	    DataManager.run(session -> {
	        String hql = "SELECT m.legajo, t.fecha, t.estado " +
	                     "FROM Turno t INNER JOIN t.medico m " +
	                     "WHERE m.legajo = :legajo AND t.fecha BETWEEN :fechaInicio AND :fechaFin";
	        Query query = session.createQuery(hql);
	        query.setParameter("legajo", legajo);
	        query.setParameter("fechaInicio", java.sql.Date.valueOf(fechaInicio));
	        query.setParameter("fechaFin", java.sql.Date.valueOf(fechaFin));
	        cfList.set(query.list());
	    });
	    return cfList.get();
	}

	@Override
	public Optional<Medico> findByFile(int file) {
		final Optional<Medico> optional = new Optional<>();
		DataManager.run(session -> {
			String hql = "SELECT m FROM Medico m WHERE m.legajo = :legajo";
			Query query = session.createQuery(hql);
			query.setParameter("legajo", file);
			optional.set((Medico) query.uniqueResult()); 
		});
		return optional;
	}

	@Override
	public Optional<Medico> findById(int id) {
		return findById(id, false);
	}
	
	private void updateStatus(int id, boolean newStatus) throws NotFoundException {
		Optional<Medico> search = findById(id);
    	if(search.isEmpty()) throw new NotFoundException();
        DataManager.transact(session -> {
        	Medico original = search.get();
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
