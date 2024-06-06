package dao;

import java.util.List;
import entity.Optional;
import exceptions.NotFoundException;
import entity.Especialidad;

public interface IEspecialidadDAO {

    /**
     * Agrega una especialidad a la base de datos.
     * @param especialidad Datos de la especialidad a agregar.
     */
    void add(Especialidad especialidad);

    /**
     * Busca una especialidad por su id.
     * @param id Id de la especialidad.
     * @return Objeto Especialidad con los datos
     */
    Optional<Especialidad> findById(int id);

    /**
     * Lista todas las especialidades de la base de datos.
     */
    List<Especialidad> list();

    /**
     * Lista todas las especialidades de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
    List<Especialidad> list(int page, int size);
    
    /**
     * Lista las especialidades, permite incluir registros inactivos.
     */
    List<Especialidad> list(boolean showInactiveRecords);
    
    /**
     * Lista las especialidades, permite incluir registros inactivos, paginable.
     */
    List<Especialidad> list(int page, int size, boolean showInactiveRecords);

    /**
     * Actualiza una especialidad en la base de datos.
     * @param especialidad Especialidad con los datos a actualizar.
     */
    void update(Especialidad especialidad);

    /**
     * Deshabilita una especialidad de la base de datos.
     * @param especialidad Especialidad a eliminar.
     */
    void disable(int id) throws NotFoundException;
    
    /**
     * Rehabilita una especialidad de la base de datos.
     * @param especialidad Especialidad a eliminar.
     */
    void enable(int id) throws NotFoundException;

	Optional<Especialidad> findById(int id, boolean includeInactives);

}