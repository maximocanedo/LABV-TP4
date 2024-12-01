'use strict';
import { resolveLocalUrl } from "./../../lib/commons.js";
import * as users from "./../../actions/users.js";
import { control } from "./../../controller/web.auth.js";
import { PERMIT } from "./../../actions/users.js";
import { toastAPIErrors } from "./../../actions/commons.js";

(async () => {
    // @ts-ignore
    window.me = await control(false, []);
})();
// @ts-ignore
const form = /** @type {HTMLFormElement} */(document.signup);
// @ts-ignore
const txtUsername = /** @type {HTMLInputElement} */(document.signup.username);
// @ts-ignore
const txtName = /** @type {HTMLInputElement} */(document.signup.name);
// @ts-ignore
const txtPassword = /** @type {HTMLInputElement} */(document.signup.password);
// @ts-ignore
const txtRepeatPassword = /** @type {HTMLInputElement} */(document.signup.repeatPassword);
// @ts-ignore
const role = null; //
const btnCrear = /** @type {HTMLButtonElement} */(document.getElementById("btnCrear"));

const usernameInvalidFeedback = /** @type {HTMLDivElement} */(document.getElementById("usernameInvalidFeedback"));
const nameInvalidFeedback = /** @type {HTMLDivElement} */(document.querySelector("#nameInvalidFeedback"));
const passwordInvalidFeedback = /** @type {HTMLDivElement} */(document.getElementById("passwordInvalidFeedback"));
const passwordRepeatInvalidFeedback = /** @type {HTMLDivElement} */(document.getElementById("passwordRepeatInvalidFeedback"));

const validity = {
    username: false,
    name: false,
    password: false,
    repeat: false,
    role: false
};

form.addEventListener('submit', (e) => {
    e.preventDefault();
    users.signup({
        username: txtUsername.value,
        name: txtName.value,
        password: txtPassword.value,
        doctor: null
    }).then(user => users.login(txtUsername.value, txtPassword.value)).then(response => {
        if(response.ok) window.location.href = resolveLocalUrl("/users/manage/");
        toastAPIErrors(response);
    }).catch(err => {

        toastAPIErrors(err);
    }).finally(() => {
        
    });
});

const certV = () => {
    const { username, name, password, repeat } = validity;
    // console.log({ username, name, password, repeat });
    btnCrear.disabled = !(username && name && password && repeat);
};

/**
 * @param {HTMLElement} el 
 */
const valid = el => {
    el.classList.remove("is-invalid");
    el.classList.add("is-valid");
    certV();
};

/**
 * @param {HTMLElement} el 
 */
const invalid = el => {
    el.classList.add("is-invalid");
    el.classList.remove("is-valid");
    certV();
};

txtUsername.addEventListener("change", async (x) => {
    let exists = false;
    try {
        exists = (await users.existsByUsername(txtUsername.value))?? false;
    } catch(err) {
        exists = false;
    } finally {
        const pattern = /^[a-zA-Z][a-zA-Z0-9._]{4,14}$/;
        const matches = pattern.test(txtUsername.value);
        if(!matches) {
            invalid(txtUsername);
            usernameInvalidFeedback.innerText = 'El nombre de usuario debe comenzar con una letra, puede tener caracteres alfanuméricos, puntos, guiones y tener entre 4 y 14 caracteres. ';
        }
        if(exists) {
            invalid(txtUsername);
            usernameInvalidFeedback.innerText = 'Este nombre de usuario no está disponible. ';
        }
        if(matches && !exists) {
            valid(txtUsername);
            usernameInvalidFeedback.innerText = '';
        }
        validity.username = !exists && matches;
    }
});

const onValidateName = e => {
    validity.name = !(txtName.value.length == 0)
    if(!validity.name) {
        invalid(txtName);
        nameInvalidFeedback.innerText = 'Campo requerido. ';
    } else {
        valid(txtName);
        nameInvalidFeedback.innerText = '';
    }
};

txtName.addEventListener('change', onValidateName);
txtName.addEventListener('blur', onValidateName);

const basicallyCorrect = password => /^(?=.*?[A-ZÑÇ])(?=.*?[a-zñç])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/.test(password);
const hasAnyName = password => password.toLowerCase().includes(txtName.value.toLowerCase()) || password.toLowerCase().includes(txtUsername.value.toLowerCase());

txtPassword.addEventListener('input', e => {
    const b = basicallyCorrect(txtPassword.value);
    const h = hasAnyName(txtPassword.value);
    validity.password = b && !h;
    if(!b) {
        invalid(txtPassword);
        passwordInvalidFeedback.innerText = 'La contraseña debe tener mínimo ocho caracteres, contener caracteres especiales, alfanuméricos, mayúsculas y minúsculas. ';
    }
    if(h) {
        invalid(txtPassword);
        passwordInvalidFeedback.innerText = `No, ${txtName.value}, tu contraseña no puede tener tu nombre ni tu nombre de usuario. `;
    }
    if(validity.password) {
        valid(txtPassword);
        passwordInvalidFeedback.innerText = '';
    }
});

txtRepeatPassword.addEventListener('change', e => {
    validity.repeat = txtRepeatPassword.value == txtPassword.value;
    if(validity.repeat) {
        valid(txtRepeatPassword);
        passwordRepeatInvalidFeedback.innerText = '';
    } else {
        invalid(txtRepeatPassword);
        passwordRepeatInvalidFeedback.innerText = 'Las contraseñas deben coincidir. ';
    }
});
