package web.entity;

/**
 * Interfaz de la entidad Usuario (User).
 * 
 * Esta interfaz existe para permitir devolver {@link User} o {@link web.entity.view.UserView UserView}.
 * <br /><br />
 * <b>¿Por qué existen estas dos clases?</b><br />
 * Solucioné el tema de los permisos mediante la asignación de <u>permisos individuales</u> a cada usuario.<br />
 * Si un usuario solicita ver los datos de otro usuario, en función de los permisos asignados, se le devolverá un {@link User}, un {@link web.entity.view.UserView UserView}, o se lanzará un {@link web.exceptions.NotAllowedException NotAllowedException}.
 * 
 * @author Max
 * 
 * @see User
 * @see view.UserView UserView
 *
 */
public interface IUser {

	/**
	 * Devuelve el nombre de usuario.
	 */
	String getUsername();
	
	/**
	 * Establece un nuevo nombre de usuario.
	 */
	void setUsername(String username);
	
	/**
	 * Devuelve el nombre.
	 */
	String getName();
	
	/**
	 * Establece un nuevo nombre.
	 */
	void setName(String name);
	
	/**
	 * Devuelve el estado del usuario en el sistema.
	 */
	boolean isActive();
	
	/**
	 * Establece el estado del usuario en el sistema.
	 */
	void setActive(boolean active);
		
}
