package web.logic;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import web.entity.Doctor;
import web.entity.Optional;
import web.entity.Schedule;
import web.entity.User;
import web.entity.input.DoctorQuery;
import web.entity.view.DoctorMinimalView;
import web.exceptions.NotFoundException;

/**
 * Interface que define la <b>lógica de operaciones</b> para la gestión de médicos.
 * Permite administrar registros de médicos en el sistema.
 */
public interface IDoctorLogic {

    /**
     * Agrega un nuevo médico a la base de datos.
     * <br />
     * No toma en cuenta los horarios, porque al ser un objeto de registro, sin ningún tipo de ID, no se puede validar las superposiciones con el resto de horarios.
     * Es mejor agregar los horarios luego del registro del médico, mediante el uso de otros métodos especializados.
     *
     * @param medico Los datos del médico a agregar.
     * @param requiring El usuario que realiza la solicitud.
     * @return El médico agregado.
     * 
     * @see {@link #addSchedule(int, Schedule, User)}
	 *
     * @see {@link #removeSchedule(int, Schedule, User)}
     * 
     */
    Doctor add(Doctor medico, User requiring);

    /**
     * Busca un médico por su ID.
     *
     * @param id El ID del médico.
     * @param requiring El usuario que realiza la solicitud.
     * @return Un objeto {@link Optional} que contiene el médico si se encuentra, o vacío si no.
     */
    Optional<Doctor> findById(int id, User requiring);
    
    /**
     * Busca médicos en la base de datos que coincidan con los criterios especificados en la consulta.
     *
     * @param query Un objeto {@link DoctorQuery} que contiene los criterios de búsqueda.
     *              Incluye filtros y paginación.
     * @param requiring El usuario que realiza la solicitud.
     * @return Una lista de registros {@link DoctorMinimalView} que coinciden con los criterios de búsqueda y paginación.
     */
	List<DoctorMinimalView> search(DoctorQuery query, User requiring);

    /**
     * Busca un médico por su ID, incluyendo opcionalmente médicos inactivos.
     *
     * @param id El ID del médico.
     * @param includeInactive Si se deben incluir médicos inactivos en la búsqueda.
     * @param requiring El usuario que realiza la solicitud.
     * @return Un objeto {@link Optional} que contiene el médico si se encuentra, o vacío si no.
     */
    Optional<Doctor> findById(int id, boolean includeInactive, User requiring);

    /**
     * Busca un médico por su legajo.
     *
     * @param file El legajo del médico.
     * @param requiring El usuario que realiza la solicitud.
     * @return Un objeto {@link Optional} que contiene el médico si se encuentra, o vacío si no.
     */
    Optional<Doctor> findByFile(int file, User requiring);

    /**
     * Lista los legajos y nombres de los médicos de la base de datos.
     *
     * @param requiring El usuario que realiza la solicitud.
     * @return Lista de arrays de objetos que contienen legajo y nombre de cada médico.
     */
    List<Object[]> listOnlyFileNumbersAndNames(User requiring);

    /**
     * Obtiene los legajos de todos los médicos de la base de datos.
     *
     * @param requiring El usuario que realiza la solicitud.
     * @return Lista de legajos.
     */
    List<Integer> listOnlyFileNumbers(User requiring);

    /**
     * Obtiene los turnos asignados a un médico específico en una fecha dada.
     *
     * @param fileNumber El legajo del médico.
     * @param fecha La fecha deseada.
     * @param requiring El usuario que realiza la solicitud.
     * @return Lista de arrays de objetos que contienen legajo, fecha y estado de cada turno.
     */
    List<Object[]> getAppointmentsByDoctorAndDate(int fileNumber, LocalDate fecha, User requiring);

    /**
     * Obtiene los turnos asignados a un médico específico en un rango de fechas.
     *
     * @param fileNumber El legajo del médico.
     * @param startDate La fecha de inicio del rango.
     * @param endDate La fecha de fin del rango.
     * @param requiring El usuario que realiza la solicitud.
     * @return Lista de arrays de objetos que contienen legajo, fecha y estado de cada turno.
     */
    List<Object[]> getAppointmentsByDoctorAndDateRange(int fileNumber, LocalDate startDate, LocalDate endDate, User requiring);

    /**
     * Encuentra el médico con el legajo de mayor valor en la base de datos.
     *
     * @param requiring El usuario que realiza la solicitud.
     * @return El médico con el legajo más alto.
     */
    Doctor findDoctorWithHighestFileNumber(User requiring);

    /**
     * Lista todos los médicos de la base de datos ordenados por legajo en orden descendente, con paginación.
     *
     * @param page Número de la página (desde 1 en adelante).
     * @param size Cantidad de elementos por página.
     * @param requiring El usuario que realiza la solicitud.
     * @return Lista de médicos.
     */
    List<Doctor> listOrderByFileDescending(int page, int size, User requiring);

    /**
     * Lista todos los médicos de la base de datos ordenados por legajo en orden descendente.
     *
     * @param requiring El usuario que realiza la solicitud.
     * @return Lista de médicos.
     */
    List<Doctor> listOrderByFileDescending(User requiring);

    /**
     * Actualiza la información de un médico en la base de datos.
     *
     * @param medico Los datos del médico a actualizar.
     * @param requiring El usuario que realiza la solicitud.
     * @return El médico actualizado.
     * @throws NotFoundException Si el médico no se encuentra en la base de datos.
     */
    Doctor update(Doctor medico, User requiring) throws NotFoundException;

    /**
     * Deshabilita un médico en la base de datos.
     *
     * @param id El ID del médico a deshabilitar.
     * @param requiring El usuario que realiza la solicitud.
     * @throws NotFoundException Si el médico no se encuentra en la base de datos.
     */
    void disable(int id, User requiring) throws NotFoundException;

    /**
     * Rehabilita un médico en la base de datos.
     *
     * @param id El ID del médico a rehabilitar.
     * @param requiring El usuario que realiza la solicitud.
     * @throws NotFoundException Si el médico no se encuentra en la base de datos.
     */
    void enable(int id, User requiring) throws NotFoundException;

    /**
     * Agrega un horario al registro de un médico.
     * @param file Legajo del médico.
     * @param schedule Horario a agregar.
     * @param requiring Usuario que realiza la solicitud.
     * @return Lista de horarios del médico, actualizada.
     */
	Set<Schedule> addSchedule(int file, Schedule schedule, User requiring);

	/**
	 * Elimina un horario del registro de un médico.
	 * @param file Legajo del médico.
	 * @param schedule Horario a remover. Debe contener el ID del horario.
	 * @param requiring Usuario que realiza la solicitud.
	 * @return Lista de horarios del médico, actualizada.
	 */
	Set<Schedule> removeSchedule(int file, Schedule schedule, User requiring);

}
