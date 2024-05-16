package daoImpl;

import java.util.List;

import org.hibernate.Query;

import dao.IEspecialidadDAO;
import daoImpl.DataManager.ContainerFor;
import entity.Especialidad;

public class EspecialidadDAOImpl implements IEspecialidadDAO {

    @Override
    public void add(Especialidad especialidad) {
        DataManager.transact(session -> {
            session.save(especialidad);
        });
    }

    @Override
    public Especialidad getById(int id) {
        final ContainerFor<Especialidad> cfEspecialidad = new ContainerFor<>(null);
        DataManager.run(session -> {
            String hql = "FROM Especialidad WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            cfEspecialidad.object = (Especialidad) query.uniqueResult();
        });
        return cfEspecialidad.object;
    }

    @Override
    public List<Especialidad> list() {
        return list(1, 15);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Especialidad> list(int page, int size) {
        final ContainerFor<List<Especialidad>> cfList = new ContainerFor<>(null);
        DataManager.run(session -> {
            String hql = "FROM Especialidad";
            Query query = session.createQuery(hql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            cfList.object = query.list();
        });
        return cfList.object;
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
