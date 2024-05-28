package dao;

import java.util.List;

import entity.Medico;

public interface IMedicoDAO {

    /**
     * Agrega un medico a la base de datos.
     * @param medico datos del medico a agregar.
     */
    void add(Medico medico);

    /**
     * Obtengo un medico por su id.
     * @param id Id del medico.
     * @return Objeto Medico con los datos.
     */
    Medico getById(int id);

    /**
     * Lista todos los medicos de la base de datos.
     * @return Lista con los medicos.
     */
    List<Medico> list();
    
    /**
     * Lista los legajos de los medicos de la base de datos.
     * @return Lista con los medicos.
     */
    List<Object[]> listMedicosLegajoAscP2();
    
    /**
     * Obtiene el medico con el legajo de mayor valor de la base de datos.
     * @return Lista con los medicos.
     */
    Medico medicoMayorLegajoP5();

    /**
     * Lista todos los medicos de la base de datos, paginable.
     * @param page Numero de pagina (De 1 en adelante)
     * @param size Cantidad de elementos por pagina.
     * @return Lista con los medicos.
     */
    List<Medico> list(int page, int size);

    /**
     * Actualiza un medico en la base de datos.
     * @param medico Medico con los datos a actualizar.
     */
    void update(Medico medico);

    /**
     * Elimina permanentemente un medico de la base de datos.
     * @param medico Medico a eliminar.
     */
    void erase(Medico medico);
}
