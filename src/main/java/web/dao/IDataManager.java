package web.dao;

import java.util.function.Consumer;

import org.hibernate.Session;

public interface IDataManager {
	
	/**
	 * Ejecuta una transacción de tipo lectura en la base de datos.
	 * @param function Función a ejecutar, recibe un parámetro de tipo {@link Session}.
	 * @param onError Función a ejecutar en caso de error, recibe un paráemtro de tipo {@link Exception}. Por defecto imprime en consola. 
	 */
	void run(Consumer<Session> function, Consumer<Exception> onError);
	
	/**
	 * Ejecuta una transacción de tipo lectura en la base de datos.
	 * @param function Función a ejecutar, recibe un parámetro de tipo {@link Session}. 
	 */
	void run(Consumer<Session> function);
	
	/**
	 * Ejecuta una transacción de tipo lectoescritura en la base de datos.
	 * @param function Función a ejecutar, recibe un parámetro de tipo {@link Session}.
	 * @param onError Función a ejecutar en caso de error, recibe un paráemtro de tipo {@link Exception}. Por defecto imprime en consola. 
	 */
	void transact(Consumer<Session> function, Consumer<Exception> onError);

	/**
	 * Ejecuta una transacción de tipo lectoescritura en la base de datos.
	 * @param function Función a ejecutar, recibe un parámetro de tipo {@link Session}. 
	 */
	void transact(Consumer<Session> function);
	
	/**
	 * Cierra la conexión a la base de datos.
	 */
	void shutdown();
	
}