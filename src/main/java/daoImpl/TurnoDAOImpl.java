package daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import dao.ITurnoDAO;
import daoImpl.DataManager.ContainerFor;
import entity.Turno;
import entity.TurnoEstado;

public class TurnoDAOImpl implements ITurnoDAO {

	public void add(Turno turno) {
		DataManager.transact(session -> {
			session.save(turno);
		});
	}

	public Turno getByid(int id) {
		final ContainerFor<Turno> turno = new ContainerFor<>(null);
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

	@Override
	public int countPresencesBetween(Date date1, Date date2) {
		final ContainerFor<Integer> r = new ContainerFor<Integer>(0);
		DataManager.run(session -> {
			String queryString = "SELECT COUNT(*) FROM Turno t WHERE t.estado = :e AND t.fecha BETWEEN :d1 AND :d2";
			Query query = session.createQuery(queryString);
			query.setParameter("e", TurnoEstado.PRESENTE);
			query.setParameter("d1", date1);
			query.setParameter("d2", date2);
			Long l = (Long) query.uniqueResult();
			r.object = l.intValue();
		});
		return r.object;
	}

	@Override
	public int countAbsencesBetween(Date date1, Date date2) {
		final ContainerFor<Integer> r = new ContainerFor<Integer>(0);
		DataManager.run(session -> {
			String queryString = "SELECT COUNT(*) FROM Turno t WHERE t.estado = :e AND t.fecha BETWEEN :d1 AND :d2";
			Query query = session.createQuery(queryString);
			query.setParameter("e", TurnoEstado.AUSENTE);
			query.setParameter("d1", date1);
			query.setParameter("d2", date2);
			Long l = (Long) query.uniqueResult();
			r.object = l.intValue();
		});
		return r.object;
	}
}
