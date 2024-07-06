'use strict';
import * as u from './../auth.js';
import * as db from "./../store/doctors.js";
/// <reference path="../types/entities.js" />

/**
 * Crea un nuevo doctor.
 * @param {Object} data Datos del doctor a crear.
 * @returns {Promise<Object>} Promesa con la respuesta del servidor.
 */
export const create = async (data) => {
    return u.post("doctors", data)
        .then(response => response.json())
        .then(doctor => db.update(doctor)) // Actualiza el almacenamiento local
        .catch(err => {
            throw err;
        });
};

/**
 * @deprecated Usar {@link Query} en su lugar.
 * Busca todos los doctores.
 * @returns {Promise<Object[]>} Promesa con un array de doctores.
 */
export const findAll = async () => {
    return u.get("doctors")
        .then(response => response.json())
        .then(doctors => {
            const updatePromises = doctors.map(doctor => db.update(doctor)); // Actualiza el almacenamiento local
            return Promise.all(updatePromises);
        })
        .catch(err => {
            throw err;
        });
};

/**
 * Busca un doctor por su ID.
 * @param {number} id ID del doctor a buscar.
 * @returns {Promise<Object>} Promesa con los datos del doctor encontrado.
 */
export const findById = async (id) => {
    return u.get(`doctors/id/${id}`)
        .then(response => response.json())
        .then(doctor => db.update(doctor)) // Actualiza el almacenamiento local
        .catch(err => {
            throw err;
        });
};

/**
 * Busca un doctor por su legajo.
 * @param {number} file Legajo del doctor a buscar.
 * @returns {Promise<Object>} Promesa con los datos del doctor encontrado.
 */
export const findByFile = async (file) => {
    return u.get(`doctors/file/${file}`)
        .then(response => response.json())
        .then(doctor => db.update(doctor)) // Actualiza el almacenamiento local
        .catch(err => {
            throw err;
        });
};

/**
 * Actualiza los datos de un doctor.
 * @param {number} id ID del doctor a actualizar.
 * @param {Object} data Nuevos datos del doctor.
 * @returns {Promise<Object>} Promesa con los datos actualizados del doctor.
 */
export const update = async (id, data) => {
    return u.patch(`doctors/id/${id}`, data)
        .then(response => response.json())
        .then(updatedDoctor => db.update(updatedDoctor)) // Actualiza el almacenamiento local
        .catch(err => {
            throw err;
        });
};

/**
 * Deshabilita a un doctor por su ID.
 * @param {number} id ID del doctor a deshabilitar.
 * @returns {Promise<boolean>} Promesa con un booleano indicando si se deshabilitó correctamente.
 */
export const disable = async (id) => {
    const response = await u.del(`doctors/id/${id}`);
    if (response.ok) {
        // Borrar del almacenamiento local. PENDIENTE.
    }
    return response.ok;
};

/**
 * Habilita a un doctor por su ID.
 * @param {number} id ID del doctor a habilitar.
 * @returns {Promise<boolean>} Promesa con un booleano indicando si se habilitó correctamente.
 */
export const enable = async (id) => {
    const response = await u.post(`doctors/id/${id}`);
    if (response.ok) {
        // Se debería ACTUALIZAR, pero no recibimos una instancia actualizada, por lo que no hacemos nada.
        // Se actualizará una vez aparezca en alguna búsqueda o consulta.
    }
    return response.ok;
};

/**
 * Clase para construir y ejecutar consultas de búsqueda de doctores.
 * @example 
 * // Búsqueda simple:
 * const list: Promise<Doctor[]> = await new Query("Algo").search();
 * 
 * @example
 * // Búsqueda con filtros:
 * const query: Query = new Query("Algo");
 * query
 *   .filterByStatus(FilterStatus.ONLY_ACTIVE)
 *   .filterByDay("MONDAY")
 *   .filterBySpecialty(1)
 *   .paginate(2, 5);
 * const list: Promise<Doctor[]> = await query.search();
 */
export class Query {
    #q;
    #status = "";
    #day = "";
    #specialty = null;
    #page = 1;
    #size = 10;

    /**
     * Crea una nueva instancia de Query.
     * @param {string} q Texto de búsqueda.
     */
    constructor(q = "") {
        this.#q = q;
    }

    /**
     * Ejecuta la búsqueda de doctores según los parámetros configurados.
     * @returns {Promise<Object[]>} Promesa con un array de doctores encontrados.
     */
    async search() {
        const params = {
            q: this.#q,
            status: this.#status,
            day: this.#day,
            specialty: this.#specialty,
            page: this.#page,
            size: this.#size
        };

        return u.get("doctors", params)
            .then(response => response.json())
            .then(doctors => {
                const updatePromises = doctors.map(doctor => db.update(doctor)); // Actualiza el almacenamiento local
                return Promise.all(updatePromises);
            })
            .catch(err => {
                throw err;
            });
    }

    /**
     * Configura la paginación para la búsqueda.
     * @param {number} page Número de página.
     * @param {number} size Tamaño de la página.
     * @returns {Query} Instancia actual de Query.
     */
    paginate(page, size) {
        if (page != null && page > 0) this.#page = page;
        if (size != null && size > 0) this.#size = size;
        return this;
    }

    /**
     * Filtra los doctores por estado.
     * @param {string} status Estado por el cual filtrar.
     * @returns {Query} Instancia actual de Query.
     */
    filterByStatus(status) {
        this.#status = status;
        return this;
    }

    /**
     * Filtra los doctores por día disponible.
     * @param {string} day Día por el cual filtrar.
     * @returns {Query} Instancia actual de Query.
     */
    filterByDay(day) {
        this.#day = day;
        return this;
    }

    /**
     * Filtra los doctores por especialidad.
     * @param {string} specialty Especialidad por la cual filtrar.
     * @returns {Query} Instancia actual de Query.
     */
    filterBySpecialty(specialty) {
        this.#specialty = specialty;
        return this;
    }
};
