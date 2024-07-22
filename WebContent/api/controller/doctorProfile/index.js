'use strict';
import { SpecialtySelector } from "./../../lib/selectors/SpecialtySelector.js";
import * as doctors from "./../../actions/doctors.js";
import { ElementBuilder } from "./../../controller/dom.js";

const specialtySelector = new SpecialtySelector();
const updateBasicDataBtn = /** @type {HTMLButtonElement} */(document.getElementById("updateBasicDataBtn"));
updateBasicDataBtn.parentNode.insertBefore(specialtySelector.getTrigger(), updateBasicDataBtn);
const updateSensibleDataBtn =/** @type {HTMLButtonElement} */(document.getElementById("updateSensibleDataBtn"));
const btnState = /** @type {HTMLButtonElement} */(document.getElementById("btnState"));
const daySelect = /** @type {HTMLSelectElement} */(document.getElementById("daySelect"));
const startInput = /** @type {HTMLInputElement} */(document.getElementById("startInput"));
const endInput = /** @type {HTMLInputElement} */(document.getElementById("endInput"));
const endInputText = /** @type {HTMLDivElement} */(document.getElementById("endInputText"));
const addScheduleBtn = /** @type {HTMLButtonElement} */(document.getElementById("addScheduleBtn"));
const scheduleAddingIn = /** @type {HTMLDivElement} */(document.getElementById("scheduleAddingIn"));

const getNextDay = day => {
    const days = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];
    const i = days.indexOf(day);
    if(i > -1) {
        return days[(i + 1) % days.length];
    }
    throw new Error("Día de semana inválido. ");
};

let day = daySelect.value;
let endDay = day;

const handleTimeChange = (e) => {
    resetScheduleAddingErrors();
    day = daySelect.value;
    endDay = day;
    const start = startInput.value;
    const end = endInput.value;
    if(start == '' || end == '' || day == '') {
        addScheduleBtn.disabled = true;
        return;
    }
    const startTime = start.split(":").map(x => parseInt(x) || 0);
    const endTime = end.split(":").map(x => parseInt(x) || 0);
    const minsStartTime = startTime[0] * 60 + startTime[1];
    const minsEndTime = endTime[0] * 60 + endTime[1];

    if(minsEndTime <= minsStartTime) {
        endInputText.innerText = ("El horario termina al día siguiente. ");
        endDay = getNextDay(day);
    } else {
        endInputText.innerText = '';
        endDay = day;
    }

    addScheduleBtn.disabled = false;

};
daySelect.addEventListener('change', handleTimeChange);
startInput.addEventListener('input', handleTimeChange);
endInput.addEventListener('input', handleTimeChange);

const markError = err => {
    scheduleAddingIn.innerText = 'error' in err ? err.error.description : err.message;
    daySelect.classList.add("is-invalid");
    startInput.classList.add("is-invalid");
    endInput.classList.add("is-invalid");
};

const resetScheduleAddingErrors = () => {
    daySelect.classList.remove("is-invalid");
    startInput.classList.remove("is-invalid");
    endInput.classList.remove("is-invalid");
};

addScheduleBtn.addEventListener('click', async (e) => {
    const start = startInput.value;
    const end = endInput.value;
    if(start == '' || end == '' || day == '') {
        addScheduleBtn.disabled = true;
        return;
    }
    /** @type {Schedule} */
    // @ts-ignore
    const schedule = {
        beginDay: day,
        finishDay: endDay,
        // @ts-ignore
        startTime: start + ":00",
        // @ts-ignore
        endTime: end + ":00",
        active: true
    };
    console.log(schedule);
    //return;
    doctors.addSchedule(schedule, doctor.file)
        .then(schedules => {
            doctor = { ...doctor, schedules };
            fillData();
        }).catch(markError);
});


