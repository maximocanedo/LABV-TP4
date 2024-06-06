package logic;

import java.util.List;

import entity.Optional;

import entity.Paciente;
import exceptions.NotFoundException;

public interface IPacienteLogic {

	/**
	 * Agrega un paciente a la base de datos.
	 * @param paciente Datos del paciente a agregar. Debe contener la contraseña ya encriptada.
	 */
	void add(Paciente paciente);

	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 */
	Optional<Paciente> findById(int id);
	
	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 */
	Optional<Paciente> findById(int id, boolean includeInactives);
	
	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<Paciente> list(int page, int size, boolean includeInactives);

	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<Paciente> list(int page, int size);

	/**
	 * Lista todos los pacientes de la base de datos.
	 */
	List<Paciente> list();

	/**
	 * Actualiza un paciente de la base de datos.
	 * @param paciente Paciente con los datos a actualizar.
	 */
	void update(Paciente paciente) throws NotFoundException;
	
	/**
	 * Elimina permanentemente un paciente de la base de datos.
	 * @param paciente Paciente a eliminar.
	 */
	@Deprecated
	void erase(Paciente paciente);
	
	/**
	 * Deshabilita un paciente de la base de datos.
	 */
	void disable(int id) throws NotFoundException;
	
	/**
	 * Deshabilita un paciente de la base de datos.
	 */
	void enable(int id) throws NotFoundException;

}