package logic;

import java.util.List;

import entity.Optional;

import entity.Paciente;

public interface IPacienteLogic {

	/**
	 * Agrega un paciente a la base de datos.
	 * @param paciente Datos del paciente a agregar. Debe contener la contraseña ya encriptada.
	 */
	void add(Paciente paciente);

	/**
	 * Obtiene un paciente por su id.
	 * @param id. Id del paciente.
	 * @throws Exception 
	 * @throws NotFoundException 
	 */
	Optional<Paciente> findById(int id);

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
	void update(Paciente paciente);
	
	/**
	 * Elimina permanentemente un paciente de la base de datos.
	 * @param paciente Paciente a eliminar.
	 */
	void erase(Paciente paciente);

}