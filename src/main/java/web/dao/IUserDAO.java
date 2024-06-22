package web.dao;

import java.util.List;

import web.entity.Optional;
import web.entity.User;
import web.exceptions.NotFoundException;

public interface IUserDAO {

	/**
	 * Agrega un usuario a la base de datos.
	 * @param user Datos del usuario a agregar. Debe contener la contraseña ya encriptada.
	 */
	User add(User user);

	/**
	 * Obtiene un usuario por su nombre de usuario.
	 * @param username Nombre de usuario.
	 * @throws Exception 
	 * @throws NotFoundException 
	 */
	Optional<User> findByUsername(String username);
	
	/**
	 * Obtiene un usuario por su nombre de usuario.
	 * @param username Nombre de usuario.
	 * @throws Exception 
	 * @throws NotFoundException 
	 */
	Optional<User> findByUsername(String username, boolean includeInactives);

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
	 * Lista todos los usuarios de la base de datos, paginable.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<User> list(int page, int size, boolean includeInactives);

	/**
	 * Actualiza un usuario de la base de datos.
	 * @param user Usuario con los datos a actualizar.
	 * @return 
	 */
	User update(User user);

	/**
	 * Elimina permanentemente un usuario de la base de datos.
	 * @param user Usuario a eliminar.
	 */
	@Deprecated
	void erase(User user);
	
	void disable(String username) throws NotFoundException;
	
	void enable(String username) throws NotFoundException;

}