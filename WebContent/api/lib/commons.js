'use strict';

import { resolveUrl } from "./../auth.js";

/**
 * 
 * @param {string} name 
 * @param {string} defaultValue 
 * @returns {string}
 */
export const getParam = 
    (name, defaultValue = "") => new URLSearchParams(window.location.search).get(name) ?? defaultValue;

export const getNumericParam = 
    (name, defaultValue = 0) => {
        const y = parseInt(getParam(name, defaultValue + ""));
        if(isNaN(y)) return defaultValue;
        return y;
};

export const resolveLocalUrl = (relativeUrl) => {
    const PREFIX = "";
    if(window.location.port == '81')
        return resolveUrl(PREFIX + (!relativeUrl.startsWith("/") ? "": "") + relativeUrl);
    return (!relativeUrl.startsWith("/") ? "/": "") + relativeUrl;
};





export const footer = () => {
    
    if(document.querySelectorAll('footer#footer').length > 0) return;

    var node_1 = document.createElement('DIV');
    node_1.setAttribute('class', 'container')
    node_1.setAttribute('id', 'footer');

    var node_2 = document.createElement('FOOTER');
    node_2.setAttribute('class', 'row row-cols-1 row-cols-sm-2 row-cols-md-5 py-5 my-5 border-top');
    node_1.appendChild(node_2);

    var node_3 = document.createElement('DIV');
    node_3.setAttribute('class', 'col mb-3');
    node_2.appendChild(node_3);

    var node_4 = document.createElement('A');
    node_4.setAttribute('href', '/');
    node_4.setAttribute('class', 'd-flex align-items-center mb-3 link-dark text-decoration-none');
    node_3.appendChild(node_4);

    var node_5 = document.createElement('img');
    node_5.setAttribute('width', '40');
    node_5.setAttribute('height', '40');
    node_5.setAttribute("src", "https://iconape.com/wp-content/files/bv/111875/svg/utn-2.svg");
    node_4.appendChild(node_5);

    var node_7 = document.createElement('P');
    node_7.setAttribute('class', 'text-muted');
    node_3.appendChild(node_7);

    var node_8 = document.createTextNode((("Grupo N.º 3 de Laboratorio de Computación V © UTN FRGP 2024")));
    node_7.appendChild(node_8);

    var node_9 = document.createElement('DIV');
    node_9.setAttribute('class', 'col mb-3');
    node_2.appendChild(node_9);

    var node_10 = document.createElement('DIV');
    node_10.setAttribute('class', 'col mb-3');
    node_2.appendChild(node_10);

    var node_11 = document.createElement('H5');
    node_10.appendChild(node_11);

    var node_12 = document.createTextNode((("Usuarios")));
    node_11.appendChild(node_12);

    var node_13 = document.createElement('UL');
    node_13.setAttribute('class', 'nav flex-column');
    node_10.appendChild(node_13);

    var node_14 = document.createElement('LI');
    node_14.setAttribute('class', 'nav-item mb-2');
    node_13.appendChild(node_14);

    var node_15 = document.createElement('A');
    node_15.setAttribute('href', resolveLocalUrl('/users/signup/'));
    node_15.setAttribute('class', 'nav-link p-0 text-muted');
    node_14.appendChild(node_15);

    var node_16 = document.createTextNode((("Registrarse")));
    node_15.appendChild(node_16);

    var node_17 = document.createElement('LI');
    node_17.setAttribute('class', 'nav-item mb-2');
    node_13.appendChild(node_17);

    var node_18 = document.createElement('A');
    node_18.setAttribute('href', resolveLocalUrl('/login/'));
    node_18.setAttribute('class', 'nav-link p-0 text-muted');
    node_17.appendChild(node_18);

    var node_19 = document.createTextNode((("Iniciar sesión")));
    node_18.appendChild(node_19);

    var node_20 = document.createElement('LI');
    node_20.setAttribute('class', 'nav-item mb-2');
    node_13.appendChild(node_20);

    var node_21 = document.createElement('A');
    node_21.setAttribute('href', resolveLocalUrl('/users/manage/'));
    node_21.setAttribute('class', 'nav-link p-0 text-muted');
    node_20.appendChild(node_21);

    var node_22 = document.createTextNode((("Ver usuario actual")));
    node_21.appendChild(node_22);

    var node_23 = document.createElement('LI');
    node_23.setAttribute('class', 'nav-item mb-2');
    node_13.appendChild(node_23);

    var node_24 = document.createElement('A');
    node_24.setAttribute('href', resolveLocalUrl('/users/'));
    node_24.setAttribute('class', 'nav-link p-0 text-muted');
    node_23.appendChild(node_24);

    var node_25 = document.createTextNode((("Buscar")));
    node_24.appendChild(node_25);

    var node_26 = document.createElement('DIV');
    node_26.setAttribute('class', 'col mb-3');
    node_2.appendChild(node_26);

    var node_27 = document.createElement('H5');
    node_26.appendChild(node_27);

    var node_28 = document.createTextNode((("Registros")));
    node_27.appendChild(node_28);

    var node_29 = document.createElement('UL');
    node_29.setAttribute('class', 'nav flex-column');
    node_26.appendChild(node_29);

    var node_30 = document.createElement('LI');
    node_30.setAttribute('class', 'nav-item mb-2');
    node_29.appendChild(node_30);

    var node_31 = document.createElement('A');
    node_31.setAttribute('href', resolveLocalUrl('/pacientes/add/'));
    node_31.setAttribute('class', 'nav-link p-0 text-muted');
    node_30.appendChild(node_31);

    var node_32 = document.createTextNode((("Registrar paciente")));
    node_31.appendChild(node_32);

    var node_33 = document.createElement('LI');
    node_33.setAttribute('class', 'nav-item mb-2');
    node_29.appendChild(node_33);

    var node_34 = document.createElement('A');
    node_34.setAttribute('href', resolveLocalUrl('/pacientes/'));
    node_34.setAttribute('class', 'nav-link p-0 text-muted');
    node_33.appendChild(node_34);

    var node_35 = document.createTextNode((("Buscar pacientes")));
    node_34.appendChild(node_35);

    var node_36 = document.createElement('LI');
    node_36.setAttribute('class', 'nav-item mb-2');
    node_29.appendChild(node_36);

    var node_37 = document.createElement('A');
    node_37.setAttribute('href', resolveLocalUrl('/medicos/add/'));
    node_37.setAttribute('class', 'nav-link p-0 text-muted');
    node_36.appendChild(node_37);

    var node_38 = document.createTextNode((("Registrar médico")));
    node_37.appendChild(node_38);

    var node_39 = document.createElement('LI');
    node_39.setAttribute('class', 'nav-item mb-2');
    node_29.appendChild(node_39);

    var node_40 = document.createElement('A');
    node_40.setAttribute('href', resolveLocalUrl('/medicos/'));
    node_40.setAttribute('class', 'nav-link p-0 text-muted');
    node_39.appendChild(node_40);

    var node_41 = document.createTextNode((("Buscar médicos")));
    node_40.appendChild(node_41);

    var node_42 = document.createElement('DIV');
    node_42.setAttribute('class', 'col mb-3');
    node_2.appendChild(node_42);

    var node_43 = document.createElement('H5');
    node_42.appendChild(node_43);

    var node_44 = document.createTextNode((("Turnos")));
    node_43.appendChild(node_44);

    var node_45 = document.createElement('UL');
    node_45.setAttribute('class', 'nav flex-column');
    node_42.appendChild(node_45);

    var node_46 = document.createElement('LI');
    node_46.setAttribute('class', 'nav-item mb-2');
    node_45.appendChild(node_46);

    var node_47 = document.createElement('A');
    node_47.setAttribute('href', resolveLocalUrl('/appointments/add/'));
    node_47.setAttribute('class', 'nav-link p-0 text-muted');
    node_46.appendChild(node_47);

    var node_48 = document.createTextNode((("Registrar")));
    node_47.appendChild(node_48);

    var node_49 = document.createElement('LI');
    node_49.setAttribute('class', 'nav-item mb-2');
    node_45.appendChild(node_49);

    var node_50 = document.createElement('A');
    node_50.setAttribute('href', resolveLocalUrl('/appointments/'));
    node_50.setAttribute('class', 'nav-link p-0 text-muted');
    node_49.appendChild(node_50);

    var node_51 = document.createTextNode((("Buscar")));
    node_50.appendChild(node_51);


    document.body.append(node_1);
    document.querySelector('header div h1').addEventListener('click', () => {
        resolveLocalUrl('/');
    });

};

(() => {
    footer();
    document.querySelector('header div h1.m-0').addEventListener('click', () => {
        window.location.href = resolveLocalUrl('/');
    });
})();