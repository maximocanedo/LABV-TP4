'use strict';
import { useDatabase, req } from "./index.js";
/// <reference path="../types/entities.js" />

const db = useDatabase("patients");

/**
 * Actualiza un paciente.
 * @param {IPatient} patient Datos a actualizar.
 * @returns {Promise<IPatient>}
 */
export const update = async (patient) => {
    return patient;
    return db.transact(async (store) => req(store.get(patient.id)).then(legacyPatient => {
        /** @type {IPatient} */
        const updatedpatient = { ...legacyPatient, ...patient, _lastOfflineSaved: Date.now() };
        if(!patient.active) {
            return req(store.delete(patient.id)).then(() => patient);
        }
        return req(store.put(updatedpatient)).then(() => updatedpatient);
    }));
};

/**
 * Buscar paciente por ID.
 * @param {number} id ID
 * @returns {Promise<IPatient>}
 */
export const getById = async (id) => db.read(store => req(store.get(id)));

/**
 * Obtener todos los pacientes.
 * @returns {Promise<IPatient[]>}
 */
export const getAll = async () => db.read(store => req(store.getAll()));

/**
 * Función de ejemplo para buscar un paciente por nombre.
 * @param {string} name Nombre del paciente a buscar
 * @returns {Promise<IPatient[]>} Array de pacientes encontrados.
 */
export const findByName = async (name) => db.read(store => req(store.index('name').getAll(name)));