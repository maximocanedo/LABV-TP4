'use strict';
import * as u from './../auth.js';
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

/**
 * 
 * @deprecated Usar search() PENDIENTE de crear.
 */
export const findAll = async () => {
    return u.get("patients/")
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

/**
 * Buscar por ID.
 * @param {number} id 
 * @returns {Promise<IPatient>}
 */
export const findById = async (id) => {
    return u.get("patients/id/" + id)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

/**
 * Actualizar datos de un paciente.
 * @param {number} id 
 * @param {PatientUpdateRequest} data 
 * @returns {Promise<Patient>}
 */
export const update = async (id, data) => {
    return u.patch("patients/id/" + id, data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

/**
 * 
 * @param {number} id 
 */
export const disable = async (id) => {
    return u.del("patients/id/" + id)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};

/**
 * 
 * @param {number} id 
 */
export const enable = async (id) => {
    return u.post("patients/id/" + id)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};