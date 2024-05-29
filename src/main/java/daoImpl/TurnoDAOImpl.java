package daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import dao.ITurnoDAO;
import entity.Optional;
import entity.Turno;
import entity.TurnoEstado;

public class TurnoDAOImpl implements ITurnoDAO {

	public void add(Turno turno) {
		DataManager.transact(session -> {
			session.save(turno);
		});
	}

	public Optional<Turno> getByid(int id) {
		final Optional<Turno> turno = new Optional<>();
		DataManager.run(session -> {
			String hql = "FROM Turno WHERE id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			turno.set((Turno) query.uniqueResult());
		});
		return turno;
	}

	public List<Turno> list() {
		return list(1, 15);
	}

	@SuppressWarnings("unchecked")
	public List<Turno> list(int page, int size) {
		final Optional<List<Turno>> optional = new Optional<>();
		DataManager.run(session -> {
			String hql = "SELECT * FROM Turno";
			Query query = session.createQuery(hql);
			query.setFirstResult((page - 1) * size);
			query.setMaxResults(size);
			optional.set(query.list());
		});
		return optional.get();
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
		final Optional<Integer> r = new Optional<Integer>(0);
		DataManager.run(session -> {
			String queryString = "SELECT COUNT(*) FROM Turno t WHERE t.estado = :e AND t.fecha BETWEEN :d1 AND :d2";
			Query query = session.createQuery(queryString);
			query.setParameter("e", TurnoEstado.PRESENTE);
			query.setParameter("d1", date1);
			query.setParameter("d2", date2);
			Long l = (Long) query.uniqueResult();
			r.set(l.intValue());
		});
		return r.get();
	}

	@Override
	public int countAbsencesBetween(Date date1, Date date2) {
		final Optional<Integer> r = new Optional<Integer>(0);
		DataManager.run(session -> {
			String queryString = "SELECT COUNT(*) FROM Turno t WHERE t.estado = :e AND t.fecha BETWEEN :d1 AND :d2";
			Query query = session.createQuery(queryString);
			query.setParameter("e", TurnoEstado.AUSENTE);
			query.setParameter("d1", date1);
			query.setParameter("d2", date2);
			Long l = (Long) query.uniqueResult();
			r.set(l.intValue());
		});
		return r.get();
	}
}
