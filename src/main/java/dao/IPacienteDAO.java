package dao;

import java.util.List;
import entity.Optional;
import entity.Paciente;

public interface IPacienteDAO {

	/**
	 * Agrega un paciente a la base de datos.
	 * @param paciente Datos del paciente a agregar. Debe contener la contraseña ya encriptada.
	 */
	void add(Paciente paciente);

	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 * @return Objeto Paciente con los datos del paciente en cuestión.
	 * @throws Exception 
	 * @throws NotFoundException 
	 */
	Optional<Paciente> getById(int id);

	/**
	 * Lista todos los pacientes de la base de datos.
	 * @return Lista con los pacientes.
	 */
	List<Paciente> list();

	/**
	 * Lista todos los pacientes de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 * @return Lista con los pacientes.
	 */
	List<Paciente> list(int page, int size);

	/**
	 * Actualiza un paciente de la base de datos.
	 * @param paciente Paciente con los datos a actualizar.
	 */
	void update(Paciente paciente);

	/**
	 * Elimina permanentemente un paciente de la base de datos.
	 * @param paciente Paciente a eliminar.
	 */
	void erase(Paciente paciente);

}