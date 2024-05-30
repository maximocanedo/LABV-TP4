package daoImpl;

import java.util.List;

import org.hibernate.Query;

import dao.IEspecialidadDAO;
import entity.Optional;
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
    public void update(Especialidad record) {
        DataManager.transact(session -> {
            session.update(record);
        });
    }

    @Override
    public void erase(Especialidad record) {
        DataManager.transact(session -> {
            session.delete(record);
        });
    }
}
