package logic;

import java.util.Date;
import entity.Optional;
import java.util.List;

import entity.Appointment;
import exceptions.NotFoundException;

public interface IAppointmentLogic {

	/**
	 * Registra un turno
	 * @param turno Datos del turno.
	 */
	void register(Appointment turno);

	/**
	 * Elimina permanentemente un turno.
	 * @param turno Turno a eliminar.
	 */
	@Deprecated
	void erase(Appointment turno);
	
	/**
	 * Deshabilita un turno.
	 */
	void disable(int id) throws NotFoundException;
	
	/**
	 * Rehabilita un turno.
	 */
	void enable(int id) throws NotFoundException;

	/**
	 * Busca un Turno en la base de datos.
	 * @param id ID del turno.
	 */
	Optional<Appointment> findById(int id);
	
	/**
	 * Busca un Turno en la base de datos.
	 * @param id ID del turno.
	 */
	Optional<Appointment> findById(int id, boolean includeInactives);

	/**
	 * Devuelve una lista de turnos.
	 * @param page Número de página (Comienza en 1)
	 * @param size Tamaño de página
	 */
	List<Appointment> list(int page, int size, boolean includeInactives);
	
	/**
	 * Devuelve una lista de turnos.
	 * @param page Número de página (Comienza en 1)
	 * @param size Tamaño de página
	 */
	List<Appointment> list(int page, int size);

	/**
	 * Devuelve una lista de turnos.
	 */
	List<Appointment> list();

	/**
	 * Actualiza la información de un turno en la base de datos.
	 * @param turno Datos del turno a actualizar.
	 */
	void update(Appointment turno) throws NotFoundException;
	
	/**
	 * Cuenta la cantidad de turnos marcados como presentes dentro del rango de fechas indicado.
	 * @param firstDate Primer fecha límite del rango
	 * @param endDate Segunda fecha límite del rango
	 */
	int countPresencesBetween(Date startDate, Date endDate);
	
	/**
	 * Cuenta la cantidad de turnos marcados como ausentes dentro del rango de fechas indicado.
	 * @param startDate Primer fecha límite del rango
	 * @param endDate Primer fecha límite del rango
	 */
	int countAbsencesBetween(Date startDate, Date endDate);

}