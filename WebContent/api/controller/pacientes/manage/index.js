'use strict';
import { div, button, input, treatAPIErrors, toastAPIErrors } from "./../../../actions/commons.js";
import * as patients from "./../../../actions/patients.js";
import * as appointments from "./../../../actions/appointments.js";
import { load } from "./events.js";
import * as ap from "./appointments.js";
import { 
    updateBasicDataBtn,
    updateSensibleDataBtn,
    nameInput, 
    surnameInput, 
    sensibleInfoCard,
    birthInput,
    addressInput,
    localtyInput,
    emailInput,
    provinceInput,
    phoneInput,
    btnState
} from "./dom.js";
import { control } from "./../../../controller/web.auth.js";
import { PERMIT } from "./../../../actions/users.js";

(async () => {
    // @ts-ignore
    window.me = await control(true, [PERMIT.READ_PATIENT_PERSONAL_DATA]);
})();

btnState.addEventListener('click', async (e) => {
    if(confirm(`Estás a punto de ${patient.active ? "deshabilitar" : "habilitar"} este registro. \n¿Continuar?`)) {
        (patient.active ? patients.disable(patient.id) : patients.enable(patient.id)).then(ok => {
            if(ok) {
                console.log("Listo. ");
                patient.active = !patient.active;
                fillData();
            }
        }).catch(toastAPIErrors)
    }
});

updateSensibleDataBtn.addEventListener('click', async (e) => {
    // @ts-ignore
    patients.update(patient.id, {
        birth: birthInput.valueAsDate,
        address: addressInput.value,
        localty: localtyInput.value,
        province: provinceInput.value,
        email: emailInput.value,
        phone: phoneInput.value
    }).then(updated => {
        // @ts-ignore
        patient = updated;
        fillData();
    }).catch(toastAPIErrors);
});

updateBasicDataBtn.addEventListener('click', async (e) => {
    // @ts-ignore
    patients.update(patient.id, {
        name: nameInput.value,
        surname: surnameInput.value
    }).then(updated => {
        // @ts-ignore
        patient = updated;
        fillData();
    }).catch(toastAPIErrors);
});

const allowSensible = key => document.querySelectorAll(`[sensible-field="${key}"]`).forEach(el => el.classList.remove("d-none"));

const getDNIInPath = () => {
    const sp = new URLSearchParams(window.location.search);
    return (sp.get("dni")??"").trim();
};

const getIdInPath = () => {
    const sp = new URLSearchParams(window.location.search);
    return (sp.get("id")??"0").trim().match(/\d+/)[0];
};

const getIdentifier = () => {
    return {
        dni: (getDNIInPath()),
        id: parseInt(getIdInPath())
    };
};

const fill = (cname, value) => {
    document.querySelectorAll('[data-field="' + cname + '"]').forEach(element => {
        // @ts-ignore
        element.innerText = value;
    });
    document.querySelectorAll('[data-value="' + cname + '"]').forEach(element => {
        // @ts-ignore
        element.value = value;
    });
};

const fillData = () => {
    fill("patient.id", patient.id);
    fill("patient.name", patient.name);
    fill("patient.surname", patient.surname);
    fill("patient.dni", patient.dni);
    fill("patient.active.switchButton", patient.active ? "Deshabilitar" : "Habilitar");
    btnState.innerText = patient.active ? "Deshabilitar" : "Habilitar";
    if(patient.phone) {
        sensibleInfoCard.classList.remove("d-none");
        allowSensible("patient.phone");
        fill("patient.phone", patient.phone);
    }
    if(patient.address) {
        allowSensible("patient.address");
        fill("patient.address", patient.address);
    }
    if(patient.localty) {
        allowSensible("patient.localty");
        fill("patient.localty", patient.localty);
    }
    if(patient.province) {
        allowSensible("patient.province");
        fill("patient.province", patient.province);
    }
    if(patient.birth) {
        allowSensible("patient.birth");
        fill("patient.birth", new Date(patient.birth).toLocaleDateString());
        fill("patient.birth.sql", new Date(patient.birth).toISOString().split("T")[0]);
    }
    if(patient.email) {
        allowSensible("patient.email");
        fill("patient.email", patient.email);
    }
    fill("patient.active", patient.active ? "Habilitado" : "Deshabilitado");
    document.querySelectorAll(".hidable-if-disabled").forEach(el => {
        el.classList.add("d-none");
        if(patient.active) el.classList.remove("d-none");
    });
    updateBtnState();
    if(patient._lastOfflineSaved) {
        let d = new Date(patient._lastOfflineSaved);
        fill("localState", "Disponible sin conexión.");
        fill("lastTimeLocallyUpdated", d.toLocaleDateString() + " a las " + d.toLocaleTimeString());
    } else {
        fill("localState", "Este registro no está disponible fuera de línea. ");
        fill("lastTimeLocallyUpdated", "-");
    }
    load(patient);
    ap.load(patient);
};

const updateBtnState = () => {
    if(!patient) return;

    fill("patient.active.switchButton", patient.active ? "Deshabilitar" : "Habilitar");
    btnState.classList.remove("btn-success", "btn-danger");
    btnState.classList.add(patient.active ? "btn-danger" : "btn-success");
};

/** @type {Patient} */
let patient;

const hideSensibleDataInit = () => {
    document.querySelectorAll('[sensible-field]').forEach(el => el.classList.add("d-none"));
};

const loadPatientData = async () => {
    hideSensibleDataInit();
    const u = getIdentifier();
    try {
        // @ts-ignore
        if(u.dni != "") patient = await patients.findByDNI(u.dni);
        // @ts-ignore
        else if(u.id > 0) patient = await patients.findById(u.id);
    
    } catch(err) {
        treatAPIErrors(err);
    }
    fillData();
    //fillAuthPermits();
    //loadSections();
    return patient;
};

(async () => {
    await loadPatientData();
})();