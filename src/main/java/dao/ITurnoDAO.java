package dao;

import java.util.Date;
import java.util.List;
import entity.Optional;

import entity.Turno;

public interface ITurnoDAO {
		
		void add(Turno turno);

		Optional<Turno> getByid(int id);

		List<Turno> list();

		List<Turno> list(int page, int size);

		void update(Turno turno);
		
		void erase(Turno turno);
		
		int countPresencesBetween(Date date1, Date date2);
		
		int countAbsencesBetween(Date date1, Date date2);
}
