'use strict';
import * as appointments from "./../../../actions/appointments.js";
import { DoctorSelector } from "./../../.././lib/selectors/DoctorSelector.js";
import { PatientSelector } from "./../../.././lib/selectors/PatientSelector.js";

const valid = {
    doctor: false,
    patient: false,
    date: false
};

// Doctor
const doctorSelector = new DoctorSelector();
const selectorDoctorValid = document.getElementById("selectorDoctorValid");
const selectorDoctorInvalid = document.getElementById("selectorDoctorInvalid");
// Paciente
const patientSelector = new PatientSelector();
const selectorPatientValid = document.getElementById("selectorPatientValid");
const selectorPatientInvalid = document.getElementById("selectorPatientInvalid");
//
const txtDate = document.getElementById("txtDate");
const txtDateValid = document.getElementById("txtDateValid");
const txtDateInvalid = document.getElementById("txtDateInvalid");
const btnAsignarTurno = document.getElementById("btnAsignarTurno");

document.querySelector(".selectorDoctor").prepend(doctorSelector.getTrigger());
document.querySelector(".selectorPatient").prepend(patientSelector.getTrigger());

// No permite seleccionar minutos, resetea minutos
txtDate.addEventListener("change", () => {
    try {
        // @ts-ignore
        let date = new Date(txtDate.value);
        date.setMinutes(0);
        let isoDateTime = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString();
        // @ts-ignore
        txtDate.value = isoDateTime.split(".")[0];
        validateDate()
    } catch (error) {
        // nada...
    }
});

btnAsignarTurno.addEventListener("click", () => {
    validateDoctor();
    validatePatient();
    validateDate();
    if (valid.doctor && valid.patient && valid.date) {
        // @ts-ignore
        appointments.create({doctor: doctorSelector.getSelectedFile(), patient: patientSelector.getSelectedFile(), date: new Date(txtDate.value).toISOString()})
    }
});

const validateDoctor = () => {
    if(doctorSelector.getSelectedFile() !== null){
        selectorDoctorValid.innerText = "Valido!";
        selectorDoctorValid.classList.remove("d-none");
        selectorDoctorInvalid.classList.add("d-none");
        valid.doctor = true;
        return;
    }
    selectorDoctorValid.classList.remove("d-none");
    selectorDoctorInvalid.innerText = "Invalido! Seleccione un Doctor";
    selectorDoctorInvalid.classList.remove("d-none")
    valid.doctor = false;
};

const validatePatient = () => {
    if(patientSelector.getSelectedFile() !== null){
        selectorPatientValid.innerText = "Valido!";
        selectorPatientValid.classList.remove("d-none");
        selectorPatientInvalid.classList.add("d-none");
        valid.patient = true;
        return;
    }
    selectorDoctorValid.classList.add("d-none");
    selectorPatientInvalid.innerText = "Invalido! Seleccione un Paciente";
    selectorPatientInvalid.classList.remove("d-none");
    valid.patient = false;
};

const validateDate = () => {
    // @ts-ignore
    if (txtDate.value !== "" && isValidDate(txtDate.value)) {
        txtDateValid.innerText = "Valido!";
        txtDateInvalid.innerText = "";
        txtDate.classList.remove("is-invalid")
        txtDate.classList.add("is-valid")
        valid.date = true;
        return;
    }
    txtDateValid.innerText = "";
    txtDateInvalid.innerText = "Invalido";
    txtDate.classList.remove("is-valid")
    txtDate.classList.add("is-invalid")
    valid.date = false
}; 

const isValidDate = (date) => {
    return new Date(date) > new Date();
};