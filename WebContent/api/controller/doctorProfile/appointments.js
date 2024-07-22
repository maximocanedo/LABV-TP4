'use strict';
import { PatientSelector } from "./../../lib/selectors/PatientSelector.js";
import * as appointments from "./../../actions/appointments.js";

const button = (selector) => /** @type {HTMLButtonElement} */(document.querySelector(selector));
const input = (selector) => /** @type {HTMLInputElement} */(document.querySelector(selector));
const div = (selector) => /** @type {HTMLDivElement} */(document.querySelector(selector));
const select = selector => /** @type {HTMLSelectElement} */(document.querySelector(selector));

let doctor = null;

export const btnSearchForAppointments = button("#btnSearchForAppointments");
export const appointment_q = input("#appointment_q");
export const patientSelector = new PatientSelector();
export const selectStatus = select("#selectStatus");
export const inputDesde = input("#inputDesde");
export const inputHasta = input("#inputHasta");
export const appointmentQuery = new appointments.Query(appointment_q.value);
export const cln = x => x != null && x != undefined && x.trim() != "";

btnSearchForAppointments.addEventListener('click', e => {
    const data = {
        status: /** @type {AppointmentStatus | null} */(selectStatus.value),
        patient: patientSelector.getSelectedFile(),
        from: (inputDesde.valueAsDate ? inputDesde.valueAsDate.toISOString().split("T")[0] : null),
        to: inputHasta.valueAsDate ? inputHasta.valueAsDate.toISOString().split("T")[0] : null,
        q: appointment_q.value
    }
    appointmentQuery
        .filterByDoctor(doctor)
        .filterByAppointmentStatus((data.status && selectStatus.value != "NONE") ? data.status : null)
        .filterByPatient(data.patient)
        .filterByDate(data.from)
        .filterByDateBetween(data.from, data.to)
        .filterByStatus("ONLY_ACTIVE");

    appointmentQuery.search()
        .then(results => {
            console.log(results);
        }).catch(console.error);

    console.log(data);
});

export const load = (d) => {
    doctor = d;
    inputDesde.valueAsDate = new Date();
};

load();