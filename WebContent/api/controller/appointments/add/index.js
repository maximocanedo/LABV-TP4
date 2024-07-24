'use strict';
import { DoctorSelector } from "../../../lib/selectors/DoctorSelector.js";
import { PatientSelector } from "../../../lib/selectors/PatientSelector.js";
import { SpecialtySelector } from "../../../lib/selectors/SpecialtySelector.js";
import * as appointments from "../../../actions/appointments.js";
import * as doctors from "../../../actions/doctors.js";
import { div, button, input, select, toastAPIErrors, treatAPIErrors } from "./../../../actions/commons.js";
import { control } from "./../../../controller/web.auth.js";
import { PERMIT } from "./../../../actions/users.js";
import { resolveLocalUrl } from "../../../lib/commons.js";
/** @type { IUser } */
let me;
(async () => {
    /** @type { IUser } */
    // @ts-ignore
    me = await control(true, []);
    if(!me.access.some(action => action == PERMIT.CREATE_APPOINTMENT) && me.doctor) {
        doctorSelector.updateSelection(me.doctor);
        doctorSelector.getTrigger().style.opacity = "0.5";
        doctorSelector.getTrigger().querySelectorAll("button").forEach(el => {
            el.disabled = true;
            el.classList.add("disabled");
        });
        specialtySelector.updateSelection(me.doctor.specialty);
        specialtySelector.getTrigger().querySelectorAll("button").forEach(el => {
            el.disabled = true;
            el.classList.add("disabled");
        });
        specialtySelector.getTrigger().style.opacity = "0.5";
    }
})();
const valid = {
    doctor: false,
    patient: false,
    specialty: false,
    hora: false,
    fecha: false
};

const btnAsignarTurno = /** @type {HTMLButtonElement} */(document.getElementById("btnAsignarTurno"));


// Doctor
const dSelector = div(".selectorDoctor");
const doctorSelector = new DoctorSelector();
dSelector.prepend(doctorSelector.getTrigger());
const selectorDoctorValid = document.getElementById("selectorDoctorValid");
const selectorDoctorInvalid = document.getElementById("selectorDoctorInvalid");

// Especialidad
const sSelector = div(".specialtySelector");
const specialtySelector = new SpecialtySelector();
sSelector.prepend(specialtySelector.getTrigger())
const specialtySelectorValid = document.getElementById("specialtySelectorValid");
const specialtySelectorInvalid = document.getElementById("specialtySelectorInvalid");

// Paciente
const pSelector = div(".selectorPatient");
const patientSelector = new PatientSelector();
pSelector.prepend(patientSelector.getTrigger());
const selectorPatientValid = document.getElementById("selectorPatientValid");
const selectorPatientInvalid = document.getElementById("selectorPatientInvalid");

patientSelector.getTrigger().addEventListener('change', () => {
    const file = patientSelector.getSelectedFile();
    if(file == null || !file.active) {
        writeValidityOfPatient(false, "Seleccione un paciente. ");
    } else {
        writeValidityOfPatient(true, "");
    }
})


const selectorDateValid = document.getElementById("selectorDateValid");
const selectorDateInvalid = document.getElementById("selectorDateInvalid");


const selectorTimeValid = document.getElementById("selectorTimeValid");
const selectorTimeInvalid = document.getElementById("selectorTimeInvalid");

const fecha = select("#fecha");
const hora = select("#hora");
const nextMonth = button("#nextMonth");
const prevMonth = button("#prevMonth");

let date = new Date();

specialtySelector.getTrigger().addEventListener('change', e => {
    const selected = specialtySelector.getSelectedFile();
    if(selected == null || (doctorSelector.getSelectedFile() != null && doctorSelector.getSelectedFile().specialty.id != selected.id)) {
        doctorSelector.updateSelection(null);
    }
    if(selected == null) {
        writeValidityOfSpecialty(false, "Seleccione una especialidad. ");
        return;
    }
    writeValidityOfSpecialty(true, "");
    doctorSelector.setInitialQuery(new doctors.Query().filterBySpecialty(selected.id));
});

const handlerForTime = async (e) => {
    const date = fecha.value;
    if(!date || date.trim() == "") {
        writeValidityOfDate(false, "Fecha inválida. ");
        return;
    } writeValidityOfDate(true, "");
    const schedules = await appointments.getAvailableSchedules(doctorSelector.getSelectedFile().file, new Date(date));
    hora.innerHTML = '';
    if(schedules.length == 0) {
        writeValidityOfTime(false, "No hay horarios disponibles este día. ");
        return;
    } else writeValidityOfTime(true, "");
    schedules.map((schedule, index) => {
        const x = document.createElement("option");
        x.value = schedule;
        x.text = schedule;
        if(index == 0) x.selected = true;
        hora.append(x);
    });

};

