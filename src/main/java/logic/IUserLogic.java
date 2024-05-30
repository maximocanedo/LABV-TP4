package logic;

import java.util.List;
import entity.Optional;

import entity.User;

public interface IUserLogic {

	/**
	 * Registra una cuenta de usuario.
	 * @param user Datos del usuario a agregar.
	 */
	void signup(User user);

	/**
	 * Revisa si existe un usuario con el nombre de usuario indicado y compara su contraseña con la indicada.
	 * @param username Nombre de usuario.
	 * @param password Contraseña en texto plano.
	 * @return Objeto User, si las contraseñas no coinciden devuelve null.
	 */
	Optional<User> check(String username, String password);

	/**
	 * Busca un usuario por su nombre de usuario.
	 * @param username Nombre de usuario
	 */
	Optional<User> findByUsername(String username);

	/**
	 * Deshabilita un usuario.
	 * @param user Usuario a deshabilitar.
	 */
	void disable(User user);

	/**
	 * Habilita un usuario.
	 * @param user Usuario a habilitar.
	 */
	void enable(User user);

	/**
	 * Lista todos los usuarios de la base de datos.
	 * @param page Número de página (De 1 en adelante)
	 * @param size Cantidad de elementos.
	 */
	List<User> list(int page, int size);

	/**
	 * Lista todos los usuarios de la base de datos.
	 */
	List<User> list();

	/**
	 * Cambia la contraseña de un usuario sólo si la contraseña ingresada coincide con la actual.
	 * @param username Nombre de usuario.
	 * @param currentPassword Contraseña actual.
	 * @param newPassword Nueva contraseña.
	 * @return Resultado de la operación.
	 */
	boolean changePassword(String username, String currentPassword, String newPassword);
	
	/**
	 * Actualiza los datos (Salvo la contraseña) del usuario.
	 * @param user Usuario con los datos a actualizar.
	 * @return Resultado de la operación.
	 */
	boolean update(User user);

}