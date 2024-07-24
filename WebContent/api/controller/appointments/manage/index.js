'use strict';
import * as appointments from "./../../../actions/appointments.js";
import { control } from "./../../../controller/web.auth.js";
import { PERMIT } from "./../../../actions/users.js";
import { resolveLocalUrl } from "./../../../lib/commons.js";
import { button, div, input, textarea, toastAPIErrors, treatAPIErrors } from "./../../../actions/commons.js";

/** @type {IAppointment | null} */
let appointment = null;

const btnState = button("#btnState");
const btnChangeStatus = button('#btnChangeStatus');
const pendienteChk = input('#pendienteChk');
const ausenteChk = input('#ausenteChk');
const presenteChk = input('#presenteChk');
const txtRemarks = textarea('#txtRemarks');

const onChkChange = () => {
    let state = appointment.status;
    txtRemarks.parentElement.classList.add("d-none");
    if(pendienteChk.checked) state = "PENDING";
    if(ausenteChk.checked) state = "ABSENT";
    if(presenteChk.checked) {
        state = "PRESENT";
        txtRemarks.parentElement.classList.remove("d-none");
        return { 
            status: "PRESENT", 
            remarks: txtRemarks.value 
        };
    }
    return { 
        status: state
    };
    
};
pendienteChk.addEventListener('change', onChkChange);
ausenteChk.addEventListener('change', onChkChange);
presenteChk.addEventListener('change', onChkChange);
btnChangeStatus.addEventListener('click', async () => {
    const data = onChkChange();
    console.log(data);
    // @ts-ignore
    appointments.update(appointment.id, data)
        .then(updated => {
            appointment = { ...appointment, ...updated };
            fillData();
        }).catch(toastAPIErrors);
})

btnState.addEventListener('click', async () => {
    if(confirm('Estás a punto de ' + (appointment.active ? "borrar" : "recuperar") + ' este registro. \n¿Continuar?')) {
        (appointment.active ? appointments.disable : appointments.enable)(appointment.id)
            .then(ok => {
                if(ok) {
                    console.log("Operación realizada con éxito. ");
                    appointment.active = !appointment.active;
                }
                else console.error("Error al intentar habilitar o deshabilitar este registro. ");
                fillData();
                updateBtnState();
            }).catch(toastAPIErrors);
    }
});

/**
 * 
 * @param {string} text 
 * @param {string} link 
 * @returns {HTMLAnchorElement}
 */
const a = (text, link) => {
    const x = document.createElement("a");
    x.href = resolveLocalUrl(link);
    x.innerText = text;
    return x;
}

(async () => {
    // @ts-ignore
    window.me = await control(true, [PERMIT.CREATE_DOCTOR]);
})();
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
const getIdInPath = () => {
    const sp = new URLSearchParams(window.location.search);
    return parseInt((sp.get("id")??"0").trim().match(/\d+/)[0]);
};
const updateBtnState = () => {
    if(!appointment) return;

    fill("appointment.active.switchButton", appointment.active ? "Deshabilitar" : "Habilitar");
    btnState.classList.remove("btn-success", "btn-danger");
    btnState.classList.add(appointment.active ? "btn-danger" : "btn-success");
};
const fillData = () => {
    if(appointment == null) return;
    fill("appointment.id", appointment.id);
    div(".patientLinkHere").innerHTML = '';
    div(".doctorLinkHere").innerHTML = '';
    div(".patientLinkHere").append(a(`${appointment.patient.surname}, ${appointment.patient.name}`, `/pacientes/manage/?dni=${appointment.patient.dni}`));
    // @ts-ignore
    div(".doctorLinkHere").append(a(`${(appointment.assignedDoctor??appointment.doctor).surname}, ${(appointment.assignedDoctor??appointment.doctor).name}`, `/medicos/manage/?file=${(appointment.assignedDoctor??appointment.doctor).file}`));
    // @ts-ignore
    fill("appointment.status", appointment.statusDescription?? appointment.status);
    fill("appointment.remarks", appointment.remarks);
    fill("appointment.active", appointment.active ? 'Habilitado' : 'Deshabilitado');
    document.querySelectorAll(".hidable-if-disabled").forEach(el => {
        el.classList.add("d-none");
        if(appointment.active) el.classList.remove("d-none");
    });
    updateBtnState();
    if(appointment._lastOfflineSaved) {
        let d = new Date(appointment._lastOfflineSaved);
        fill("localState", "Disponible sin conexión.");
        fill("lastTimeLocallyUpdated", d.toLocaleDateString() + " a las " + d.toLocaleTimeString());
    } else {
        fill("localState", "Este registro no está disponible fuera de línea. ");
        fill("lastTimeLocallyUpdated", "-");
    }
};


(async () => {
    const id = getIdInPath();
    if(id == 0) {
        throw new Error("Falta parámetro ID o este es inválido. ");
    }
    appointments.findById(id)
        .then(file => {
            appointment = file;
            fillData();
        }).catch(treatAPIErrors);
})();