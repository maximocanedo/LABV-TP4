package web.dao;

import java.util.List;

import org.hibernate.Query;

import web.entity.Optional;
import web.entity.Patient;
import web.entity.User;
import web.entity.input.PatientQuery;
import web.entity.input.UserQuery;
import web.exceptions.NotFoundException;

public interface IPatientDAO {

	/**
	 * Agrega un paciente a la base de datos.
	 * @param paciente Datos del paciente a agregar.
	 */
	Patient add(Patient paciente);

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
	 * Lista todos los pacientes de la base de datos.
	 * @deprecated use {@link #search(PatientQuery)} instead
	 */
	@Deprecated
	List<Patient> list();

	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 * @deprecated use {@link #search(PatientQuery)} instead
	 */
	@Deprecated
	List<Patient> list(int page, int size);
	
	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 * @deprecated use {@link #search(PatientQuery)} instead
	 */
	@Deprecated
	List<Patient> list(int page, int size, boolean includeInactives);

	/**
	 * Actualiza un paciente de la base de datos.
	 * @param paciente Paciente con los datos a actualizar.
	 */
	Patient update(Patient paciente);
	
	/**
	 * Busca pacientes.
	 */
	public List<Patient> search(PatientQuery q);

	/**
	 * Elimina permanentemente un paciente de la base de datos.
	 * @param paciente Paciente a eliminar.
	 */
	@Deprecated
	void erase(Patient paciente);
	
	/**
     * Deshabilita un paciente de la base de datos.
     * @param id ID del paciente a eliminar.
     * @throws NotFoundException Si el ID ingresado no corresponde a ningún paciente.
     */
    void disable(int id) throws NotFoundException;
    
    /**
     * Rehabilita un paciente de la base de datos.
     * @param id ID del paciente a rehabilitar.
     * @throws NotFoundException Si el ID ingresado no corresponde a ningún paciente.
     */
    void enable(int id) throws NotFoundException;

}