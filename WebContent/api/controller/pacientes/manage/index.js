'use strict';
import { div, button, input } from "./../../../actions/commons.js";
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


btnState.addEventListener('click', async (e) => {
    if(confirm(`Estás a punto de ${patient.active ? "deshabilitar" : "habilitar"} este registro. \n¿Continuar?`)) {
        (patient.active ? patients.disable(patient.id) : patients.enable(patient.id)).then(ok => {
            if(ok) {
                console.log("Listo. ");
                patient.active = !patient.active;
                fillData();
            }
        }).catch(console.error)
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
    }).catch(console.error);
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
    }).catch(console.error);
});


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
    if(patient.phone) {
        sensibleInfoCard.classList.remove("d-none");
        fill("patient.phone", patient.phone);
    }
    if(patient.address) {
        fill("patient.address", patient.address);
    }
    if(patient.localty) {
        fill("patient.localty", patient.localty);
    }
    if(patient.province) {
        fill("patient.province", patient.province);
    }
    if(patient.birth) {
        fill("patient.birth.sql", new Date(patient.birth).toISOString().split("T")[0]);
    }
    if(patient.email) {
        fill("patient.email", patient.email);
    }
    fill("patient.active", patient.active ? "Habilitado" : "Deshabilitado");
    fill("patient.active.switchButton", patient.active ? "Deshabilitar" : "Habilitar");
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

/** @type {Patient} */
let patient;

const hideSensibleDataInit = () => {
    document.querySelectorAll('[sensible-field]').forEach(el => el.classList.add("d-none"));
};

const loadPatientData = async () => {
    hideSensibleDataInit();
    const u = getIdentifier();
    // @ts-ignore
    if(u.dni != "") patient = await patients.findByDNI(u.dni);
    // @ts-ignore
    else if(u.id > 0) patient = await patients.findById(u.id);
    else throw new Error("Must specify ID or FILE URL parameter. ");
    fillData();
    //fillAuthPermits();
    //loadSections();
    return patient;
};

(async () => {
    await loadPatientData();
})();