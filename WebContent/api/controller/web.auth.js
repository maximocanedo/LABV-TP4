'use strict';
import { CommonModal } from "./../lib/modals.js";
import * as users from "./../actions/users.js";
import { resolveLocalUrl } from "./../lib/commons.js";

/**
 * 
 * @param {boolean} strict ¿Se debe redirigir a la página de login?
 * @param {*} permits Permisos necesarios para entrar.
 * @returns {Promise<IUser | null>}
 */
export const control = async (strict = true, permits = []) => {
    document.querySelectorAll('[signup-link]').forEach((/** @type {HTMLAnchorElement} */el) => {
        el.href = resolveLocalUrl('/users/signup');
        el.innerText = 'Registrate';
    });
    document.querySelectorAll('[login-link]').forEach((/** @type {HTMLAnchorElement} */el) => {
        el.href = resolveLocalUrl('/login');
        el.innerText = 'Iniciar sesión';
    });
    try {
        let me = await users.myself();
        if(permits.length == 0 || me.access.some(action => permits.includes(action))) {
            return me;
        } else {
            let permitsList = permits.map(permit => "\t\t-\t" + users.permitDocs[permit] + ". ").join("\n");
            const win = new CommonModal({ id: "errModalPermits" , scrollable: false});
            win.setTitle("No autorizado. ");
            win.getBody().innerText = ("Necesitás al menos uno de estos permisos para ingresar: \n\n" + permitsList + "\n\nSolicita a un administrador que te conceda alguno de estos permisos para poder ingresar. ");
            win.setMainButtonLabel("");
            win.removeMainButton();
            win.show();
            win.onHide((e) => {
                window.history.back();
                document.body.innerHTML = '';
            });
            
        }
    } catch (err) {
        const win = new CommonModal({ id: "errModal" , scrollable: false});
        win.setTitle("No autenticado");
        win.getBody().innerText = ("Tenés que iniciar sesión para entrar a esta página. ");
        win.setMainButtonLabel("");
        win.removeMainButton();
        if(strict) win.show();
        win.onHide((e) => {
            window.location.href = resolveLocalUrl("/login");
        });
    }
};