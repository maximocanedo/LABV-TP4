'use strict';
import * as u from './../auth.js';
/// <reference path="../types/entities.js" />

/**
 * Crea un nuevo appointment.
 * @param {Object} data Datos del appointment.
 * @returns {Promise<Object>} Promesa con la respuesta del servidor.
 */
export const create = async (data) => {
    return u.post("appointments", data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};
/**
 * Busca un appointment por id.
 * @param {number} id Id del appointment.
 * @returns {Promise<Object>} Promesa con la respuesta del servidor.
 */
export const findById = async (id) => {
    return u.get(`appoinments/id/${id}`)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};
/**
 * Busca todos los appointment.
 * @returns {Promise<Object>} Promesa con la respuesta del servidor.
 */
export const findAll = async () => {
    return u.get(`appoinments/`)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};
/**
 * Actualiza un appointment.
 * @param {number} id Id del appointment.
 * @param {Object} data Datos del appointment.
 * @returns {Promise<Object>} Promesa con la respuesta del servidor.
 */
export const update = async (id, data) => {
    return u.patch(`appoinments/id/${id}`, data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};
/**
 * Elimina logicamente un appointment.
 * @param {number} id Id del appointment.
 * @returns {Promise<Object>} Promesa con la respuesta del servidor.
 */
export const disable = async (id) => {
    return u.del(`appoinments/id/${id}`)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};
/**
 * Habilita logicamente un appointment.
 * @param {number} id Id del appointment.
 * @returns {Promise<Object>} Promesa con la respuesta del servidor.
 */
export const enable = async (id) => {
    return u.post(`appoinments/id/${id}`)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};