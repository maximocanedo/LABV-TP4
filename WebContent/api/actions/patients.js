'use strict';
import * as u from './../auth.js';
import * as db from './../store/patients.js';
import { GenericQuery } from './commons.js';
/// <reference path="../types/entities.js" />

/**
 * Registra un paciente.
 * @param {PatientSignRequest} data 
 * @returns {Promise<Patient>} Paciente registrado.
 */
export const create = async (data) => {
    return u.post("patients", data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        })
};

export class Query extends GenericQuery {
    #dni;
    constructor(q = "") {
        super(q);
        super.setLocalDatabase(db);
        super.setPrefix("patients");
    }
}

/**
 * Buscar por ID.
 * @param {number} id 
 * @returns {Promise<IPatient>}
 */
export const findById = async (id) => {
    return u.get("patients/id/" + id)
        .then(response => response.json())
        .then(result => db.update(result))
        .catch(err => {
            throw err;
        });
};

/**
 * Buscar por DNI.
 * @param {string} DNI 
 * @returns {Promise<IPatient>}
 */
export const findByDNI = async (DNI) => {
    return u.get("patients/dni/" + DNI)
        .then(response => response.json())
        .then(result => db.update(result))
        .catch(err => {
            throw err;
        });
};


/**
 * Actualizar datos de un paciente.
 * @param {number} id 
 * @param {PatientUpdateRequest} data 
 * @returns {Promise<IPatient>}
 */
export const update = async (id, data) => {
    return u.patch("patients/id/" + id, data)
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
    return u.del("patients/id/" + id)
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
    return u.post("patients/id/" + id)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};