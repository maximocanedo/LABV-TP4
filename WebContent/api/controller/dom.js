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

    #currentValue;
    #focus = false;

    constructor(/** @type {keyof HTMLElementTagNameMap | String} */ tagNameOrElement) {
        /** @type {HTMLElement} */
        this.element = document.createElement(tagNameOrElement);
        this.on("focus", (ev, el) => {
            this.#focus = true;
        });
        this.on("blur", (ev, el) => {
            this.#focus = false;
        });
    }

    getTarget() {
        return this.element;
    }

    linkValue(store, action, path = this.element.id + "Value") {
        if(path == undefined || path == null || path.trim() == "" || path.trim() == "Value") 
            throw new Error("Must define an id in order to link a store. ");
        const getStoredValue = () => {
            return path.trim().split('.').reduce((acc, part) => acc && acc[part], store.getState());
        };
        store.subscribe(() => {
            const previousValue = this.#currentValue;
            this.#currentValue = getStoredValue();
            if(this.#currentValue != previousValue) {
                if(!this.#focus)
                    this.value(this.#currentValue);
            }
        });
        this.on("input", (ev, el) => {
            store.dispatch({
                type: action,
                // @ts-ignore
                payload: this.element.value
            });
        });
        this.value(getStoredValue());
        return this;
    }

    linkReadableText(store, path) {
        if(path == undefined || path == null || path.trim() == "" || path.trim() == "Value") 
            throw new Error("Must define an id in order to link a store. ");
        const getStoredValue = () => {
            return store.getState()[path.trim()];
        };
        store.subscribe(() => {
            const previousValue = this.#currentValue;
            this.#currentValue = getStoredValue();
            if(this.#currentValue != previousValue) {
                if(!this.#focus)
                    this.text(this.#currentValue);
            }
        });
        this.text(getStoredValue());
        return this;
    }

    build() {
        return this.element;
    }

    type(type) {
        this.element.setAttribute("type", type);
        return this;
    }

    href(href) {
        // @ts-ignore
        this.element.href = href;
        return this;
    }

    attr(attribute, value) {
        this.element.setAttribute(attribute, value);
        return this;
    }

    value(value) {
        // @ts-ignore
        this.element.value = value;
        return this;
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

    change(/** @type {(ev: Event, element: ElementBuilder) => void} */ handler) {
        return this.on("change", handler);
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