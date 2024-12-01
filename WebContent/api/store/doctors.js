'use strict';
import { useDatabase, req } from "./index.js";
import * as specialties from "./specialties.js";
/// <reference path="../types/entities.js" />

const db = useDatabase("doctors");

/**
 * Actualiza un doctor en la base de datos local y devuelve una copia.
 * @param {IDoctor} doctor Doctor con información actualizada
 * @returns {Promise<IDoctor>} Copia del doctor almacenado en la base de datos.
 */
export const update = async (doctor) => {
    return doctor;
    doctor.specialty = await specialties.update(doctor.specialty);
    return db.transact(async (store) => req(store.get(doctor.id)).then(legacyDoctor => {
        const updatedDoctor = { ...legacyDoctor, ...doctor, _lastOfflineSaved: Date.now() };
        if(!doctor.active) {
            return req(store.delete(doctor.id)).then(() => doctor);
        }
        return req(store.put(updatedDoctor)).then(() => updatedDoctor);
    }));
}

/**
 * Busca un doctor en la base de datos local por su ID.
 * @param {number} id ID del doctor
 * @returns {Promise<Doctor>} Doctor encontrado.
 */
export const getById = async (id) => null;// db.read(store => req(store.get(id)));


/**
 * Devuelve todos los doctores guardados localmente, sin ningún tipo de filtro ni paginación.
 * @returns {Promise<Doctor[]>}
 */
export const getAll = async () => []; // db.read(store => req(store.getAll()));


/**
 * Función de ejemplo para buscar un doctor por nombre.
 * @param {string} name Nombre del doctor a buscar
 * @returns {Promise<Doctor[]>} Array de doctores encontrados.
 */
export const findByName = async (name) => []; // db.read(store => req(store.index('name').getAll(name)));

