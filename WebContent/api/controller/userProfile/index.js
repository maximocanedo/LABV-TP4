
'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import * as users from "./../../actions/users.js";


const btnState = document.getElementById("btnState");
const password1 = document.getElementById("password1");
const password2 = document.getElementById("password2");
const password0 = document.getElementById("password0");
const updateBtn = document.getElementById("updateBtn");
const rol_template_select = document.getElementById("rol_template_select");
const rol_template_select_btn = document.getElementById("rol_template_select_btn");
const denyAllbtn = document.getElementById("denyAllbtn");
const btnUpdatePassword = document.getElementById("btnUpdatePassword");

const getUsernameInPath = () => {
    const sp = new URLSearchParams(window.location.search);
    return sp.get("u").trim() ?? "";
};

const itsMe = () => getUsernameInPath().trim() == "";

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

/** @type {User | UserMinimalView | IUser} */
let user = null;

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


let me = {};


const permitCheckbox = (action_name) => {
    const allowed = [...user.access].some(action => action == action_name);
    const canModify = [...me.access].some(action => action == users.PERMIT.GRANT_PERMISSIONS);
    const row = new ElementBuilder("div")
                    .classList("row");
    const col = new ElementBuilder("div")
                    .classList("col")
                    .appendTo(row.getTarget());
    const checkBox = new ElementBuilder("input")
                    .attr("type", "checkbox")
                    .id("action__" + action_name)
                    .appendTo(col.getTarget());
    const lbl = new ElementBuilder("label")
                    .attr("for", "action__" + action_name)
                    .text(users.permitDocs[action_name])
                    .appendTo(col.getTarget());
    
    /** @type {HTMLInputElement} */
    const chk = /** @type {HTMLInputElement} */ checkBox.getTarget();
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


const switchViewingAndEditingSections = () => {
    if(!updating && [ ...user.access ].some(action => action == users.PERMIT.UPDATE_USER_DATA)) {
        document.querySelector("section.updating").classList.remove("d-none");
        document.querySelector("section.viewing").classList.add("d-none");
    } else {
        document.querySelector("section.updating").classList.add("d-none");
        document.querySelector("section.viewing").classList.remove("d-none");
    }
}

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
    if(!itsMe()) password0.remove();
    console.log(user);
    fillAuthPermits();
    return user;
};

const fillUserData = () => {
    fill("user.name", user.name);
    fill("user.username", "@" + user.username);
    fill("user.active", (user.active ? "Habilitado" : "Deshabilitado"));
    fill("user.active.switchButton", (user.active ? "Deshabilitar" : "Habilitar"));
    btnState.addEventListener("click", async (e) => {
        await rem();
    });
};


(async () => {
    await loadUserData();
})();