btnState.addEventListener('click', async (e) => {
    if(confirm(`Estás a punto de ${doctor.active ? "deshabilitar" : "habilitar"} este registro. \n¿Continuar?`)) {
        (doctor.active ? doctors.disable(doctor.id) : doctors.enable(doctor.id)).then(ok => {
            if(ok) {
                console.log("Listo. ");
                doctor.active = !doctor.active;
                fillData();
            }
        }).catch(console.error)
    }
});
updateBasicDataBtn.addEventListener('click', async (e) => {
    const name = (/** @type {HTMLInputElement} */(document.getElementById('nameInput'))).value;
    const surname = (/** @type {HTMLInputElement} */(document.getElementById('surnameInput'))).value;
    const specialty = specialtySelector.getSelectedFile();
    doctors.update(doctor.id, { name, surname, specialty })
        .then(doc => {
            // @ts-ignore
            doctor = doc;
            fillData();
            
        }).catch(console.error);
});
updateSensibleDataBtn.addEventListener('click', async (e) => {
    const sex = (/** @type {HTMLSelectElement} */(document.getElementById("sexSelect"))).value;
    const birth = (/** @type {HTMLInputElement} */(document.getElementById("birthInput"))).value;
    const address = (/** @type {HTMLInputElement} */(document.getElementById('addressInput'))).value;
    const localty = (/** @type {HTMLInputElement} */(document.getElementById('localtyInput'))).value;
    const email = (/** @type {HTMLInputElement} */(document.getElementById('emailInput'))).value;
    const phone = (/** @type {HTMLInputElement} */(document.getElementById('phoneInput'))).value;

    

    doctors.update(doctor.id, { sex, birth, address, localty, email, phone })
        .then(doc => {
            // @ts-ignore
            doctor = doc;
            fillData();
        }).catch(console.error);

});

const getFileInPath = () => {
    const sp = new URLSearchParams(window.location.search);
    return (sp.get("file")??"0").trim().match(/\d+/)[0];
};

const getIdInPath = () => {
    const sp = new URLSearchParams(window.location.search);
    return (sp.get("id")??"0").trim().match(/\d+/)[0];
};

const getIdentifier = () => {
    return {
        file: parseInt(getFileInPath()),
        id: parseInt(getIdInPath())
    };
}


let me = null;
/** @type {IDoctor & Doctor} */
let doctor = null;

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

/**
 * @param {Schedule} schedule 
 * @returns 
 */
const printScheduleInline = schedule => {
    let days = { MONDAY: "Lunes", TUESDAY: "Martes", WEDNESDAY: "Miércoles", THURSDAY: "Jueves", FRIDAY: "Viernes", SATURDAY: "Sábado", SUNDAY: "Domingo" };
            let str = days[schedule.beginDay];
            str += " " + schedule.startTime.slice(0,5); //.map(x => (('0'+x).slice(-2))).join(":");
            str += " - ";
            if(schedule.finishDay !== schedule.beginDay) str += days[schedule.finishDay] + " ";
            str += schedule.endTime.slice(0,5); //.map(x => (('0'+x).slice(-2))).join(":");
            return str;
}
/**
 * @param {Schedule} schedule 
 */
const listItemOutOfSchedule = schedule => {
    /**
     * <div class="list-group-item ">
                            <div class="row ">
                                
                            <div class="col col-8 d-flex align-items-center justify-content-start">
                                <span class="">Lunes 18:00 - Martes 06:00</span>
                            </div>
                            <div class="col col-4 d-flex justify-content-end">
                                <button class="btn btn-outline-danger btn-sm">Eliminar</button>
                            </div>
                            </div>
                        </div>
     */
    const scheduleText = new ElementBuilder("span").text(printScheduleInline(schedule));
    const firstCol = new ElementBuilder("div")
                        .classList("col", "col-8", "d-flex", "align-items-center", "justify-content-start")
                        .append(scheduleText);
    const btn = new ElementBuilder("button")
                        .classList("btn", "btn-outline-danger", "btn-sm")
                        .text("Eliminar")
                        .click(async (e, el) => {
                            doctors.deleteSchedule(schedule.id, doctor.file)
                            .then(schedules => {
                                console.log(schedules);
                                // @ts-ignore
                                doctor = { ...doctor, schedules };
                                fillData();
                            }).catch(console.error);
                        });
    const secondCol = new ElementBuilder("div").classList("col", "col-4", "d-flex", "justify-content-end").append(btn);
    const row = new ElementBuilder("div").classList("row").append(firstCol).append(secondCol);
    const item = new ElementBuilder("div").classList("list-group-item").append(row);
    return item.getTarget();
}

