package web.dao;

import java.util.Date;
import java.util.List;

import web.entity.Appointment;
import web.entity.Optional;
import web.entity.input.AppointmentQuery;
import web.exceptions.NotFoundException;

public interface IAppointmentDAO {
	
	/**
	 * Registra un turno
	 * @param turno Datos del turno.
	 * @return 
	 */
	Appointment add(Appointment turno);

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
	 * Busca turnos.
	 * @param q Filtros a aplicar.
	 */
	List<Appointment> search(AppointmentQuery q);

	/**
	 * Actualiza la información de un turno en la base de datos.
	 * @param turno Datos del turno a actualizar.
	 */
	Appointment update(Appointment turno);
	
	/**
     * Deshabilita un turno de la base de datos.
     * @param id ID del turno a eliminar.
     * @throws NotFoundException Si el ID ingresado no corresponde a ningún turno.
     */
    void disable(int id) throws NotFoundException;
    
    /**
     * Rehabilita un turno de la base de datos.
     * @param id ID del turno a rehabilitar.
     * @throws NotFoundException Si el ID ingresado no corresponde a ningún turno.
     */
    void enable(int id) throws NotFoundException;
	
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
