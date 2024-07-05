package web.dao;

import java.util.List;

import web.entity.Optional;
import web.entity.User;
import web.entity.input.UserQuery;
import web.entity.view.UserView;
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
	 * @param includeInactives Define si se deben incluir registros lógicamente eliminados.
	 */
	Optional<User> findByUsername(String username);
	
	/**
	 * Obtiene un usuario por su nombre de usuario.
	 * @param username Nombre de usuario.
	 * @param includeInactives Define si se deben incluir registros lógicamente eliminados.
	 */
	Optional<User> findByUsername(String username, boolean includeInactives);
	
	/**
	 * Obtiene un usuario por su nombre de usuario.
	 * @param username Nombre de usuario.
	 * @throws Exception 
	 * @throws NotFoundException 
	 */
	Optional<UserView> findBasicByUsername(String username, boolean includeInactives);
	
	/**
	 * Consulta la disponibilidad de un nombre de usuario.
	 */
	boolean checkUsernameAvailability(String username);
	
	/**
	 * Busca usuarios.
	 * @param q Filtros y paginación.
	 */
	List<UserView> search(UserQuery q);

	/**
	 * Actualiza un usuario de la base de datos.
	 * @param user Usuario con los datos a actualizar.
	 * @return 
	 */
	User update(User user);
	
	/**
	 * Deshabilita un usuario.
	 */
	void disable(String username) throws NotFoundException;
	
	/**
	 * Rehabilita un usuario.
	 */
	void enable(String username) throws NotFoundException;

}