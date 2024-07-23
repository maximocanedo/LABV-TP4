'use strict';
'use strict';
import { DoctorSelector } from "../../../lib/selectors/DoctorSelector.js";
import * as appointments from "../../../actions/appointments.js";
import * as doctors from "../../../actions/doctors.js";
import { SpecialtySelector } from "../../../lib/selectors/SpecialtySelector.js";
import { div, button, input, select } from "./../../../actions/commons.js";

export const mselector = div(".mselector");
export const sselector = div(".sselector");
export const specialtyS = new SpecialtySelector();
sselector.append(specialtyS.getTrigger());
export const doctorS = new DoctorSelector();
mselector.append(doctorS.getTrigger());
export const fecha = select("#fecha");
export const hora = select("#hora");
export const nextMonth = button("#nextMonth");

const date = new Date();
specialtyS.getTrigger().addEventListener('change', e => {
    const selected = specialtyS.getSelectedFile();
    doctorS.setInitialQuery(new doctors.Query().filterBySpecialty(selected.id));
});
const handleDoctorChange = async () => {
    if(doctorS.getSelectedFile() == null) return;
    const dates = await appointments.getAvailableDates(doctorS.getSelectedFile().file, date);
    fecha.innerHTML = '';
    if(dates.length == 0) {
        console.warn("No hay más fechas disponibles este mes. ");
        return;
    }
    dates.map(d => {
        const x = document.createElement("option");
        x.value = d;
        x.text = d;
        fecha.append(x);
    });
    console.log(dates);
    console.log(date);
    await handlerForTime(date);

};
doctorS.getTrigger().addEventListener('change', handleDoctorChange);
nextMonth.addEventListener('click', async () => {
    const actualMonth = (date.getMonth())
    date.setMonth((actualMonth + 1) % 12);
    date.setDate(1);
    date.setFullYear(date.getFullYear() + Math.floor((actualMonth + 1) / 12));
    await handleDoctorChange();
});
const handlerForTime = async (e) => {
    const date = fecha.value;
    const schedules = await appointments.getAvailableSchedules(doctorS.getSelectedFile().file, new Date(date));
    hora.innerHTML = '';
    console.log(schedules);
    schedules.map(schedule => {
        const x = document.createElement("option");
        x.value = schedule;
        x.text = schedule;
        hora.append(x);
    });
};
fecha.addEventListener('change', handlerForTime);