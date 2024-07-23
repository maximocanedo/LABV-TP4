'use strict';
import * as u from './../auth.js';
import * as db from "./../store/doctors.js";
import { GenericQuery } from './commons.js';
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

export const existsByFile = async (file) => u.head(`doctors/file/${file}`)
    .then(response => response.ok)
    .catch(err => {
        throw err;
    });

export const existsById = async (id) => u.head(`doctors/id/${id}`)
    .then(response => response.ok)
    .catch(err => {throw err});


/**
 * Actualiza los datos de un doctor.
 * @param {number} id ID del doctor a actualizar.
 * @param {Object} data Nuevos datos del doctor.
 * @returns {Promise<IDoctor>} Promesa con los datos actualizados del doctor.
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
 * @param {number} scheduleId 
 * @param {number} doctorFile 
 * @returns {Promise<Schedule[]>}
 */
export const deleteSchedule = async (scheduleId, doctorFile) => {
    return u.del(`doctors/file/${doctorFile}/schedules/${scheduleId}`)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

/**
 * @param {Schedule} schedule 
 * @param {number} doctorFile 
 * @returns {Promise<Schedule[]>}
 */
export const addSchedule = async (schedule, doctorFile) => {
    return u.post(`doctors/file/${doctorFile}/schedules`, {
        ...schedule
    }).then(response => response.json())
    .catch(err => {
        throw err;
    });
}

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
 * @extends {GenericQuery<IDoctor>}
 * @example 
 * // Búsqueda simple:
 * const list: Promise<IDoctor[]> = await new Query("Algo").search();
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
export class Query extends GenericQuery {

    #day = "";
    #specialty = null;

    constructor(q = "") {
        super(q);
        super.setLocalDatabase(db);
        super.setPrefix("doctors");
    }

    getParams() {
        return {
            ...super.getParams(),
            day: this.#day,
            specialty: this.#specialty
        }
    }

    filterByDay(day) {
        this.#day = day;
        return this;
    }

    filterBySpecialty(specialty) {
        this.#specialty = specialty;
        return this;
    }
};