'use strict';

/**
 * Crear una sección que se puede minimizar o expandir
 * @param {string} title - Título de la sección
 * @returns {HTMLDivElement} - Elemento de la sección
 */
export const domSection = (title) => {
    const section = document.createElement("div");
    section.classList.add("section");

    const header = document.createElement("div");
    header.classList.add("section-header");

    const t = document.createElement("h3");
    t.innerText = title;
    
    header.append(t);

    const toggleButton = document.createElement("a");
    toggleButton.innerText = "Expandir";
    toggleButton.href = "#";
    toggleButton.addEventListener("click", () => {
        /** @type {HTMLDivElement} */
        const content = section.querySelector(".section-content");
        if (content.style.display === "none") {
            content.style.display = "block";
            toggleButton.innerText = "Minimizar";
        } else {
            content.style.display = "none";
            toggleButton.innerText = "Expandir";
        }
    });

    const content = document.createElement("div");
    content.classList.add("section-content");
    content.style.display = "none";

    header.appendChild(toggleButton);
    section.append(header, content);

    document.querySelector(".test-sections").appendChild(section);
    return section;
};

/**
 * Crear elemento prueba
 * @param {string} name 
 * @param {string} description 
 * @param {(this: HTMLAnchorElement, ev: MouseEvent) => any} onClick 
 * @param {HTMLDivElement} section - Sección a la que añadir el elemento
 * @returns {HTMLDivElement}
 */
export const domTesterElement = (name, description, onClick, section) => {
    const root = document.createElement("div");
    root.classList.add("option");

    const link = document.createElement("a");
    link.href = "#";
    link.innerText = name;
    link.addEventListener('click', onClick);

    const p = document.createElement("p");
    p.innerText = description;

    root.append(link, p);
    section.querySelector(".section-content").append(root);
    return root;
};
