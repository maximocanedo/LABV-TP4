'use strict';

/**
 * Crear elemento prueba
 * @param {string} name 
 * @param {string} description 
 * @param {(this: HTMLAnchorElement, ev: MouseEvent) => any} onClick 
 * @returns {HTMLDivElement}
 */
export const domTesterElement = (name, description, onClick) => {
    const root = document.createElement("div");
    root.classList.add("option");

    const link = document.createElement("a");
    link.href = "#";
    link.innerText = name;
    link.addEventListener('click', onClick);

    const p = document.createElement("p");
    p.innerText = description;

    root.append(link, p);
    document.querySelector(".test-options").append(root);
    return root;
};