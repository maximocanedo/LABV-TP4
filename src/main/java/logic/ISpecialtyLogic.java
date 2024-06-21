package logic;

import java.util.List;
import entity.Optional;
import exceptions.NotFoundException;
import entity.Specialty;
import entity.User;

public interface ISpecialtyLogic {
	
	/**
     * Agrega una especialidad a la base de datos.
     * @param specialty Datos de la especialidad a agregar.
     */
	void add(Specialty specialty, User requiring);

	/**
     * Busca una especialidad por su id.
     * @param id Id de la especialidad.
     * @return Objeto Especialidad con los datos
     */
	Optional<Specialty> findById(int id);

	/**
     * Actualiza una especialidad en la base de datos.
     * @param specialty Especialidad con los datos a actualizar.
     */
	void update(Specialty specialty, User requiring) throws NotFoundException;

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

	/**
     * Elimina permanentemente una especialidad de la base de datos.
     * @param specialty Especialidad a eliminar.
     */
	@Deprecated
	void permanentlyDelete(Specialty specialty);
	
	/**
     * Lista todas las especialidades de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     * @param includeInactiveRecords Si se deben incluir registros deshabilitados en la lista.
     */
	List<Specialty> list(int page, int size, boolean includeInactiveRecords, User requiring);

	/**
     * Lista todas las especialidades de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
	List<Specialty> list(int page, int size);
	
	/**
     * Lista todas las especialidades de la base de datos.
     * @param includeInactiveRecords Si se deben incluir registros deshabilitados en la lista.
     */
	List<Specialty> list(boolean includeInactiveRecords, User requiring);

	/**
    * Lista todas las especialidades de la base de datos.
    */
	List<Specialty> list();
	
	
	/** # Deprecated methods **/

	/**
     * Agrega una especialidad a la base de datos.
     * @param specialty Datos de la especialidad a agregar.
     */
	@Deprecated
	void add(Specialty specialty);

	/**
     * Actualiza una especialidad en la base de datos.
     * @param specialty Especialidad con los datos a actualizar.
     */
	@Deprecated
	void update(Specialty specialty) throws NotFoundException;

	/**
     * Deshabilita una especialidad de la base de datos.
     * @param especialidad Especialidad a deshabilitar.
     */
	@Deprecated
	void disable(int id) throws NotFoundException;

	/**
     * Habilita una especialidad de la base de datos.
     * @param especialidad Especialidad a habilitar.
     */
	@Deprecated
	void enable(int id) throws NotFoundException;

	/**
     * Lista todas las especialidades de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     * @param includeInactiveRecords Si se deben incluir registros deshabilitados en la lista.
     */
	@Deprecated
	List<Specialty> list(int page, int size, boolean includeInactiveRecords);
	
	/**
     * Lista todas las especialidades de la base de datos.
     * @param includeInactiveRecords Si se deben incluir registros deshabilitados en la lista.
     */
	@Deprecated
	List<Specialty> list(boolean includeInactiveRecords);


}