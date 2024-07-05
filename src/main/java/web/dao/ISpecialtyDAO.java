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
    Specialty add(Specialty especialidad);

    /**
     * Busca una especialidad por su id.
     * @param id Id de la especialidad.
     * @return Objeto Especialidad con los datos
     */
    Optional<Specialty> findById(int id);

    /**
     * Actualiza una especialidad en la base de datos.
     * @param especialidad Especialidad con los datos a actualizar.
     */
    Specialty update(Specialty especialidad);

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