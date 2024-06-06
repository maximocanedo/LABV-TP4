package logic;

import java.time.LocalDate;
import java.util.List;
import entity.Optional;
import exceptions.NotFoundException;
import entity.Medico;

public interface IMedicoLogic {

	/**
     * Agrega un medico a la base de datos.
     * @param medico datos del medico a agregar.
     */
    void add(Medico medico);

    /**
     * Busca un médico por su ID.
     * @param id ID del medico.
     */
    Optional<Medico> findById(int id);
    
    /**
     * Busca un médico por su ID.
     * @param id ID del medico.
     */
    Optional<Medico> findById(int id, boolean includeInactive);
    
    /**
     * Busca un médico por su legajo.
     * @param file Legajo
     */
    Optional<Medico> findByFile(int file);
    
    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
    List<Medico> list(int page, int size, boolean includeInactive);
    
    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
    List<Medico> list(int page, int size);

    /**
     * Lista todos los medicos de la base de datos.
     */
    List<Medico> list();
    
    /**
     * Lista los legajos de los medicos de la base de datos.
     */
    List<Object[]> listOnlyFileNumbersAndNames();
    
    /**
     * Obtiene todos los legajos de los medicos 
     * @return Lista de legajos.
     */
    List<Integer> listOnlyFileNumbers();

    /**
     * Devuelve una lista con el legajo, la fecha y el estado de cada turno asignado a un médico en específico durante la fecha indicada.
     * @param Legajo del médico.
     * @param Fecha deseada.
     */
    List<Object[]> getAppointmentsByDoctorAndDate(int fileNumber, LocalDate fecha);
    
    /**
     * Devuelve una lista con el legajo, la fecha y el estado de cada turno asignado a un médico en específico dentro del rango de fechas indicado.
     * @param Legajo del médico
     * @param startDate Fecha límite.
     * @param endDate Segunda fecha límite.
     */
    List<Object[]> getAppointmentsByDoctorAndDateRange(int fileNumber, LocalDate startDate, LocalDate endDate);
    
    /**
     * Obtiene el medico con el legajo de mayor valor de la base de datos.
     */
	Medico findDoctorWithHighestFileNumber();

	/**
     * Lista todos los médicos de la base de datos, paginable.
     * @param page Número de página (De 1 en adelante)
     * @param size Cantidad de elementos por página.
     */
	List<Medico> listOrderByFileDescending(int page, int size);

	/**
     * Lista todos los médicos de la base de datos.
     */
	List<Medico> listOrderByFileDescending();
	
	/**
	 * Actualiza un médico de la base de datos.
	 */
	void update(Medico medico) throws NotFoundException;
	
	/**
	 * Elimina permanentemente un médico de la base de datos.
	 */
	@Deprecated
	void erase(Medico medico);
	
	/**
	 * Deshabilita un médico de la base de datos.
	 */
	void disable(int id) throws NotFoundException;
	
	/**
	 * Rehabilita un médico de la base de datos.
	 */
	void enable(int id) throws NotFoundException;
}
