'use strict';

import { ElementBuilder } from "./../../controller/dom.js";

export const load = () => {
    return "";
    const navbar = new ElementBuilder('nav')
        .classList('navbar', 'navbar-expand-lg', 'bg-body-tertiary');

    const container = new ElementBuilder('div')
        .classList('container-fluid')
        .appendTo(navbar.getTarget());

    const navbarBrand = new ElementBuilder('a')
        .classList('navbar-brand')
        .href('#')
        .text('Administrador')
        .appendTo(container.getTarget());

    const navbarToggler = new ElementBuilder('button')
        .classList('navbar-toggler')
        .type('button')
        .attr('data-bs-toggle', 'collapse')
        .attr('data-bs-target', '#navbarNavAltMarkup')
        .attr('aria-controls', 'navbarNavAltMarkup')
        .attr('aria-expanded', 'false')
        .attr('aria-label', 'Toggle navigation')
        .append(new ElementBuilder('span').classList('navbar-toggler-icon'))
        .appendTo(container.getTarget());

    const navbarCollapse = new ElementBuilder('div')
        .classList('collapse', 'navbar-collapse')
        .id('navbarNavAltMarkup')
        .appendTo(container.getTarget());

    const navLinks = new ElementBuilder('div')
        .classList('navbar-nav')
        .appendTo(navbarCollapse.getTarget());

    const navPacientes = new ElementBuilder('a')
        .classList('nav-link', 'active')
        .id('navPacientes')
        .attr('aria-current', 'page')
        .href('../pacientes/index.html')
        .text('Pacientes')
        .appendTo(navLinks.getTarget());

    const navAsignarTurno = new ElementBuilder('a')
        .classList('nav-link')
        .id('navTurno')
        .href('../appointments/add/index.html')
        .text('Asignar Turno')
        .appendTo(navLinks.getTarget());

    const navMedicos = new ElementBuilder('a')
        .classList('nav-link')
        .id('navMedicos')
        .href('../medicos/index.html')
        .text('Medicos')
        .appendTo(navLinks.getTarget());

    const navInformes = new ElementBuilder('a')
        .classList('nav-link')
        .id('navInformes')
        .href('../informes/index.html')
        .text('Informes')
        .appendTo(navLinks.getTarget());

    document.body.prepend(navbar.build());
};