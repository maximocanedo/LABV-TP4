package dao;

import java.util.List;
import entity.Turno;

public interface ITurnoDAO {
		
		void add(Turno turno);

		Turno getByid(int id);

		 List<Turno> list();

		List<Turno> list(int page, int size);

		void update(Turno turno);
		
		void erase(Turno turno) ;
}
