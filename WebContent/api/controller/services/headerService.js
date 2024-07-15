'use strict';
import { logout } from "./../../actions/users.js";
import { ElementBuilder } from "./../../controller/dom.js";


export const load = () => {
    const navbar = new ElementBuilder('nav')
        .classList('navbar', 'navbar-expand-lg', 'bg-body-tertiary');

    const container = new ElementBuilder('div')
        .classList('container-fluid')
        .appendTo(navbar.getTarget());

    const navbarBrand = new ElementBuilder('a')
        .classList('navbar-brand')
        .href('#')
        .text('Sistema Turnos')
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
        .classList('ms-auto', 'navbar-nav')
        .appendTo(navbarCollapse.getTarget());

    const navLogin = new ElementBuilder('a')
        .classList('nav-link', 'active')
        .id('navLogin')
        .attr('aria-current', 'page')
        .href('./login.html')
        .text('Iniciar Sesion')
        .appendTo(navLinks.getTarget());

    const navRegister = new ElementBuilder('a')
        .classList('nav-link')
        .id('navRegister')
        .href('register.html')
        .text('Registrarse')
        .appendTo(navLinks.getTarget());

    const dropdown = new ElementBuilder('div')
        .classList('dropdown')
        .id('ddMenu')
        .appendTo(navLinks.getTarget());

    const dropdownButton = new ElementBuilder('button')
        .classList('btn', 'btn-secondary', 'dropdown-toggle')
        .type('button')
        .attr('data-bs-toggle', 'dropdown')
        .id('ddMenuButton')
        .attr('aria-expanded', 'false')
        .appendTo(dropdown.getTarget());;

    const dropdownMenu = new ElementBuilder('ul')
        .classList('dropdown-menu')
        .appendTo(dropdown.getTarget());

    const logoutButton = new ElementBuilder('a')
        .classList('nav-link')
        .id('navLogout')
        .href('javascript:void(0);')
        .click(logout)
        .text('Logout');

    const dropdownItem = new ElementBuilder('li')
        .append(logoutButton)
        .appendTo(dropdownMenu.getTarget());

    // Ensamblar todo el menú desplegable
    //dropdownMenu.append(dropdownItem);
    //dropdown.append(dropdownButton).append(dropdownMenu);
    //navLinks.append(navLogin).append(navRegister).append(dropdown);
    //navbarCollapse.append(navLinks);
    //container.append(navbarBrand).append(navbarToggler).append(navbarCollapse);
    //navbar.append(container);

    document.body.prepend(navbar.build());
};