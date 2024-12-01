package web.dao;

import java.util.List;
import java.util.Optional;
import web.entity.Schedule;
import web.entity.input.ScheduleQuery;
import web.exceptions.NotFoundException;

public interface IScheduleDAO {

    /**
     * Agrega un horario a la base de datos.
     * @param schedule Datos del horario a agregar.
     */
    Schedule add(Schedule schedule);

    /**
     * Busca un horario por su id.
     * @param id Id del horario.
     * @return Objeto Optional con los datos del horario si es encontrado
     */
    Optional<Schedule> findById(int id);

    /**
     * Actualiza un horario en la base de datos.
     * @param schedule Horario con los datos a actualizar.
     */
    Schedule update(Schedule schedule);

    /**
     * Deshabilita un horario de la base de datos.
     * @param id Id del horario a deshabilitar.
     */
    void disable(int id) throws NotFoundException;

    /**
     * Rehabilita un horario de la base de datos.
     * @param id Id del horario a habilitar.
     */
    void enable(int id) throws NotFoundException;

    /**
     * Busca un horario por su id, incluyendo inactivos si es necesario.
     * @param id Id del horario.
     * @param includeInactives Incluir horarios inactivos en la búsqueda.
     * @return Objeto Optional con los datos del horario si es encontrado
     */
    Optional<Schedule> findById(int id, boolean includeInactives);

    /**
     * Busca horarios basados en una consulta.
     * @param query Objeto de consulta para buscar horarios.
     * @return Lista de horarios que coinciden con la consulta.
     */
    List<Schedule> search(ScheduleQuery query);
}
