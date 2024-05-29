package daoImpl;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;

import dao.IMedicoDAO;
import entity.Optional;
import entity.Medico;

public class MedicoDAOImpl implements IMedicoDAO {
    
    @Override
    public void add(Medico medico) {
        DataManager.transact(session -> {
            session.save(medico);
        });
    }
    
    @Override
    public Optional<Medico> getById(int id) {
        final Optional<Medico> cfMedico = new Optional<>(null);
        DataManager.run(session -> {
            String hql = "FROM Medico WHERE id = :id";
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
    @SuppressWarnings("unchecked")
    public List<Medico> list(int page, int size) {
        final Optional<List<Medico>> optionalList = new Optional<>();
        DataManager.run(session -> {
            String hql = "FROM Medico";
            Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            optionalList.set(query.list());
        });
        return optionalList.get();
    }
    
    @Override
    public void update(Medico medico) {
        DataManager.transact(session -> {
            session.update(medico);
        });
    }
    
    @Override
    public void erase(Medico medico) {
        DataManager.transact(session -> {
            session.delete(medico);
        });
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listMedicosLegajoAscP2() {
		final Optional<List<Object[]>> optionalList = new Optional<>();
        DataManager.run(session -> {
            String hql = "SELECT m.legajo, m.nombre, m.apellido FROM Medico m ORDER BY m.legajo ASC";
            Query query = session.createQuery(hql);
            optionalList.set(query.list());
        });
        return optionalList.get();
	}

	@Override
	public Medico medicoMayorLegajoP5() {
		List<Medico> list = this.listOrderByFileDescending(1, 1);
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> TodosMedicosXLegajoP4(){
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
	public List<Object[]> getTurnosMedicoEnFecha(int legajo, LocalDate fecha) {
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
	public List<Object[]> getTurnosMedicoEnRangoDeFechas(int legajo, LocalDate fechaInicio, LocalDate fechaFin) {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTurnosMedicoEnFecha(int legajo, LocalDate fecha) {
        final ContainerFor<List<Object[]>> cfList = new ContainerFor<>(null);
        DataManager.run(session -> {
            String hql = "SELECT m.legajo, t.fechaAlta, t.estado " +
                         "FROM Medico m INNER JOIN m.turnos t " +
                         "WHERE m.legajo = :legajo AND t.fechaAlta = :fecha";
            Query query = session.createQuery(hql);
            query.setParameter("legajo", legajo);
            query.setParameter("fecha", java.sql.Date.valueOf(fecha));
            cfList.object = query.list();
        });
        return cfList.object;
    }
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Object[]> getTurnosMedicoEnRangoDeFechas(int legajo, LocalDate fechaInicio, LocalDate fechaFin) {
        final ContainerFor<List<Object[]>> cfList = new ContainerFor<>(null);
        DataManager.run(session -> {
            String hql = "SELECT m.legajo, t.fechaAlta, t.estado " +
                         "FROM Medico m INNER JOIN m.turnos t " +
                         "WHERE m.legajo = :legajo AND t.fechaAlta BETWEEN :fechaInicio AND :fechaFin";
            Query query = session.createQuery(hql);
            query.setParameter("legajo", legajo);
            query.setParameter("fechaInicio", java.sql.Date.valueOf(fechaInicio));
            query.setParameter("fechaFin", java.sql.Date.valueOf(fechaFin));
            cfList.object = query.list();
        });
        return cfList.object;
    }
}
