package logic;

import java.util.List;

import entity.Optional;

import entity.Patient;
import exceptions.NotFoundException;

public interface IPatientLogic {

	/**
	 * Agrega un paciente a la base de datos.
	 * @param paciente Datos del paciente a agregar. Debe contener la contraseña ya encriptada.
	 */
	void add(Patient paciente);

	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 */
	Optional<Patient> findById(int id);
	
	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 */
	Optional<Patient> findById(int id, boolean includeInactives);
	
	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<Patient> list(int page, int size, boolean includeInactives);

	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<Patient> list(int page, int size);

	/**
	 * Lista todos los pacientes de la base de datos.
	 */
	List<Patient> list();

	/**
	 * Actualiza un paciente de la base de datos.
	 * @param paciente Paciente con los datos a actualizar.
	 */
	void update(Patient paciente) throws NotFoundException;
	
	/**
	 * Elimina permanentemente un paciente de la base de datos.
	 * @param paciente Paciente a eliminar.
	 */
	@Deprecated
	void erase(Patient paciente);
	
	/**
	 * Deshabilita un paciente de la base de datos.
	 */
	void disable(int id) throws NotFoundException;
	
	/**
	 * Deshabilita un paciente de la base de datos.
	 */
	void enable(int id) throws NotFoundException;

}