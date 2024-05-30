package dao;

import java.util.List;

import entity.Optional;
import entity.User;

public interface IUserDAO {

	/**
	 * Agrega un usuario a la base de datos.
	 * @param user Datos del usuario a agregar. Debe contener la contraseña ya encriptada.
	 */
	void add(User user);

	/**
	 * Obtiene un usuario por su nombre de usuario.
	 * @param username Nombre de usuario.
	 * @throws Exception 
	 * @throws NotFoundException 
	 */
	Optional<User> findByUsername(String username);

	/**
	 * Lista todos los usuarios de la base de datos.
	 */
	List<User> list();

	/**
	 * Lista todos los usuarios de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<User> list(int page, int size);

	/**
	 * Actualiza un usuario de la base de datos.
	 * @param user Usuario con los datos a actualizar.
	 */
	void update(User user);

	/**
	 * Elimina permanentemente un usuario de la base de datos.
	 * @param user Usuario a eliminar.
	 */
	void erase(User user);

}