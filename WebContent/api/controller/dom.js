'use strict';

export class ElementBuilder {

    /**
     * Crea un ElementBuilder a partir de un elemento existente.
     * @param {HTMLElement} element 
     * @returns {ElementBuilder}
     */
    static from(/** @type {HTMLElement} */ element) {
        let builder = new ElementBuilder(element.tagName);
        builder.element = element;
        return builder;
    }

    constructor(/** @type {keyof HTMLElementTagNameMap | String} */ tagNameOrElement) {
        /** @type {HTMLElement} */
        this.element = document.createElement(tagNameOrElement);
    }

    build() {
        return this.element;
    }

    classList(/** @type {string[]} */ ...tokens) {
        this.element.classList.add(...tokens);
        return this;
    }

    removeClass(/** @type {string} */ token) {
        this.element.classList.remove(token);
        return this;
    }

    id(/** @type {string} */ id) {
        this.element.id = id;
        return this;
    }

    name(/** @type {string} */ name) {
        this.element.setAttribute("name", name);
        return this;
    }

    appendTo(/** @type {string | Element} */ element) {
        (element instanceof Element ? element : document.querySelector(element)).append(this.element);
        return this;
    }

    append(/** @type {Element | ElementBuilder} */x) {
        if(x instanceof ElementBuilder) this.element.append(x.element);
        else this.element.append(x);
        return this;
    }

    text(/** @type {string} */ text) {
        this.element.innerText = text;
        return this;
    }

    html(/** @type {string} */ plainHTML) {
        this.element.innerHTML = plainHTML;
        return this;
    }

    on(/** @type {keyof HTMLElementEventMap} */ eventName, /** @type {(ev: Event, element: ElementBuilder) => void} */ handler) {
        this.element.addEventListener(eventName, (ev) => handler(ev, this));
        return this;
    }

    click(/** @type {(ev: Event, element: ElementBuilder) => void} */ handler) {
        return this.on("click", handler);
    }

    hide() {
        return this.classList("___hiding");
    }

    show() {
        return this.removeClass("___hiding");
    }

    css(/** @type {string} */ key, /** @type {string} */ value) {
        this.element.style[key] = value;
        return this;
    }

    display(/** @type {string} */ value) {
        this.element.style.display = value;
        return this;
    }

}