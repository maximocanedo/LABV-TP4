package logic;

import java.util.List;
import entity.Optional;
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
	void update(Especialidad especialidad);

	/**
     * Deshabilita una especialidad de la base de datos.
     * @param especialidad Especialidad a deshabilitar.
     */
	void disable(Especialidad especialidad);

	/**
     * Habilita una especialidad de la base de datos.
     * @param especialidad Especialidad a habilitar.
     */
	void enable(Especialidad especialidad);

	/**
     * Elimina permanentemente una especialidad de la base de datos.
     * @param especialidad Especialidad a eliminar.
     */
	void permanentlyDelete(Especialidad especialidad);

	/**
     * Lista todas las especialidades de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     */
	List<Especialidad> list(int page, int size);

	/**
    * Lista todas las especialidades de la base de datos.
    */
	List<Especialidad> list();

}