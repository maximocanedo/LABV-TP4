package web.logic;

import java.util.Date;
import java.util.List;

import web.entity.Appointment;
import web.entity.Optional;
import web.entity.User;
import web.entity.input.AppointmentQuery;
import web.exceptions.NotFoundException;

public interface IAppointmentLogic {

	/**
	 * Registra un turno
	 * @param turno Datos del turno.
	 */
	Appointment register(Appointment turno, User requiring);
	
	/**
	 * Deshabilita un turno.
	 */
	void disable(int id, User requiring) throws NotFoundException;
	
	/**
	 * Rehabilita un turno.
	 */
	void enable(int id, User requiring) throws NotFoundException;

	/**
	 * Busca un Turno en la base de datos.
	 * @param id ID del turno.
	 */
	Optional<Appointment> findById(int id, User requiring);
	
	/**
	 * Busca un Turno en la base de datos.
	 * @param id ID del turno.
	 */
	Optional<Appointment> findById(int id, boolean includeInactives, User requiring);

	List<Appointment> search(AppointmentQuery q, User requiring);

	/**
	 * Actualiza la información de un turno en la base de datos.
	 * @param turno Datos del turno a actualizar.
	 */
	Appointment update(Appointment turno, User requiring) throws NotFoundException;
	
	/**
	 * Cuenta la cantidad de turnos marcados como presentes dentro del rango de fechas indicado.
	 * @param firstDate Primer fecha límite del rango
	 * @param endDate Segunda fecha límite del rango
	 */
	int countPresencesBetween(Date startDate, Date endDate, User requiring);
	
	/**
	 * Cuenta la cantidad de turnos marcados como ausentes dentro del rango de fechas indicado.
	 * @param startDate Primer fecha límite del rango
	 * @param endDate Primer fecha límite del rango
	 */
	int countAbsencesBetween(Date startDate, Date endDate, User requiring);
	
}