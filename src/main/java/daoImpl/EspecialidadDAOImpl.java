package daoImpl;

import java.util.List;

import org.hibernate.Query;

import dao.IEspecialidadDAO;
import entity.Optional;
import exceptions.NotFoundException;
import entity.Especialidad;

public class EspecialidadDAOImpl implements IEspecialidadDAO {

    @Override
    public void add(Especialidad record) {
        DataManager.transact(session -> {
            session.save(record);
        });
    }

    @Override
    public Optional<Especialidad> findById(int id) {
        final Optional<Especialidad> optional = new Optional<>();
        DataManager.run(session -> {
            String hql = "FROM Especialidad WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            optional.set((Especialidad) query.uniqueResult());
        });
        return optional;
    }

    @Override
    public List<Especialidad> list() {
        return list(1, 15);
    }

    @Override
    public List<Especialidad> list(int page, int size) {
        return this.list(page, size, false);
    }

    @Override
    public void update(Especialidad record) throws NotFoundException {
    	Optional<Especialidad> file = findById(record.getId());
    	if(file.isEmpty()) throw new NotFoundException();
        DataManager.transact(session -> {
            Especialidad original = file.get();
            if(record.getNombre() != null)
            	original.setNombre(record.getNombre());
            if(record.getDescripcion() != null)
            	original.setDescripcion(record.getDescripcion());
            session.update(original);
        });
    }

    @Override
    public void disable(int id) throws NotFoundException {
    	Optional<Especialidad> file = findById(id);
    	if(file.isEmpty()) throw new NotFoundException();
        DataManager.transact(session -> {
            Especialidad original = file.get();
            original.setActiveStatus(false);
            session.update(original);
        });
    }
    
    @Override
    public void enable(int id) throws NotFoundException {
    	Optional<Especialidad> file = findById(id);
    	if(file.isEmpty()) throw new NotFoundException();
        DataManager.transact(session -> {
            Especialidad original = file.get();
            original.setActiveStatus(true);
            session.update(original);
        });
    }

	@Override
	public List<Especialidad> list(boolean showInactiveRecords) {
		return this.list(1, 10, showInactiveRecords);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Especialidad> list(int page, int size, boolean showInactiveRecords) {
		final Optional<List<Especialidad>> optional = new Optional<>();
        DataManager.run(session -> {
            String hql = "SELECT e FROM Especialidad e" + (showInactiveRecords ? "" : " WHERE e.active");
            Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            optional.set(((List<Especialidad>) query.list()));
        });
        return optional.get();
	}
}
