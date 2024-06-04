package dao;

import java.time.LocalDate;
import java.util.List;

import entity.Optional;
import entity.Medico;

public interface IMedicoDAO {

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
     * Busca un médico por su legajo.
     * @param file Legajo
     */
    Optional<Medico> findByFile(int file);

    /**
     * Lista todos los medicos de la base de datos.
     */
    List<Medico> list();
    
    /**
     * Lista los legajos de los medicos de la base de datos.
     */
    List<Object[]> listOnlyFileNumbersAndNames();
    
    /**
     * Obtiene el medico con el legajo de mayor valor de la base de datos.
     */
    Medico findDoctorWithHighestFileNumber();

    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
    List<Medico> list(int page, int size);
    
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
     * Actualiza un medico en la base de datos.
     * @param medico Medico con los datos a actualizar.
     */
    void update(Medico medico);

    /**
     * Elimina permanentemente un medico de la base de datos.
     * @param medico Medico a eliminar.
     */
    void erase(Medico medico);
    
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
