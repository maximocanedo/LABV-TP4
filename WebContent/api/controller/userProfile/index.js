// PENDIENTE: CAMBIAR RUTA /doctors/manage más abajo.
'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import * as users from "./../../actions/users.js";
import * as doctors from "./../../actions/doctors.js";
import { DoctorSelector } from "./../../lib/selectors/DoctorSelector.js";
import { control } from "./../../controller/web.auth.js";

(async () => {
    // @ts-ignore
    window.me = await control(true, []);
})();

const doctorSelector = new DoctorSelector();
doctorSelector.setInitialQuery(new doctors.Query().filterByUnassigned(true));
doctorSelector.getTrigger().classList.add("me-auto");
doctorSelector.getTrigger().classList.remove("mb-3");
document.querySelector(".medicoSelectorHere").prepend(doctorSelector.getTrigger());
const updateDoctorBtn = /** @type {HTMLButtonElement} */(document.getElementById("updateDoctorBtn"));
const btnState = /** @type {HTMLButtonElement} */(document.getElementById("btnState"));
const password1 = /** @type {HTMLInputElement} */(document.getElementById("password1"));
const password2 = /** @type {HTMLInputElement} */(document.getElementById("password2"));
const password0 = /** @type {HTMLInputElement} */(document.getElementById("password0"));
const updateBtn = /** @type {HTMLButtonElement} */(document.getElementById("updateBtn"));
const updateDataBtn = /** @type {HTMLButtonElement} */ (document.getElementById("updateDataBtn"));
const rol_template_select = /** @type {HTMLSelectElement} */(document.getElementById("rol_template_select"));
const rol_template_select_btn = /** @type {HTMLButtonElement} */(document.getElementById("rol_template_select_btn"));
const denyAllbtn = /** @type {HTMLButtonElement} */(document.getElementById("denyAllbtn"));
const btnUpdatePassword = /** @type {HTMLButtonElement} */(document.getElementById("btnUpdatePassword"));
const nameInput = /** @type {HTMLInputElement} */ (document.querySelector("#nameInput"));
const card__changeAccountName = document.querySelector(".card.__changeAccountName");
const card__resetPassword = document.querySelector(".card.__resetPassword");
const card__grantRole = document.querySelector(".card.__grantRole");
const card__assignDoctor = document.querySelector(".card.__assignDoctor");
const card__deletePermissions = document.querySelector(".card.__deletePermissions");
const card__disable = document.querySelector(".card.disable");

rol_template_select.addEventListener("change", (e) => {
    rol_template_select_btn.disabled = rol_template_select.value == "NONE";
});

const getUsernameInPath = () => {
    const sp = new URLSearchParams(window.location.search);
    return (sp.get("u")??"").trim() ?? "";
};

const itsMe = () => getUsernameInPath().trim() == "";
/** @type {(string) => boolean} */
const can = action => me.active && me.access.some(x => x == action);

const canChangeName = () => itsMe() || can(users.PERMIT.UPDATE_USER_DATA);
const canResetPassword = () => itsMe() || can(users.PERMIT.RESET_PASSWORD);
const canAssignDoctor = () => can(users.PERMIT.ASSIGN_DOCTOR);
const canDisable = () => can(users.PERMIT.DELETE_OR_ENABLE_USER);
const canGrant = () => can(users.PERMIT.GRANT_PERMISSIONS);

const shouldUpdateTabBeRemoved = () => !canChangeName() && !canResetPassword() && !canDisable() && canAssignDoctor();

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

/** @type {User | UserMinimalView | IUser | null} */
let user;

let updating = false;


const validatePasswords = () => {
    let pattern = /^(?=.*?[A-ZÑÇ])(?=.*?[a-zñç])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
    let v = password1.value;
    if(pattern.test(v)) {
        return true;
    } else {
        // MOSTRAR MENSAJE ERROR.
        return false;
    }
};

