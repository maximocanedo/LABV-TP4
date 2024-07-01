package web.logic;

import java.time.LocalDate;
import java.util.List;

import web.entity.Doctor;
import web.entity.Optional;
import web.entity.User;
import web.exceptions.NotFoundException;

public interface IDoctorLogic {
	
	/**
     * Agrega un medico a la base de datos.
     * @param medico datos del medico a agregar.
     */
    Doctor add(Doctor medico, User requiring);

    /**
     * Busca un médico por su ID.
     * @param id ID del medico.
     */
    Optional<Doctor> findById(int id, User requiring);
    
    /**
     * Busca un médico por su ID.
     * @param id ID del medico.
     */
    Optional<Doctor> findById(int id, boolean includeInactive, User requiring);
    
    /**
     * Busca un médico por su legajo.
     * @param file Legajo
     */
    Optional<Doctor> findByFile(int file, User requiring);
    
    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
    @Deprecated
    List<Doctor> list(int page, int size, boolean includeInactive, User requiring);
    
    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
    @Deprecated
    List<Doctor> list(int page, int size, User requiring);

    /**
     * Lista todos los medicos de la base de datos.
     */
    @Deprecated
    List<Doctor> list(User requiring);
    
    /**
     * Lista los legajos de los medicos de la base de datos.
     */
    List<Object[]> listOnlyFileNumbersAndNames(User requiring);
    
    /**
     * Obtiene todos los legajos de los medicos 
     * @return Lista de legajos.
     */
    List<Integer> listOnlyFileNumbers(User requiring);

    /**
     * Devuelve una lista con el legajo, la fecha y el estado de cada turno asignado a un médico en específico durante la fecha indicada.
     * @param Legajo del médico.
     * @param Fecha deseada.
     */
    List<Object[]> getAppointmentsByDoctorAndDate(int fileNumber, LocalDate fecha, User requiring);
    
    /**
     * Devuelve una lista con el legajo, la fecha y el estado de cada turno asignado a un médico en específico dentro del rango de fechas indicado.
     * @param Legajo del médico
     * @param startDate Fecha límite.
     * @param endDate Segunda fecha límite.
     */
    List<Object[]> getAppointmentsByDoctorAndDateRange(int fileNumber, LocalDate startDate, LocalDate endDate, User requiring);
    
    /**
     * Obtiene el medico con el legajo de mayor valor de la base de datos.
     */
	Doctor findDoctorWithHighestFileNumber(User requiring);

	/**
     * Lista todos los médicos de la base de datos, paginable.
     * @param page Número de página (De 1 en adelante)
     * @param size Cantidad de elementos por página.
     */
	List<Doctor> listOrderByFileDescending(int page, int size, User requiring);

	/**
     * Lista todos los médicos de la base de datos.
     */
	List<Doctor> listOrderByFileDescending(User requiring);
	
	/**
	 * Actualiza un médico de la base de datos.
	 */
	Doctor update(Doctor medico, User requiring) throws NotFoundException;
	
	/**
	 * Elimina permanentemente un médico de la base de datos.
	 */
	@Deprecated
	void erase(Doctor medico);
	
	/**
	 * Deshabilita un médico de la base de datos.
	 */
	void disable(int id, User requiring) throws NotFoundException;
	
	/**
	 * Rehabilita un médico de la base de datos.
	 */
	void enable(int id, User requiring) throws NotFoundException;
	
	/** # Deprecated methods **/

	/**
     * Agrega un medico a la base de datos.
     * @param medico datos del medico a agregar.
     */
	@Deprecated
    void add(Doctor medico);

    /**
     * Busca un médico por su ID.
     * @param id ID del medico.
     */
	@Deprecated
    Optional<Doctor> findById(int id);
    
    /**
     * Busca un médico por su ID.
     * @param id ID del medico.
     */
	@Deprecated
    Optional<Doctor> findById(int id, boolean includeInactive);
    
    /**
     * Busca un médico por su legajo.
     * @param file Legajo
     */
	@Deprecated
    Optional<Doctor> findByFile(int file);
    
    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
	@Deprecated
    List<Doctor> list(int page, int size, boolean includeInactive);
    
    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
	@Deprecated
    List<Doctor> list(int page, int size);

    /**
     * Lista todos los medicos de la base de datos.
     */
	@Deprecated
    List<Doctor> list();
    
    /**
     * Lista los legajos de los medicos de la base de datos.
     */
	@Deprecated
    List<Object[]> listOnlyFileNumbersAndNames();
    
    /**
     * Obtiene todos los legajos de los medicos 
     * @return Lista de legajos.
     */
	@Deprecated
    List<Integer> listOnlyFileNumbers();

    /**
     * Devuelve una lista con el legajo, la fecha y el estado de cada turno asignado a un médico en específico durante la fecha indicada.
     * @param Legajo del médico.
     * @param Fecha deseada.
     */
	@Deprecated
    List<Object[]> getAppointmentsByDoctorAndDate(int fileNumber, LocalDate fecha);
    
    /**
     * Devuelve una lista con el legajo, la fecha y el estado de cada turno asignado a un médico en específico dentro del rango de fechas indicado.
     * @param Legajo del médico
     * @param startDate Fecha límite.
     * @param endDate Segunda fecha límite.
     */
	@Deprecated
    List<Object[]> getAppointmentsByDoctorAndDateRange(int fileNumber, LocalDate startDate, LocalDate endDate);
    
    /**
     * Obtiene el medico con el legajo de mayor valor de la base de datos.
     */
	@Deprecated
	Doctor findDoctorWithHighestFileNumber();

	/**
     * Lista todos los médicos de la base de datos, paginable.
     * @param page Número de página (De 1 en adelante)
     * @param size Cantidad de elementos por página.
     */
	@Deprecated
	List<Doctor> listOrderByFileDescending(int page, int size);

	/**
     * Lista todos los médicos de la base de datos.
     */
	@Deprecated
	List<Doctor> listOrderByFileDescending();
	
	/**
	 * Actualiza un médico de la base de datos.
	 */
	@Deprecated
	void update(Doctor medico) throws NotFoundException;
	
	
	/**
	 * Deshabilita un médico de la base de datos.
	 */
	@Deprecated
	void disable(int id) throws NotFoundException;
	
	/**
	 * Rehabilita un médico de la base de datos.
	 */
	@Deprecated
	void enable(int id) throws NotFoundException;
}
