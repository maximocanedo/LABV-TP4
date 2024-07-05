package web.dao;

import java.util.List;

import web.entity.Optional;
import web.entity.Patient;
import web.entity.input.PatientQuery;
import web.entity.view.PatientCommunicationView;
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
	Optional<PatientCommunicationView> findComViewById(int id, boolean includeInactives);

	/**
	 * Actualiza un paciente de la base de datos.
	 * @param paciente Paciente con los datos a actualizar.
	 */
	Patient update(Patient paciente);
	
	/**
	 * Busca pacientes.
	 */
	public List<PatientCommunicationView> search(PatientQuery q);
	
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