hora.addEventListener('change', () => {
    if(hora.value.trim() == "") {
        writeValidityOfTime(false, "Horario inválido. ");
    } else {
        writeValidityOfTime(true, "");
    }
});

const writeValidityOfDoctor = (v, mes) => {
    dSelector.querySelector(".input-group .form-control").classList.remove('is-valid');
    dSelector.querySelector(".input-group .form-control").classList.remove('is-invalid');
    dSelector.querySelector(".input-group").classList.remove('is-valid');
    dSelector.querySelector(".input-group").classList.remove('is-invalid');
    selectorDoctorValid.innerText = '';
    selectorDoctorInvalid.innerText = '';
    valid.doctor = v;
    if(v) {
        dSelector.querySelector(".input-group .form-control").classList.add('is-valid');
        dSelector.querySelector(".input-group").classList.add('is-valid');
        selectorDoctorValid.innerText = mes;
    } else {
        dSelector.querySelector(".input-group .form-control").classList.add('is-invalid');
        dSelector.querySelector(".input-group").classList.add('is-invalid');
        selectorDoctorInvalid.innerText = mes;
    }
    updateBtnAsignarTurnoState();
};

const writeValidityOfPatient = (v, mes) => {
    pSelector.querySelector(".input-group .form-control").classList.remove('is-valid');
    pSelector.querySelector(".input-group .form-control").classList.remove('is-invalid');
    pSelector.querySelector(".input-group").classList.remove('is-valid');
    pSelector.querySelector(".input-group").classList.remove('is-invalid');
    selectorPatientValid.innerText = '';
    selectorPatientInvalid.innerText = '';
    valid.patient = v;
    if(v) {
        pSelector.querySelector(".input-group .form-control").classList.add('is-valid');
        pSelector.querySelector(".input-group").classList.add('is-valid');
        selectorPatientValid.innerText = mes;
    } else {
        pSelector.querySelector(".input-group .form-control").classList.add('is-invalid');
        pSelector.querySelector(".input-group").classList.add('is-invalid');
        selectorPatientInvalid.innerText = mes;
    }
    updateBtnAsignarTurnoState();
};

const writeValidityOfSpecialty = (v, mes) => {
    sSelector.querySelector(".input-group .form-control").classList.remove('is-valid');
    sSelector.querySelector(".input-group .form-control").classList.remove('is-invalid');
    sSelector.querySelector(".input-group").classList.remove('is-valid');
    sSelector.querySelector(".input-group").classList.remove('is-invalid');
    specialtySelectorValid.innerText = '';
    specialtySelectorInvalid.innerText = '';
    valid.specialty = v;
    if(v) {
        sSelector.querySelector(".input-group .form-control").classList.add('is-valid');
        sSelector.querySelector(".input-group").classList.add('is-valid');
        specialtySelectorValid.innerText = mes;
    } else {
        sSelector.querySelector(".input-group .form-control").classList.add('is-invalid');
        sSelector.querySelector(".input-group").classList.add('is-invalid');
        specialtySelectorInvalid.innerText = mes;
    }
    updateBtnAsignarTurnoState();
};

const writeValidityOfDate = (v, mes) => {
    fecha.classList.remove('is-valid');
    fecha.classList.remove('is-invalid');
    selectorDateInvalid.innerText = '';
    selectorDateValid.innerText = '';
    valid.fecha = v;
    if(v) {
        fecha.classList.add('is-valid');
        selectorDateValid.innerText = mes;
    } else {
        fecha.classList.add('is-invalid');
        selectorDateInvalid.innerText = mes;
    }
    updateBtnAsignarTurnoState();
}

const writeValidityOfTime = (v, mes) => {
    hora.classList.remove('is-valid');
    hora.classList.remove('is-invalid');
    selectorTimeInvalid.innerText = '';
    selectorTimeValid.innerText = '';
    valid.hora = v;
    if(v) {
        hora.classList.add('is-valid');
        selectorTimeValid.innerText = mes;
    } else {
        hora.classList.add('is-invalid');
        selectorTimeInvalid.innerText = mes;
    }
    updateBtnAsignarTurnoState();
}