const validateRepeatedPasswords = () => {
    return password1.value == password2.value;
};

rol_template_select_btn.addEventListener("click", async (e) => {
    const v = rol_template_select.value;
    users.grantTemplate(user.username, v)
        .then(ok => {
            console.log(ok);
            loadUserData();
        }).catch(console.error);
});

denyAllbtn.addEventListener("click", async (e) => {
    if(confirm("Vas a deshabilitar todos los permisos del usuario @" + user.username + ". \n¿Seguro que desea continuar?"))
        users.denyAll(user.username)
            .then(ok => {
                console.log("Todos los permisos deshabilitados. ");
                loadUserData();
            }).catch(console.error);
});


let me;


updateDataBtn.addEventListener("click", async (e) => {
    const newName = nameInput.value;
    users.update(user.username, /** @type {any} */ ({ name: newName }))
        .then(updated => {
            user = { ...user, ...updated};
            fillUserData();
        }).catch(console.error);
});
const updateDoctorButtonS = () => {
    updateDoctorBtn.disabled = true;
    updateDoctorBtn.classList.remove("btn-primary");
    updateDoctorBtn.classList.remove("btn-secondary");
    updateDoctorBtn.classList.remove("btn-danger");
    updateDoctorBtn.classList.remove("d-none");
    if(!user.doctor) {
        if(!doctorSelector.getSelectedFile()) {
        } else {
            updateDoctorBtn.disabled = false;
            updateDoctorBtn.classList.add("btn-primary");
            updateDoctorBtn.innerText = "Vincular";            
        }
    } else {
        if(!doctorSelector.getSelectedFile()) {
            updateDoctorBtn.disabled = false;
            updateDoctorBtn.classList.add("btn-danger");
            updateDoctorBtn.innerText = "Desvincular";  
        } else {
            if(doctorSelector.getSelectedFile().id == user.doctor.id || user.doctor.file == doctorSelector.getSelectedFile().file) {
                updateDoctorBtn.disabled = true;
                updateDoctorBtn.classList.add("d-none");
            } else {
                updateDoctorBtn.disabled = false;
            }
            updateDoctorBtn.classList.add("btn-secondary");
            updateDoctorBtn.innerText = "Actualizar";  
        }
    }
};
doctorSelector.getTrigger().addEventListener('change', updateDoctorButtonS);

updateDoctorBtn.addEventListener('click', async (e) => {
    let newDoctor = doctorSelector.getSelectedFile();
    if(newDoctor == null || !newDoctor) 
        // @ts-ignore
        newDoctor = { id: -1, file: -1 };
    users.update(user.username, /** @type {any} */({ doctor: newDoctor }))
        .then(updated => {
            return loadUserData();
        }).catch(console.error).finally(updateDoctorButtonS);
});


const permitCheckbox = (action_name) => {
    /**
     * <div class="list-group-item">
                            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                Default checkbox
                            </label>
                        </div>
     */
    const allowed = [...user.access].some(action => action == action_name);
    const canModify = [...me.access].some(action => action == users.PERMIT.GRANT_PERMISSIONS);
    const row = new ElementBuilder("div")
                    .classList("list-group-item");
    const checkBox = new ElementBuilder("input")
                    .attr("type", "checkbox")
                    .classList("form-check-input", "spaced_chk")
                    .id("action__" + action_name)
                    .appendTo(row.getTarget());
    const lbl = new ElementBuilder("label")
                    .classList("form-check-label")
                    .attr("for", "action__" + action_name)
                    .text(users.permitDocs[action_name])
                    .appendTo(row.getTarget());
    
    /** @type {HTMLInputElement} */
    const chk = /** @type {HTMLInputElement} */(checkBox.getTarget());
    chk.checked = allowed;
    chk.disabled = !canModify;
    chk.addEventListener("change", async (e) => {
        (!chk.checked ? users.denyOne : users.grantOne)(user.username, action_name).then(userPermit => {
            chk.checked = userPermit.allowed;
        }).catch(err => {
            chk.checked = !chk.checked;
        });
    });

    return row;
};

