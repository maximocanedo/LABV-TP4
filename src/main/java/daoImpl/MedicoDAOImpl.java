package daoImpl;

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
		final Optional<Medico> optionalMedico = new Optional<>(null);
        DataManager.run(session -> {
            String hql = "FROM Medico m ORDER BY m.legajo DESC";
            Query query = session.createQuery(hql);
            optionalMedico.set((Medico) query.uniqueResult());
        });
        return optionalMedico.get();
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
}
