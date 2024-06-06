package dao;

import java.time.LocalDate;
import java.util.List;

import entity.Optional;
import exceptions.NotFoundException;
import entity.Doctor;

public interface IDoctorDAO {

    /**
     * Agrega un medico a la base de datos.
     * @param medico datos del medico a agregar.
     */
    void add(Doctor medico);

    /**
     * Busca un médico por su ID.
     * @param id ID del medico.
     */
    Optional<Doctor> findById(int id);
    
    /**
     * Busca un médico por su legajo.
     * @param file Legajo
     */
    Optional<Doctor> findByFile(int file);
    
    /**
     * Busca un médico por su ID.
     * @param id ID del medico.
     * @param searchDisabled Si se buscan registros deshabilitados.
     */
    Optional<Doctor> findById(int id, boolean searchDisabled);

    /**
     * Lista todos los medicos de la base de datos.
     */
    List<Doctor> list();
    
    /**
     * Lista los legajos de los medicos de la base de datos.
     */
    List<Object[]> listOnlyFileNumbersAndNames();
    
    /**
     * Obtiene el medico con el legajo de mayor valor de la base de datos.
     */
    Doctor findDoctorWithHighestFileNumber();

    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
    List<Doctor> list(int page, int size);
    
    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     * @param includeInactiveRecords Incluir registros inactivos.
     */
    List<Doctor> list(int page, int size, boolean includeInactiveRecords);
    
    /**
     * Lista todos los médicos de la base de datos, paginable.
     * @param page Número de página (De 1 en adelante)
     * @param size Cantidad de elementos por página.
     */
    List<Doctor> listOrderByFileDescending(int page, int size);
    
    /**
     * Lista todos los médicos de la base de datos.
     */
    List<Doctor> listOrderByFileDescending();

    /**
     * Actualiza un medico en la base de datos.
     * @param medico Medico con los datos a actualizar.
     */
    void update(Doctor medico);

    /**
     * Elimina permanentemente un medico de la base de datos.
     * @param medico Medico a eliminar.
     */
    @Deprecated
    void erase(Doctor medico);
    
    /**
     * Deshabilita un médico de la base de datos.
     * @param id ID del médico a eliminar.
     * @throws NotFoundException Si el ID ingresado no corresponde a ningún médico.
     */
    void disable(int id) throws NotFoundException;
    
    /**
     * Rehabilita un médico de la base de datos.
     * @param id ID del médico a rehabilitar.
     * @throws NotFoundException Si el ID ingresado no corresponde a ningún médico.
     */
    void enable(int id) throws NotFoundException;
    
    /**
     * Obtiene todos los legajos de los medicos 
     * @return Lista de legajos.
     */
    public List<Integer> listOnlyFileNumbers();
    
    /**
     * Devuelve una lista con el legajo, la fecha y el estado de cada turno asignado a un médico en específico durante la fecha indicada.
     * @param Legajo del médico.
     * @param Fecha deseada.
     */
    List<Object[]> getAppointmentsByDoctorAndDate(int fileNumber, LocalDate date);
    
    /**
     * Devuelve una lista con el legajo, la fecha y el estado de cada turno asignado a un médico en específico dentro del rango de fechas indicado.
     * @param Legajo del médico
     * @param startDate Fecha límite.
     * @param endDate Segunda fecha límite.
     */
    List<Object[]> getAppointmentsByDoctorAndDateRange(int fileNumber, LocalDate startDate, LocalDate endDate);
}