const fillData = () => {
    if(doctor == null) return;
    fill("doctor.id", doctor.id);
    fill("doctor.file", doctor.file);
    fill("doctor.name", doctor.name);
    fill("doctor.surname", doctor.surname);
    if(doctor.sex) {
        allowSensible("doctor.sex");
        fill("doctor.sex", doctor.sex);
    }
    if(doctor.birth) {
        allowSensible("doctor.birth");
        fill("doctor.birth", new Date(doctor.birth).toLocaleDateString());
        fill("doctor.birth.sql", doctor.birth)
    }
    if(doctor.address) {
        allowSensible("doctor.address");
        fill("doctor.address", doctor.address);
    }
    if(doctor.localty) {
        allowSensible("doctor.localty");
        fill("doctor.localty", doctor.localty);
    }
    if(doctor.email) {
        allowSensible("doctor.email");
        fill("doctor.email", doctor.email)
    }
    if(doctor.phone) {
        allowSensible("doctor.phone");
        fill("doctor.phone", doctor.phone);
    }
    fill("doctor.specialty.name", doctor.specialty.name);
    fill("doctor.specialty.description", doctor.specialty.description);
    specialtySelector.updateSelection(doctor.specialty);
    //fill("doctor.user", (doctor.user ? )) // Por temas de referencias en JSON, no se incluye esta propiedad en los objetos IDoctor.
    /**
     * Llenar datos de horarios acá.
     */
    if(doctor.schedules) {
        if(!doctor.schedules.length) {
            fill("doctor.scheduleBr", "Sin horarios asignados. ");
        }
        fill("doctor.scheduleBr", doctor.schedules.map(printScheduleInline).join("\n"));
        document.querySelectorAll("._schedule-container").forEach(element => {
            element.innerHTML = '';
            doctor.schedules.map(schedule => {
                element.append(listItemOutOfSchedule(schedule));
            });
        })
    }

    if(!doctor.sex) {
        document.querySelector(".__sensibleInfo").classList.add("d-none");
    } else {
        document.querySelector(".__sensibleInfo").classList.remove("d-none");
    }



    fill("doctor.active", doctor.active ? "Habilitado" : "Deshabilitado");
    fill("doctor.active.switchButton", doctor.active ? "Deshabilitar" : "Habilitar");
    if(doctor._lastOfflineSaved) {
        let d = new Date(doctor._lastOfflineSaved);
        fill("localState", "Disponible sin conexión.");
        fill("lastTimeLocallyUpdated", d.toLocaleDateString() + " a las " + d.toLocaleTimeString());
    } else {
        fill("localState", "Este registro no está disponible fuera de línea. ");
        fill("lastTimeLocallyUpdated", "-");
    }
};

const hideSensibleDataInit = () => {
    document.querySelectorAll('[sensible-field]').forEach(el => el.classList.add("d-none"));
};
const allowSensible = key => document.querySelectorAll(`[sensible-field="${key}"]`).forEach(el => el.classList.remove("d-none"));

const loadDoctorData = async () => {
    hideSensibleDataInit();
    const u = getIdentifier();
    if(u.file > 0) doctor = await doctors.findByFile(u.file);
    else if(u.id > 0) doctor = await doctors.findById(u.id);
    else throw new Error("Must specify ID or FILE URL parameter. ");
    fillData();
    //fillAuthPermits();
    //loadSections();
    return doctor;
};


(async () => {
    await loadDoctorData();

})();