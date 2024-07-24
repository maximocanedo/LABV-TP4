'use strict';
import { DoctorSelector } from "../../../lib/selectors/DoctorSelector.js";
import { PatientSelector } from "../../../lib/selectors/PatientSelector.js";
import { SpecialtySelector } from "../../../lib/selectors/SpecialtySelector.js";
import * as appointments from "../../../actions/appointments.js";
import * as doctors from "../../../actions/doctors.js";
import { div, button, input, select } from "./../../../actions/commons.js";

const valid = {
    doctor: false,
    patient: false,
    especilidad: false,
};

const btnAsignarTurno = document.getElementById("btnAsignarTurno")


// Doctor
const dSelector = div(".selectorDoctor");
const doctorSelector = new DoctorSelector();
dSelector.append(doctorSelector.getTrigger());
const selectorDoctorValid = document.getElementById("selectorDoctorValid");
const selectorDoctorInvalid = document.getElementById("selectorDoctorInvalid");

// Especialidad
const sSelector = div(".specialtySelector");
const specialtySelector = new SpecialtySelector();
sSelector.append(specialtySelector.getTrigger())
const specialtySelectorValid = document.getElementById("specialtySelectorValid");
const specialtySelectorInvalid = document.getElementById("specialtySelectorInvalid");

// Paciente
const pSelector = div(".selectorPatient");
const patientSelector = new PatientSelector();
pSelector.append(patientSelector.getTrigger());
const selectorPatientValid = document.getElementById("selectorPatientValid");
const selectorPatientInvalid = document.getElementById("selectorPatientInvalid");

const fecha = select("#fecha");
const hora = select("#hora");
const nextMonth = button("#nextMonth");

const date = new Date();

specialtySelector.getTrigger().addEventListener('change', e => {
    const selected = specialtySelector.getSelectedFile();
    doctorSelector.setInitialQuery(new doctors.Query().filterBySpecialty(selected.id));
});

const handleDoctorChange = async () => {
    if (doctorSelector.getSelectedFile() == null) return;
    const dates = await appointments.getAvailableDates(doctorSelector.getSelectedFile().file, date);
    fecha.innerHTML = '';
    if (dates.length == 0) {

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
doctorSelector.getTrigger().addEventListener('change', handleDoctorChange);

nextMonth.addEventListener('click', async () => {
    const actualMonth = (date.getMonth())
    date.setMonth((actualMonth + 1) % 12);
    date.setDate(1);
    date.setFullYear(date.getFullYear() + Math.floor((actualMonth + 1) / 12));
    await handleDoctorChange();
});

const handlerForTime = async (e) => {
    const date = fecha.value;
    const schedules = await appointments.getAvailableSchedules(doctorSelector.getSelectedFile().file, new Date(date));
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

const fechasNoDisponibles = () => {

}

btnAsignarTurno.addEventListener("click", () => {
    validateDoctor();
    validatePatient();
    validateEspecialidad();
    if (valid.doctor && valid.patient) {
        // @ts-ignore
        appointments.create({ doctor: doctorSelector.getSelectedFile(), patient: patientSelector.getSelectedFile(), date: date })
    }
});

const validateDoctor = () => {
    if (doctorSelector.getSelectedFile() !== null) {
        selectorDoctorValid.innerText = "Valido!";
        selectorDoctorValid.classList.remove("d-none");
        selectorDoctorInvalid.classList.add("d-none");
        valid.doctor = true;
    } else {
        selectorDoctorValid.classList.add("d-none");
        selectorDoctorInvalid.innerText = "Invalido! Seleccione un Doctor";
        selectorDoctorInvalid.classList.remove("d-none")
        valid.doctor = false;
    }
};

const validatePatient = () => {
    if (patientSelector.getSelectedFile() !== null) {
        selectorPatientValid.innerText = "Valido!";
        selectorPatientValid.classList.remove("d-none");
        selectorPatientInvalid.classList.add("d-none");
        valid.patient = true;
    } else {
        selectorDoctorValid.classList.add("d-none");
        selectorPatientInvalid.innerText = "Invalido! Seleccione un Paciente";
        selectorPatientInvalid.classList.remove("d-none");
        valid.patient = false;
    }
};

const validateEspecialidad = () => {
    if (specialtySelector.getSelectedFile() !== null) {
        specialtySelectorValid.innerText = "Valido!";
        specialtySelectorValid.classList.remove("d-none");
        specialtySelectorInvalid.classList.add("d-none");
        valid.especilidad = true;
    } else {
        selectorDoctorValid.classList.add("d-none");
        specialtySelectorInvalid.innerText = "Invalido! Seleccione una Especialidad";
        specialtySelectorInvalid.classList.remove("d-none")
        valid.especilidad = false;
    }
};