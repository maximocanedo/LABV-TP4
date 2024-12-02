package web.dao;

import java.util.Date;
import java.util.List;

import web.entity.Appointment;
import web.entity.Optional;
import web.entity.input.AppointmentQuery;
import web.entity.view.AppointmentCommunicationView;
import web.entity.view.AppointmentMinimalView;
import web.exceptions.NotFoundException;

public interface IAppointmentDAO {
	
	/**
	 * Registra un turno en la base de datos.
	 * @param turno Datos del turno a registrar.
	 * @return Turno registrado con el ID generado.
	 */
	Appointment add(Appointment turno);

	/**
	 * Busca un turno en la base de datos por su ID.
	 * @param id ID del turno.
	 */
	Optional<Appointment> findById(int id);
	
	/**
	 * Busca un turno en la base de datos por su ID.
	 * @param id ID del turno.
	 */
	Optional<AppointmentMinimalView> findMinById(int id);

	/**
	 * Busca un turno en la base de datos por su ID.
	 * @param id ID del turno.
	 */
	Optional<AppointmentCommunicationView> findComById(int id);
	
	/**
	 * Busca un turno en la base de datos por su ID.
	 * @param id ID del turno.
	 * @param includeInactives ¿Se deben incluir registros deshabilitados? Por defecto, false.
	 */
	Optional<Appointment> findById(int id, boolean includeInactives);

	/**
	 * Busca un turno en la base de datos por su ID.
	 * @param id ID del turno.
	 * @param includeInactives ¿Se deben incluir registros deshabilitados? Por defecto, false.
	 */
	Optional<AppointmentMinimalView> findMinById(int id, boolean includeInactives);

	/**
	 * Busca un turno en la base de datos por su ID.
	 * @param id ID del turno.
	 * @param includeInactives ¿Se deben incluir registros deshabilitados? Por defecto, false.
	 */
	Optional<AppointmentCommunicationView> findComById(int id, boolean includeInactives);

	/**
	 * Busca turnos.
	 * @param q Filtros a aplicar.
	 */
	List<AppointmentMinimalView> search(AppointmentQuery q);

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

	Boolean isAssigned(Appointment newAppointment) throws NotFoundException;
	
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
