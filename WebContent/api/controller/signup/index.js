'use strict';
import * as users from "./../../actions/users.js";

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

const usernameInvalidFeedback = /** @type {HTMLDivElement} */(document.getElementById("usernameInvalidFeedback"));
const nameInvalidFeedback = /** @type {HTMLDivElement} */(document.querySelector("#nameInvalidFeedback"));
const passwordInvalidFeedback = /** @type {HTMLDivElement} */(document.getElementById("passwordInvalidFeedback"));

const validity = {
    username: false,
    name: false,
    password: false,
    repeat: false,
    role: false
};

/**
 * @param {HTMLElement} el 
 */
const valid = el => {
    el.classList.remove("is-invalid");
    el.classList.add("is-valid");
};

/**
 * @param {HTMLElement} el 
 */
const invalid = el => {
    el.classList.add("is-invalid");
    el.classList.remove("is-valid");
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
        console.log({
            matches, exists
        });
        validity.username = !exists && matches;
    }
});
const onValidateName = e => {
    if(txtName.value.length == 0) {
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
/**
 * 
 * @param {string} password 
 * @returns 
 */
const hasAnyName = password => password.toLowerCase().includes(txtName.value.toLowerCase()) || password.toLowerCase().includes(txtUsername.value.toLowerCase());

txtPassword.addEventListener('input', e => {
    const b = basicallyCorrect(txtPassword.value);
    const h = hasAnyName(txtPassword.value);
    if(!b) {
        invalid(txtPassword);
        passwordInvalidFeedback.innerText = 'La contraseña debe tener mínimo ocho caracteres, contener caracteres especiales, alfanuméricos, mayúsculas y minúsculas. ';
    }
    if(h) {
        invalid(txtPassword);
        passwordInvalidFeedback.innerText = `No, ${txtName.value}, tu contraseña no puede tener tu nombre ni tu nombre de usuario. `;
    }
    if(b && !h) {
        valid(txtPassword);
        passwordInvalidFeedback.innerText = '';
    }
});