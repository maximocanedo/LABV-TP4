'use strict';
import { DoctorSelector } from "./../../../lib/selectors/DoctorSelector.js";
import * as appointments from "./../../../actions/appointments.js";
import { ElementBuilder } from "./../../../controller/dom.js";
import { toastAPIErrors } from "./../../../actions/commons.js";

const button = (selector) => /** @type {HTMLButtonElement} */(document.querySelector(selector));
const input = (selector) => /** @type {HTMLInputElement} */(document.querySelector(selector));
const div = (selector) => /** @type {HTMLDivElement} */(document.querySelector(selector));
const select = selector => /** @type {HTMLSelectElement} */(document.querySelector(selector));

let patient = null;

export const loadMoreAppointments = button("#loadMoreAppointments");
export const btnSearchForAppointments = button("#btnSearchForAppointments");
export const appointment_q = input("#appointment_q");
export const doctorSelector = new DoctorSelector();
export const selectStatus = select("#selectStatus");
export const inputDesde = input("#inputDesde");
export const inputHasta = input("#inputHasta");
export const appointmentQuery = new appointments.Query(appointment_q.value);
export const cln = x => x != null && x != undefined && x.trim() != "";


/**
 * <div class="list-group-item">
                                <div class="row">
                                    <div class="col d-flex justify-content-between align-items-start">
                                        <div>(#239)</div>
                                        <div class="ms-2 me-auto">
                                            <div class="fw-bold">Subheading</div>
                                            Content for list item
                                        </div>
                                        <span class="badge text-bg-primary text-bg-danger rounded-pill">Ausente</span>
                                </div>
                            </div>
 */
/**
 * @param {IAppointment} file 
 */
const toListItem = file => {
    const eC = {
        ABSENT: "text-bg-danger",
        PENDING: "text-bg-secondary",
        PRESENT: "text-bg-success"
    };
    const badge = new ElementBuilder("span").classList("badge", "rounded-pill", eC[file.status])
    .text(/** @type {string} */(file['statusDescription']));
    const nd = new Date(file.date).toLocaleString();
    const sec = new ElementBuilder("span").text(nd);
    // @ts-ignore
    const subheading = new ElementBuilder("div").classList("fw-bold").text(`Dr. ${file.assignedDoctor.surname}, ${file.assignedDoctor.name}`);
    const meAuto = new ElementBuilder("div").classList("ms-2", "me-auto").append(subheading).append(sec);
    const idDiv = new ElementBuilder("div").text(`(#${file.id})`);
    const col = new ElementBuilder("div")
                    .classList("col", "d-flex", "justify-content-between", "align-items-start")
                    .append(idDiv)
                    .append(meAuto)
                    .append(badge);
    /** @type {ElementBuilder} */
    const row = new ElementBuilder("div").classList("row").append(col);
    /** @type {ElementBuilder} */
    const item = new ElementBuilder("div").classList("list-group-item").append(row);
    item.click((ev, el) => {
        console.log(file);
    });
    return item;
};

/** @param {IAppointment[]} results */
const handleResults = (results) => {
    results.map(result => {
        toListItem(result).appendTo(document.querySelector('.appointmentList'));
    });
    if(!results.length) {
        document.querySelector(".appointmentListErr").innerHTML = 'No se han encontrado turnos. ';
    } else {
        loadMoreAppointments.classList.remove("d-none");
    }
};

btnSearchForAppointments.addEventListener('click', e => {
    loadMoreAppointments.classList.add("d-none");
    const data = {
        status: /** @type {AppointmentStatus | null} */(selectStatus.value),
        doctor: doctorSelector.getSelectedFile(),
        from: (inputDesde.valueAsDate ? inputDesde.valueAsDate.toISOString().split("T")[0] : null),
        to: inputHasta.valueAsDate ? inputHasta.valueAsDate.toISOString().split("T")[0] : null,
        q: appointment_q.value
    };
    appointmentQuery
        .filterByPatient(patient)
        .filterByAppointmentStatus((data.status && selectStatus.value != "NONE") ? data.status : null)
        .filterByDoctor(data.doctor)
        .filterByDate(data.from)
        .filterByDateBetween(data.from, data.to)
        .filterByStatus("ONLY_ACTIVE");
    document.querySelector(".appointmentList").innerHTML = '';
    document.querySelector(".appointmentListErr").innerHTML = '';
    appointmentQuery.search().then(handleResults).catch(toastAPIErrors);
});

loadMoreAppointments.addEventListener('click', async (e) => {
    appointmentQuery.next().then(handleResults).catch(toastAPIErrors);
});

export const load = (d) => {
    patient = d;
    inputDesde.valueAsDate = new Date();
};

load();