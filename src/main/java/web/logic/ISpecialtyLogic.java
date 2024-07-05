package web.logic;

import java.util.List;

import web.entity.Optional;
import web.entity.Specialty;
import web.entity.User;
import web.entity.input.SpecialtyQuery;
import web.exceptions.NotFoundException;

public interface ISpecialtyLogic {
	
	/**
     * Agrega una especialidad a la base de datos.
     * @param specialty Datos de la especialidad a agregar.
	 * @return 
     */
	Specialty add(Specialty specialty, User requiring);

	/**
     * Busca una especialidad por su id.
     * @param id Id de la especialidad.
     * @return Objeto Especialidad con los datos
     */
	Optional<Specialty> findById(int id);

	List<Specialty> search(SpecialtyQuery query, User requiring);
	
	/**
     * Actualiza una especialidad en la base de datos.
     * @param specialty Especialidad con los datos a actualizar.
     */
	Specialty update(Specialty specialty, User requiring) throws NotFoundException;

	/**
     * Deshabilita una especialidad de la base de datos.
     * @param especialidad Especialidad a deshabilitar.
     */
	void disable(int id, User requiring) throws NotFoundException;

	/**
     * Habilita una especialidad de la base de datos.
     * @param especialidad Especialidad a habilitar.
     */
	void enable(int id, User requiring) throws NotFoundException;

}