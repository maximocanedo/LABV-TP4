'use strict';
import * as u from './../auth.js';
import * as db from './../store/specialties.js';
import { GenericQuery } from './commons.js';
/// <reference path="../types/entities.js" />

/**
 * Registra una especialidad.
 * @param {SpecialtyProps} data 
 * @returns {Promise<Specialty>}
 */
export const create = async (data) => {
    return u.post("specialties", data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        })
};

/**
 * @class
 * @extends GenericQuery<Specialty>
 */
export class Query extends GenericQuery {
    constructor(q = "") {
        super(q);
        super.setLocalDatabase(db);
        super.setPrefix("specialties");
    }
}

/**
 * Buscar por ID.
 * @param {number} id 
 * @returns {Promise<Specialty>}
 */
export const findById = async (id) => {
    return u.get("specialties/id/" + id)
        .then(response => response.json())
        .then(result => db.update(result))
        .catch(err => {
            throw err;
        });
};

/**
 * Actualizar datos de una especialidad.
 * @param {number} id 
 * @param {SpecialtyProps} data 
 * @returns {Promise<Specialty>}
 */
export const update = async (id, data) => {
    return u.patch("specialties/id/" + id, data)
        .then(response => response.json())
        .then(result => db.update(result))
        .catch(err => {
            throw err;
        });
};

/**
 * Deshabilita un elemento.
 * @param {number} id 
 */
export const disable = async (id) => {
    return u.del("specialties/id/" + id)
        .then(response => response.ok)
        .then(ok => {
            // Eliminar de la db local.
            return ok;
        })
        .catch(err => {
            throw err;
        });
};

/**
 * Habilita un elemento.
 * @param {number} id 
 */
export const enable = async (id) => {
    return u.post("specialties/id/" + id)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};