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
     * Obtengo un medico por su id.
     * @param id Id del medico.
     * @return Objeto Medico con los datos.
     */
    Optional<Medico> getById(int id);
    
    /**
     * Buscar por legajo
     * @param file Legajo
     * @return Médico.
     */
    Optional<Medico> findByFile(int file);

    /**
     * Lista todos los medicos de la base de datos.
     * @return Lista con los medicos.
     */
    List<Medico> list();
    
    /**
     * Lista los legajos de los medicos de la base de datos.
     * @return Lista con los medicos.
     */
    List<Object[]> listMedicosLegajoAscP2();
    
    /**
     * Obtiene el medico con el legajo de mayor valor de la base de datos.
     * @return Lista con los medicos.
     */
    Medico medicoMayorLegajoP5();

    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     * @return Lista con los medicos.
     */
    List<Medico> list(int page, int size);
    
    /**
     * Lista todos los médicos de la base de datos, paginable.
     * @param page Número de página (De 1 en adelante)
     * @param size Cantidad de elementos por página.
     * @return Lista de médicos.
     */
    List<Medico> listOrderByFileDescending(int page, int size);
    
    /**
     * Lista todos los médicos de la base de datos.
     * @return Primeros diez registros.
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
     * @return legajo de los medicos. 
     */
    public List<Integer> TodosMedicosXLegajoP4();
    
    /**
     * Lista medico en fecha por parametro.
     * @param legajo del medico.
     * @param fecha filtro.
     */
    List<Object[]> getTurnosMedicoEnFecha(int legajo, LocalDate fecha);
    
    /**
     * Lista medico en rango de fechas por parametro.
     * @param legajo del medico.
     * @param fechaInicio filtro.
     * @param fechaFin filtro.
     */
    List<Object[]> getTurnosMedicoEnRangoDeFechas(int legajo, LocalDate fechaInicio, LocalDate fechaFin);
}