const fillAuthPermits = () => {
    document.querySelector("._permit-container").innerHTML = "";
    Object.keys(users.PERMIT).map(key => {
        const el = permitCheckbox(key).getTarget();
        document.querySelector("._permit-container").append(el);
    });
};

const updatePasswords = async () => {
    const p1 = password1.value;
    const x = {
        vp: validatePasswords(),
        vrp: validateRepeatedPasswords(),
        im: itsMe(),
        ua: user.access.some(action => action == users.PERMIT.RESET_PASSWORD)
    };
    console.log(x);
    if(validatePasswords() && validateRepeatedPasswords() && (itsMe() || user.access.some(action => action == users.PERMIT.RESET_PASSWORD))) {
        return (itsMe() ? users.resetMyPassword(user.username, password0.value, password1.value) : users.resetPassword(user.username, password1.value))
            .then(ok => {
                // Se cambió correctamente. 
                console.log("Contraseña actualizada correctamente. ");
            }).catch(console.error);
    }
};
btnUpdatePassword.addEventListener("click", async (e) => {
    await updatePasswords();
});

const rem = async () => {
    if(user.active) {
        // Deshabilitar.
        const state = await users.disable(user.username);
        if(state) alert("Deshabilitado con éxito. ");
        user.active = state ? false : user.active;
    } else {
        // Habilitar.
        const state = await users.enable(user.username);
        if(state) alert("Habilitado con éxito. ");
        user.active = state ? true : user.active;
    }
    fillUserData();
};

const loadUserData = async () => {
    const u = getUsernameInPath();
    me = await users.myself();
    if(u == "") user = me;
    else user = await users.getUser(u);
    fillUserData();
    if(!itsMe()) document.querySelector(".pass0").remove();
    fillAuthPermits();
    loadSections();
    return user;
};

const loadSections = () => {
    !canChangeName() && card__changeAccountName.remove();
    !canResetPassword() && card__resetPassword.remove();
    !canGrant() && card__grantRole.remove();
    !canGrant() && card__deletePermissions.remove();
    !canDisable() && card__disable.remove();
    !canAssignDoctor() && card__assignDoctor.remove();
    if(shouldUpdateTabBeRemoved()) document.querySelector("#nav-update-info-tab").classList.add("disabled");
};

const fillUserData = () => {

    const dl = /** @type {HTMLDivElement} */ (document.querySelector("#linkedDoctor"));
    dl.innerHTML = '';
    fill("user.name", user.name);
    fill("user.username", "@" + user.username);
    fill("user.active", (user.active ? "Habilitado" : "Deshabilitado"));
    fill("user.active.switchButton", (user.active ? "Deshabilitar" : "Habilitar"));
    doctorSelector.updateSelection(user.doctor?? null);
    if(user._lastOfflineSaved) {
        let d = new Date(user._lastOfflineSaved);
        fill("localState", "Disponible sin conexión.");
        fill("lastTimeLocallyUpdated", d.toLocaleDateString() + " a las " + d.toLocaleTimeString());
    } else {
        fill("localState", "Este registro no está disponible fuera de línea. ");
        fill("lastTimeLocallyUpdated", "-");
    }
    if(user.doctor) {
        const a = new ElementBuilder("a")
                    .text(user.doctor.surname + ", " + user.doctor.name)
                    .href("/medicos/manage?file=" + user.doctor.file);// TODO: Actualizar ruta.
        dl.append(a.getTarget());
    } else {
        dl.innerText = "Esta cuenta no está vinculada a ningún doctor. ";
    }


    btnState.addEventListener("click", async (e) => {
        if(confirm("Estás a punto de deshabilitar esta cuenta de usuario. \n¿Continuar?")) await rem();
    });
};


(async () => {
    await loadUserData();

})();