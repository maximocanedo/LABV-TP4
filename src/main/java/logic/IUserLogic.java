package logic;

import java.util.List;
import entity.Optional;

import entity.User;
import exceptions.InvalidCredentialsException;
import exceptions.NotFoundException;
import generator.PermitTemplate;

public interface IUserLogic {

	/**
	 * Registra una cuenta de usuario.
	 * @param user Datos del usuario a agregar.
	 * @return 
	 */
	User signup(User user);
	
	User signup(User user, PermitTemplate template, User requiring);

	/**
	 * Revisa si existe un usuario con el nombre de usuario indicado y compara su contraseña con la indicada.
	 * @param username Nombre de usuario.
	 * @param password Contraseña en texto plano.
	 * @return Objeto User, si las contraseñas no coinciden devuelve null.
	 */
	Optional<User> check(String username, String password);
	
	/**
	 * Inicia sesión y devuelve un token de REFRESCO.
	 * @param username Nombre de usuario.
	 * @param password Contraseña.
	 * @return Token de refresco.
	 * @throws NotFoundException 
	 * @throws InvalidCredentialsException 
	 */
	String login(String username, String password) throws InvalidCredentialsException, NotFoundException;
	

	/**
	 * Busca un usuario por su nombre de usuario.
	 * @param username Nombre de usuario
	 */
	Optional<User> findByUsername(String username, User requiring);
	
	/**
	 * Busca un usuario por su nombre de usuario.
	 * @param username Nombre de usuario
	 */
	Optional<User> findByUsername(String username, boolean includeInactives, User requiring);

	/**
	 * Deshabilita un usuario.
	 * @param user Usuario a deshabilitar.
	 */
	void disable(User user, User requiring) throws NotFoundException;

	/**
	 * Habilita un usuario.
	 * @param user Usuario a habilitar.
	 */
	void enable(User user, User requiring) throws NotFoundException;

	/**
	 * Lista todos los usuarios de la base de datos.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<User> list(int page, int size, boolean includeInactives, User requiring);

	/**
	 * Lista todos los usuarios de la base de datos.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<User> list(int page, int size, User requiring);

	/**
	 * Lista todos los usuarios de la base de datos.
	 */
	List<User> list(User requiring);

	/**
	 * Cambia la contraseña de un usuario sólo si la contraseña ingresada coincide con la actual.
	 * @param username Nombre de usuario.
	 * @param currentPassword Contraseña actual.
	 * @param newPassword Nueva contraseña.
	 */
	void changePassword(String username, String currentPassword, String newPassword) throws NotFoundException;
	

	/**
	 * Cambia la contraseña de un usuario sólo si la contraseña ingresada coincide con la actual.
	 * @param username Nombre de usuario.
	 * @param currentPassword Contraseña actual.
	 * @param newPassword Nueva contraseña.
	 */
	void changePassword(String username, String newPassword, User requiring) throws NotFoundException;
	
	/**
	 * Actualiza los datos (Salvo la contraseña) del usuario.
	 * @param user Usuario con los datos a actualizar.
	 */
	User update(User user, User requiring) throws NotFoundException;
	
	
	
	/** # Deprecated methods **/
	
	/**
	 * Busca un usuario por su nombre de usuario.
	 * @param username Nombre de usuario
	 */
	@Deprecated
	Optional<User> findByUsername(String username);
	
	/**
	 * Busca un usuario por su nombre de usuario.
	 * @param username Nombre de usuario
	 */
	@Deprecated
	Optional<User> findByUsername(String username, boolean includeInactives);

	/**
	 * Deshabilita un usuario.
	 * @param user Usuario a deshabilitar.
	 */
	@Deprecated
	void disable(User user) throws NotFoundException;

	/**
	 * Habilita un usuario.
	 * @param user Usuario a habilitar.
	 */
	@Deprecated
	void enable(User user) throws NotFoundException;

	/**
	 * Lista todos los usuarios de la base de datos.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	@Deprecated
	List<User> list(int page, int size, boolean includeInactives);

	/**
	 * Lista todos los usuarios de la base de datos.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	@Deprecated
	List<User> list(int page, int size);

	/**
	 * Lista todos los usuarios de la base de datos.
	 */
	@Deprecated
	List<User> list();
	
	/**
	 * Actualiza los datos (Salvo la contraseña) del usuario.
	 * @param user Usuario con los datos a actualizar.
	 */
	@Deprecated
	void update(User user) throws NotFoundException;


}