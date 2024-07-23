'use strict';
import { DoctorSelector } from "../../../lib/selectors/DoctorSelector.js";
import * as appointments from "../../../actions/appointments.js";
import { div, button, input, select } from "./../../../actions/commons.js";
import { doctorSelector } from "./appointments.js";

export const nameInput = button("#nameInput");
export const surnameInput = button("#surnameInput");
export const updateBasicDataBtn = button("#updateBasicDataBtn");
export const birthInput = input("#birthInput");
export const addressInput = input("#addressInput");
export const localtyInput = input("#localtyInput");
export const emailInput = input("#emailInput");
export const provinceInput = select("#provinceInput");
export const phoneInput = input("#phoneInput");
export const updateSensibleDataBtn = button("#updateSensibleDataBtn");
export const btnState = button("#btnState");
export const sensibleInfoCard = div(".__sensibleInfo");
document.querySelector(".pSelectorWrapper").append(doctorSelector.getTrigger());



// Prueba
export const mselector = div(".mselector");
export const doctorS = new DoctorSelector();
mselector.append(doctorS.getTrigger());


doctorS.getTrigger().addEventListener('change', e => {
    const date = new Date();
    appointments.getAvailableDates(doctorS.getSelectedFile().file, date);
    appointments.getAvailableSchedules(doctorS.getSelectedFile().file, date);
});




