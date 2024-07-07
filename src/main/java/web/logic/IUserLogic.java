package web.logic;

import java.util.List;

import web.entity.IUser;
import web.entity.Optional;
import web.entity.User;
import web.entity.input.UserQuery;
import web.entity.view.UserView;
import web.exceptions.InvalidCredentialsException;
import web.exceptions.NotAllowedException;
import web.exceptions.NotFoundException;
import web.generator.PermitTemplate;

public interface IUserLogic {

	/**
	 * Registra una cuenta de usuario.
	 * @param user Datos del usuario a agregar.
	 * @return 
	 */
	User signup(User user);
	
	/**
	 * Registra una cuenta de usuario, con un set de permisos preestablecidos.
	 * @param user Datos del usuario.
	 * @param template Plantilla de permisos.
	 * @param requiring Usuario que solicita el registro.
	 * @return Usuario creado.
	 */
	User signup(User user, PermitTemplate template, User requiring);
	
	/**
	 * Registra una cuenta de usuario, con un set de permisos preestablecidos.
	 * @param user Datos del usuario.
	 * @param templateName Nombre de la plantilla de permisos.
	 * @param requiring Usuario que solicita el registro.
	 * @return Usuario creado.
	 */
	User signup(User user, String templateName, User requiring);

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
	 * Verifica la disponibilidad de un nombre de usuario.
	 */
	boolean checkUsernameAvailability(String username);
	
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
	 * Busca un usuario por su nombre de usuario.
	 * @param username Nombre de usuario.
	 * @param requiring Usuario que realiza la petición.
	 * @return Usuario solicitado.
	 * @throws NotFoundException Si el usuario no existe.
	 * @throws NotAllowedException Si el usuario que realiza la petición no tiene los suficientes permisos.
	 */
	User getByUsername(String username, User requiring) throws NotFoundException, NotAllowedException;
	
	/**
	 * Busca un usuario por su nombre de usuario.
	 * @param username Nombre de usuario.
	 * @param includeInactives Incluir usuarios deshabilitados.
	 * @param requiring Usuario que realiza la petición.
	 * @return Usuario solicitado.
	 * @throws NotFoundException Si el usuario no existe.
	 * @throws NotAllowedException Si el usuario que realiza la petición no tiene los suficientes permisos.
	 */
	IUser getByUsername(String username, boolean includeInactives, User requiring) throws NotFoundException, NotAllowedException;
	
	/**
	 * Busca usuarios.
	 * @param q Filtros y paginación a aplicar.
	 * @param requiring Usuario que solicita la búsqueda.
	 * @return Lista de usuarios.
	 */
	List<UserView> search(UserQuery q, User requiring);

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
	 * Deshabilita un usuario.
	 */
	void disable(String username, User requiring) throws NotFoundException;

	/**
	 * Habilita un usuario.
	 */
	void enable(String username, User requiring) throws NotFoundException;

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

	boolean checkUsernameAvailability(String username, User requiring);
	
}