const handleDoctorChange = async (resetDate = true) => {
    if(resetDate) date = new Date();
    if(doctorSelector.getSelectedFile() == null) {
        writeValidityOfDoctor(false, "Seleccione un médico. ");
        return;
    } else {
        writeValidityOfDoctor(true, "");
    }
    if(doctorSelector.getSelectedFile().schedules.length == 0) {
        writeValidityOfDoctor(false, "Este médico no tiene horarios registrados. ");
    }
    specialtySelector.updateSelection(doctorSelector.getSelectedFile().specialty);
    const dates = await appointments.getAvailableDates(doctorSelector.getSelectedFile().file, date);
    fecha.innerHTML = '';
    if(dates.length == 0) {
        writeValidityOfDate(false, "No hay fechas disponibles este mes. ");
        nextMonth.disabled = true;
        return;
    } else {
        nextMonth.disabled = false;
    }
    dates.map(d => {
        const x = document.createElement("option");
        x.value = d;
        x.text = d;
        fecha.append(x);
    });
    await handlerForTime(date);
    updateBtnAsignarTurnoState();

};
doctorSelector.getTrigger().addEventListener('change', async () => {
    await handleDoctorChange();
    updateBtnAsignarTurnoState();
});

prevMonth.addEventListener('click', async () => {
    if(new Date().getMonth() == date.getMonth() && new Date().getFullYear() == date.getFullYear()) return;
    const actualMonth = (date.getMonth());
    date.setMonth((actualMonth - 1) % 12);
    date.setDate(1);
    date.setFullYear(date.getFullYear() + Math.floor((actualMonth - 1) / 12));
    await handleDoctorChange(false);
    updateBtnAsignarTurnoState();
});
nextMonth.addEventListener('click', async () => {
    const actualMonth = (date.getMonth());
    date.setMonth((actualMonth + 1) % 12);
    date.setDate(1);
    date.setFullYear(date.getFullYear() + Math.floor((actualMonth + 1) / 12));
    await handleDoctorChange(false);
    updateBtnAsignarTurnoState();
});

fecha.addEventListener('change', handlerForTime);

const fechasNoDisponibles = () => {

}

const updateBtnAsignarTurnoState = () => {
    document.querySelector("#selectorDateWrapper").classList.remove("d-none");
    document.querySelector("#selectorTimeWrapper").classList.remove("d-none");
    if(!valid.doctor) {
        document.querySelector("#selectorDateWrapper").classList.add("d-none");
        document.querySelector("#selectorTimeWrapper").classList.add("d-none");
    }
    if(new Date().getMonth() == date.getMonth() && new Date().getFullYear() == date.getFullYear()) {
        prevMonth.classList.add("d-none");
    } else {
        prevMonth.classList.remove("d-none");
    }
    btnAsignarTurno.disabled = !Object.keys(valid).every(key => valid[key]);
    console.log(valid);
}
btnAsignarTurno.addEventListener("click", () => {
    const d = new Date(fecha.value);
    const h = hora.value.split(":").map(x => parseInt(x));
    d.setHours(h[0]);
    d.setMinutes(h[1]);
    const data = { 
        doctor: doctorSelector.getSelectedFile(), 
        patient: patientSelector.getSelectedFile(), 
        date: d
    };
    if (Object.keys(valid).every(key => valid[key])) {
        console.log(data);
        // @ts-ignore
        appointments.create(data)
            .then(created => {
                window.location.href = resolveLocalUrl('/appointments/manage/?id=' + created.id);
                console.log(created);
            }).catch(toastAPIErrors);
    }
});

const validateDoctor = () => {
    console.log("Working")
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
        selectorPatientValid.classList.add("d-none");
        selectorPatientInvalid.innerText = "Invalido! Seleccione un Paciente";
        selectorPatientInvalid.classList.remove("d-none");
        valid.patient = false;
    }
};

const validateEspecialidad = () => {

    if(specialtySelector.getSelectedFile() !== null){
        specialtySelectorValid.innerText = "Valido!";
        specialtySelectorValid.classList.remove("d-none");
        specialtySelectorInvalid.classList.add("d-none");
        valid.specialty = true;
    } else {
        selectorDoctorValid.classList.add("d-none");
        specialtySelectorInvalid.innerText = "Invalido! Seleccione una Especialidad";
        specialtySelectorInvalid.classList.remove("d-none")
        valid.specialty = false;
    }
};