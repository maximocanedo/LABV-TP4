package dao;

import java.util.List;
import entity.Optional;
import entity.Paciente;
import exceptions.NotFoundException;

public interface IPacienteDAO {

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
	 * Lista todos los pacientes de la base de datos.
	 */
	List<Paciente> list();

	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<Paciente> list(int page, int size);
	
	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<Paciente> list(int page, int size, boolean includeInactives);

	/**
	 * Actualiza un paciente de la base de datos.
	 * @param paciente Paciente con los datos a actualizar.
	 */
	void update(Paciente paciente);

	/**
	 * Elimina permanentemente un paciente de la base de datos.
	 * @param paciente Paciente a eliminar.
	 */
	@Deprecated
	void erase(Paciente paciente);
	
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