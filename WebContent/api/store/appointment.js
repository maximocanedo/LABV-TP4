'use strict';
import { useDatabase, req } from "./index.js";
/// <reference path="../types/entities.js" />

const db = useDatabase("appointments");

/**
 * Actualiza un turno.
 * @param {IAppointment} appointment Datos a actualizar.
 * @returns {Promise<IAppointment>}
 */
export const update = async (appointment) => {
    return db.transact(async (store) => req(store.get(appointment.id)).then(legacyAppointment => {
        /** @type {IAppointment} */
        const updatedappointment = { ...legacyAppointment, ...appointment, _lastOfflineSaved: Date.now() };
        if(!appointment.active) {
            return req(store.delete(appointment.id)).then(() => appointment);
        }
        return req(store.put(updatedappointment)).then(() => updatedappointment);
    }));
};

/**
 * Buscar turno por ID.
 * @param {number} id ID
 * @returns {Promise<IAppointment>}
 */
export const getById = async (id) => db.read(store => req(store.get(id)));

/**
 * Obtener todos los turnos.
 * @returns {Promise<IAppointment[]>}
 */
export const getAll = async () => db.read(store => req(store.getAll()));

/**
 * Función de ejemplo para buscar un turno por nombre.
 * @param {string} name Nombre del turno a buscar
 * @returns {Promise<IAppointment[]>} Array de turnos encontrados.
 */
// export const findByName = async (name) => db.read(store => req(store.index('name').getAll(name)));