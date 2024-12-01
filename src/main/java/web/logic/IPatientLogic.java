package web.logic;

import java.util.List;

import web.entity.IPatient;
import web.entity.Optional;
import web.entity.Patient;
import web.entity.User;
import web.entity.input.PatientQuery;
import web.entity.view.PatientCommunicationView;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;

public interface IPatientLogic {
	
	/**
	 * Agrega un paciente a la base de datos.
	 * @param paciente Datos del paciente a agregar. Debe contener la contraseña ya encriptada.
	 * @return 
	 */
	Patient add(Patient paciente, User requirinig);

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
	
	IPatient getById(int id, boolean includeInactives, User requiring) throws NotAllowedException;
	IPatient getById(int id, User requiring) throws NotAllowedException;
	
	/**
	 * Busca pacientes.
	 */
	List<PatientCommunicationView> search(PatientQuery query, User requiring);

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
	 * Habilita un paciente de la base de datos.
	 */
	
	void enable(int id, User requiring) throws NotFoundException;
}