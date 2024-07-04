'use strict';
import * as u from './../auth.js';
/// <reference path="../types/entities.js" />

/**
 * Registra un doctor.
 * @param {Doctor} data Datos del doctor.
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
    return u.get("/doctors/id/" + id)
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