'use strict';
import * as u from './../auth.js';
/// <reference path="../types/entities.js" />

/**
 * Registra un paciente.
 * @param {*} data 
 * @returns 
 */
export const create = async (data) => {
    return u.post("patients", data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        })
};

export const findAll = async () => {
    return u.get("patients/")
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

export const findById = async (id) => {
    return u.get("patients/id/" + id)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

export const update = async (id, data) => {
    return u.patch("patients/id/" + id, data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

export const disable = async (id) => {
    return u.del("patients/id/" + id)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};

export const enable = async (id) => {
    return u.post("patients/id/" + id)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};