package web.logic;

import java.util.List;

import web.entity.Optional;
import web.entity.Patient;
import web.entity.User;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;

public interface IPatientLogic {
	
	/**
	 * Agrega un paciente a la base de datos.
	 * @param paciente Datos del paciente a agregar. Debe contener la contraseña ya encriptada.
	 */
	void add(Patient paciente, User requirinig);

	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 */
	Optional<Patient> findById(int id, User requiring) throws NotAllowedException;
	
	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 */
	Optional<Patient> findById(int id, boolean includeInactives, User requirinig);
	
	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<Patient> list(int page, int size, boolean includeInactives, User requirinig);

	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<Patient> list(int page, int size, User requirinig);

	/**
	 * Lista todos los pacientes de la base de datos.
	 */
	List<Patient> list(User requirinig);

	/**
	 * Actualiza un paciente de la base de datos.
	 * @param paciente Paciente con los datos a actualizar.
	 */
	Patient update(Patient paciente, User requirinig) throws NotFoundException;
	
	/**
	 * Deshabilita un paciente de la base de datos.
	 */
	void disable(int id, User requirinig) throws NotFoundException;
	
	/**
	 * Deshabilita un paciente de la base de datos.
	 */
	void enable(int id, User requirinig) throws NotFoundException;

	/**
	 * Agrega un paciente a la base de datos.
	 * @param paciente Datos del paciente a agregar. Debe contener la contraseña ya encriptada.
	 */
	@Deprecated
	void add(Patient paciente);

	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 */
	@Deprecated
	Optional<Patient> findById(int id);
	
	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 */
	@Deprecated
	Optional<Patient> findById(int id, boolean includeInactives);
	
	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	@Deprecated
	List<Patient> list(int page, int size, boolean includeInactives);

	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	@Deprecated
	List<Patient> list(int page, int size);

	/**
	 * Lista todos los pacientes de la base de datos.
	 */
	@Deprecated
	List<Patient> list();

	/**
	 * Actualiza un paciente de la base de datos.
	 * @param paciente Paciente con los datos a actualizar.
	 */
	@Deprecated
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
	@Deprecated
	void disable(int id) throws NotFoundException;
	
	/**
	 * Deshabilita un paciente de la base de datos.
	 */
	@Deprecated
	void enable(int id) throws NotFoundException;

}