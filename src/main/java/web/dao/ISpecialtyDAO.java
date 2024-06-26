package web.dao;

import java.util.List;

import web.entity.Optional;
import web.entity.Specialty;
import web.entity.input.SpecialtyQuery;
import web.exceptions.NotFoundException;

public interface ISpecialtyDAO {

    /**
     * Agrega una especialidad a la base de datos.
     * @param especialidad Datos de la especialidad a agregar.
     */
    void add(Specialty especialidad);

    /**
     * Busca una especialidad por su id.
     * @param id Id de la especialidad.
     * @return Objeto Especialidad con los datos
     */
    Optional<Specialty> findById(int id);

    /**
     * Lista todas las especialidades de la base de datos.
     */
    List<Specialty> list();

    /**
     * Lista todas las especialidades de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
    List<Specialty> list(int page, int size);
    
    /**
     * Lista las especialidades, permite incluir registros inactivos.
     */
    List<Specialty> list(boolean showInactiveRecords);
    
    /**
     * Lista las especialidades, permite incluir registros inactivos, paginable.
     */
    List<Specialty> list(int page, int size, boolean showInactiveRecords);

    /**
     * Actualiza una especialidad en la base de datos.
     * @param especialidad Especialidad con los datos a actualizar.
     */
    void update(Specialty especialidad);

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

	Optional<Specialty> findById(int id, boolean includeInactives);

	List<Specialty> search(SpecialtyQuery query);

}