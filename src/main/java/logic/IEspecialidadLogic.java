package logic;

import java.util.List;
import entity.Optional;
import exceptions.NotFoundException;
import entity.Especialidad;

public interface IEspecialidadLogic {

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
     * Actualiza una especialidad en la base de datos.
     * @param especialidad Especialidad con los datos a actualizar.
     */
	void update(Especialidad especialidad) throws NotFoundException;

	/**
     * Deshabilita una especialidad de la base de datos.
     * @param especialidad Especialidad a deshabilitar.
     */
	void disable(int id) throws NotFoundException;

	/**
     * Habilita una especialidad de la base de datos.
     * @param especialidad Especialidad a habilitar.
     */
	void enable(int id) throws NotFoundException;

	/**
     * Elimina permanentemente una especialidad de la base de datos.
     * @param especialidad Especialidad a eliminar.
     */
	@Deprecated
	void permanentlyDelete(Especialidad especialidad);
	
	/**
     * Lista todas las especialidades de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     * @param includeInactiveRecords Si se deben incluir registros deshabilitados en la lista.
     */
	List<Especialidad> list(int page, int size, boolean includeInactiveRecords);

	/**
     * Lista todas las especialidades de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
	List<Especialidad> list(int page, int size);
	
	/**
     * Lista todas las especialidades de la base de datos.
     * @param includeInactiveRecords Si se deben incluir registros deshabilitados en la lista.
     */
	List<Especialidad> list(boolean includeInactiveRecords);

	/**
    * Lista todas las especialidades de la base de datos.
    */
	List<Especialidad> list();

}