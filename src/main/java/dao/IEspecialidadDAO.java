package dao;

import java.util.List;

import entity.Especialidad;

public interface IEspecialidadDAO {

    /**
     * Agrega una especialidad a la base de datos.
     * @param especialidad Datos de la especialidad a agregar.
     */
    void add(Especialidad especialidad);

    /**
     * Obtengo una especialidad por su id.
     * @param id Id de la especialidad.
     * @return Objeto Especialidad con los datos
     */
    Especialidad getById(int id);

    /**
     * Lista todas las especialidades de la base de datos.
     * @return Lista con las especialidades.
     */
    List<Especialidad> list();

    /**
     * Lista todas las especialidades de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     * @return Lista con las especialidades.
     */
    List<Especialidad> list(int page, int size);

    /**
     * Actualiza una especialidad en la base de datos.
     * @param especialidad Especialidad con los datos a actualizar.
     */
    void update(Especialidad especialidad);

    /**
     * Elimina permanentemente una especialidad de la base de datos.
     * @param especialidad Especialidad a eliminar.
     */
    void erase(Especialidad especialidad);
}
