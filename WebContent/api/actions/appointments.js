'use strict';
import * as u from './../auth.js';
import { GenericQuery } from './commons.js';
import * as db from './../store/appointment.js';
/// <reference path="../types/entities.js" />

/**
 * Registra un turno.
 * @param {AppointmentRegistrationRequest} data Datos del turno.
 * @returns {Promise<IAppointment>} Turno creado.
 */
export const create = async (data) => {
    return u.post("appointments", data)
        .then(response => response.json())
        .then(created => db.update(created))
        .catch(err => {
            throw err;
        });
};
/**
 * Busca un turno por id.
 * @param {number} id Id del turno.
 * @returns {Promise<IAppointment>}
 */
export const findById = async (id) => {
    return u.get(`appointments/id/${id}`)
        .then(response => response.json())
        .then(result => db.update(result))
        .catch(err => {
            throw err;
        });
};

/**
 * @class
 * @extends GenericQuery<Appointment>
 */
export class Query extends GenericQuery {
    /** @type {AppointmentStatus} */
    #appointmentStatus = null;
    /** @type {Date | string} */
    #date = null;
    /** @type {Date | string} */
    #limit = null;
    /** @type {IdentifiableDoctor} */
    #doctor = null;
    /** @type {IdentifiablePatient} */
    #patient = null;

    constructor(q = "") {
        super(q);
        super.setLocalDatabase(db);
        super.setPrefix("appointments");
    }

    filterByAppointmentStatus(/** @type {AppointmentStatus} */ status) {
        this.#appointmentStatus = status;
        return this;
    }

    filterByDate(/** @type {Date | string} */ date) {
        this.#date = date;
        return this;
    }

    setLimitDate(/** @type {Date | string} */ date) {
        this.#limit = date;
        return this;
    }

    filterByDateBetween(/** @type {Date | string} */ start, /** @type {Date | string} */ finish) {
        return this.filterByDate(start).setLimitDate(finish);
    }

    filterByDoctor(/** @type {IdentifiableDoctor} */ doctor) {
        if(
            (doctor.id == null || doctor.id == undefined || doctor.id < 1) &&
            (doctor.file == null || doctor.file == undefined || doctor.file < 1)
        ) return this;
        this.#doctor = doctor;
        return this;
    }

    filterByPatient(/** @type {IdentifiablePatient} */ patient) {
        if(!patient) {
            this.#patient = null;
            return this;
        } else if(
            (patient.id == null || patient.id == undefined || patient.id < 1) &&
            (patient.dni == null || patient.dni == undefined || patient.dni == "")
        ) return this;
        this.#patient = patient;
        return this;
    }

    getParams() {
        return {
            ...super.getParams(),
            appointmentStatus: this.#appointmentStatus,
            date: this.#date,
            limit: this.#limit,
            ...(this.#doctor && {
                doctorId: this.#doctor.id,
                doctorFile: this.#doctor.file,
            }),
            ...(this.#patient && {
                patientId: this.#patient.id,
                patientDni: this.#patient.dni
            })
        };
    }

}

/**
 * Actualiza un turno.
 * @param {number} id Id del turno.
 * @param {AppointmentBasicProperties} data Datos del turno.
 * @returns {Promise<IAppointment>}
 */
export const update = async (id, data) => {
    return u.patch(`appointments/id/${id}`, data)
        .then(response => response.json())
        .then(result => db.update(result))
        .catch(err => {
            throw err;
        });
};

/**
 * Elimina un turno.
 * @param {number} id Id del turno.
 * @returns {Promise<boolean>}
 */
export const disable = async (id) => {
    return u.del(`appointments/id/${id}`)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};
/**
 * Habilita logicamente un turno.
 * @param {number} id Id del turno.
 * @returns {Promise<boolean>}
 */
export const enable = async (id) => {
    return u.post(`appointments/id/${id}`)
        .then(response => response.ok)
        .catch(err => {
            throw err;
        });
};