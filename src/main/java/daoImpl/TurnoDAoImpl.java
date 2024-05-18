package daoImpl;

import java.util.List;

import org.hibernate.Query;

import dao.ITurnoDAO;
import daoImpl.DataManager.ContainerFor;
import entity.Turno;

public class TurnoDAoImpl implements ITurnoDAO{
	
public void add(Turno turno) {
	DataManager.transact(session -> {
        session.save(turno);
    });
}

public Turno getByid(int id) {
	final  ContainerFor<Turno> turno =new ContainerFor<>(null);
	 DataManager.run(session -> {
		String hql = "FROM Turno WHERE id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        turno.object = (Turno) query.uniqueResult();
	});
	return turno.object;
}

public List<Turno> list() {
	return list(1, 15);
}

@SuppressWarnings("unchecked")
public List<Turno> list(int page, int size) {
    final ContainerFor<List<Turno>> turno = new ContainerFor<>(null);
    DataManager.run(session -> {
        String hql = "SELECT * FROM Turno";
        Query query = session.createQuery(hql);
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        turno.object = query.list();
    });
    return turno.object;
}

public void update(Turno turno) {
    DataManager.transact(session -> {
        session.update(turno);
    });
}

public void erase(Turno turno) {
    DataManager.transact(session -> {
        session.delete(turno);
    });
}
}
