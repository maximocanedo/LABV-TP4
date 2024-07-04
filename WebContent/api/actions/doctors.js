'use strict';
import * as u from './../auth.js';
import { FilterStatus } from './users.js';
/// <reference path="../types/entities.js" />

/**
 * TODO: Integrar base de datos local.
 */

/**
 * Registra un doctor.
 * @param {DoctorRegistrationRequest} data Datos del doctor.
 * @returns {Promise<Doctor>} Copia del registro.
 */
export const create = async (data) => {
    return u.post("/doctors", data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

/**
 * Buscar doctor por ID.
 * @param {number} id ID del doctor
 * @returns {Promise<IDoctor>} Registro
 */
export const findById = async (id) => {
    return u.get("doctors/id/" + id)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

/**
 * Buscar doctor por número de legajo.
 * @param {number} file Número de legajo del doctor
 * @returns {Promise<IDoctor>} Registro
 */
export const findByFile = async (file) => {
    return u.get("/doctors/file/" + file)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

/**
 * Actualiza un registro de un doctor.
 * @param {number} id ID del doctor
 * @param {DoctorUpdateRequest} data Datos a actualizar. 
 * @returns Registro actualizado.
 */
export const update = async (id, data) => {
    return u.put("/doctors/id/" + id, data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

/**
 * Deshabilita un doctor.
 * @param {number} id ID del doctor.
 * @returns {Promise<boolean>} Resultado de la operación.
 */
export const disable = async (id) => {
    const response = await u.del("/doctors/id/" + id);
    return response.ok;
};

/**
 * Rehabilita un doctor.
 * @param {number} id ID del doctor.
 * @returns {Promise<boolean>} Resultado de la operación.
 */
export const enable = async (id) => {
    const response = await u.post("/doctors/id/" + id);
    return response.ok;
};

/**
 * @class Query
 * Permite crear y realizar consultas.
 */
export class Query {

    /**
     * @type {string}
     */
    #q;

    /**
     * @type {FilterStatus}
     */
    #status = "";

    /**
     * @type {string}
     */
    #day = "";

    /**
     * @type {number}
     */
    #specialty = null;

    #page = 1;

    #size = 10;

    /**
     * @constructor
     */
    constructor(q = "") {
        this.#q = q;
    }

    /**
     * Efectiviza la búsqueda.
     */
    async search() {
        return u.get("/doctors", {
            q: this.#q,
            status: this.#status,
            day: this.#day,
            specialty: this.#specialty,
            page: this.#page,
            size: this.#size
        }).then(response => response.json())
        .catch(err => {
            throw err;
        });
    }

    /**
     * Pagina la búsqueda.
     * @param {number} page Número de página
     * @param {number} size Tamaño de página
     * @returns {Query} Este objeto.
     */
    paginate(page, size) {
        if(page != null && page > 0) this.#page = page;
        if(size != null && size > 0) this.#size = size;
        return this;
    }

    /**
     * Filtra por estado.
     * @param {FilterStatus} status Estado
     * @returns {Query} Este objeto.
     */
    filterByStatus(status) {
        this.#status = status;
        return this;
    }

    /**
     * Filtra por día de semana.
     * @param {string} day Día de semana
     * @returns {Query} Este objeto.
     */
    filterByDay(day) {
        this.#day = day;
        return this;
    }

    /**
     * Filtrar por especialidad.
     * @param {number} specialty ID de especialidad
     * @returns {Query} Este objeto.
     */
    filterBySpecialty(specialty) {
        this.#specialty = specialty;
        return this;
    }

};
