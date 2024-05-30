package dao;

import java.util.Date;
import java.util.List;
import entity.Optional;

import entity.Turno;

public interface ITurnoDAO {
	
	/**
	 * Registra un turno
	 * @param turno Datos del turno.
	 */
	void add(Turno turno);

	/**
	 * Busca un Turno en la base de datos.
	 * @param id ID del turno.
	 */
	Optional<Turno> findById(int id);

	/**
	 * Devuelve una lista de turnos.
	 */
	List<Turno> list();

	/**
	 * Devuelve una lista de turnos.
	 * @param page Número de página (Comienza en 1)
	 * @param size Tamaño de página
	 */
	List<Turno> list(int page, int size);

	/**
	 * Actualiza la información de un turno en la base de datos.
	 * @param turno Datos del turno a actualizar.
	 */
	void update(Turno turno);
	
	/**
	 * Elimina un turno de la base de datos.
	 * @param turno Turno a eliminar.
	 */
	void erase(Turno turno);
	
	/**
	 * Cuenta la cantidad de turnos marcados como presentes dentro del rango de fechas indicado.
	 * @param firstDate Primer fecha límite del rango
	 * @param endDate Segunda fecha límite del rango
	 */
	int countPresencesBetween(Date firstDate, Date endDate);
	
	/**
	 * Cuenta la cantidad de turnos marcados como ausentes dentro del rango de fechas indicado.
	 * @param startDate Primer fecha límite del rango
	 * @param endDate Primer fecha límite del rango
	 */
	int countAbsencesBetween(Date startDate, Date endDate);
}
