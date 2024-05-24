package logic;

import java.util.Date;
import java.util.List;

import entity.Turno;

public interface ITurnoLogic {

	void register(Turno t);

	void disable(Turno t);

	Turno getById(int id);

	List<Turno> list(int page, int size);

	List<Turno> list();

	void update(Turno turno);
	
	int countPresencesBetween(Date d1, Date d2);
	
	int countAbsencesBetween(Date d1, Date d2);

}