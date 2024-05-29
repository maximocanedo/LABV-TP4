package daoImpl;

import java.util.List;

import org.hibernate.Query;

import dao.IEspecialidadDAO;
import entity.Optional;
import entity.Especialidad;

public class EspecialidadDAOImpl implements IEspecialidadDAO {

    @Override
    public void add(Especialidad especialidad) {
        DataManager.transact(session -> {
            session.save(especialidad);
        });
    }

    @Override
    public Optional<Especialidad> getById(int id) {
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
    @SuppressWarnings("unchecked")
    public List<Especialidad> list(int page, int size) {
        final Optional<List<Especialidad>> optional = new Optional<>();
        DataManager.run(session -> {
            String hql = "FROM Especialidad";
            Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            optional.set(query.list());
        });
        return optional.get();
    }

    @Override
    public void update(Especialidad especialidad) {
        DataManager.transact(session -> {
            session.update(especialidad);
        });
    }

    @Override
    public void erase(Especialidad especialidad) {
        DataManager.transact(session -> {
            session.delete(especialidad);
        });
    }
}
