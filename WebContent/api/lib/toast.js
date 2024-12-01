'use strict';
import bootstrap from "./bootstrap/bootstrap.bundle.js"

export class Toast {
    static TEXT = "Example Text";

    /** @type {HTMLDivElement} */ #toast;
    /** @type {HTMLDivElement} */ #content;
    /** @type {HTMLDivElement} */ #body;
    /** @type {HTMLButtonElement} */ #closeButton;
    /** @type {Text} */ #mainTextNode;
    /** @type {string} */ #id; 
    /** @type {boolean} */ #initialized = false;
    /** @type {boolean} */ #warnColor = false;
    /** @type {bootstrap.Modal} */ #bootstrap;

    #setInitializedState(x) { this.#initialized = x; }
    isInitialized() { return this.#initialized; }
    
    #initializeDOM() {
        this.#toast = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#content = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#body = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#mainTextNode = document.createTextNode(Toast.TEXT);
        this.#closeButton = /** @type {HTMLButtonElement} */(document.createElement('BUTTON'));
    }

    getWrapper() { return this.#bootstrap; }
    #initWrapper() {
        this.#bootstrap = new bootstrap.Toast(this.#toast, {});
    }

    getText() { return this.#mainTextNode.wholeText; } 
    setText(text) { this.#mainTextNode.nodeValue = text; return this; }

    getWarnColor() { return this.#warnColor; } 
    setWarnColor(value) { 
        if (!value) {
            this.#toast.classList.remove("text-bg-danger");
            this.#toast.classList.add("text-bg-success");
        } else {
            this.#toast.classList.remove("text-bg-success");
            this.#toast.classList.add("text-bg-danger");
        }
        this.#warnColor = value; 
        return this; 
    }

    getId() { return this.#id; }

    #setId(id) { 
        this.#id = id; 
        this.#toast.setAttribute('id', this.getId());
        return this; 
    }

    #init(id) {
        this.#initializeDOM();
        this.#setId(id);
        
        this.#toast.setAttribute('class', 'toast align-items-center border-0 position-absolute bottom-0 end-0 m-3');
        this.#toast.setAttribute('tabindex', '-1');
        this.#toast.setAttribute('role', 'alert');
        this.#toast.setAttribute('aria-live', 'assertive');
        this.#toast.setAttribute('aria-atomic', 'true');

        this.#content.setAttribute('class', 'd-flex');

        this.#body.setAttribute('class', 'toast-body');
        this.#body.appendChild(this.#mainTextNode);
        this.#content.appendChild(this.#body);
        
        this.#closeButton.setAttribute('class', 'btn-close btn-close-white me-2 m-auto');
        this.#closeButton.setAttribute('type', 'button');
        this.#closeButton.setAttribute('data-bs-dismiss', 'toast');
        this.#closeButton.setAttribute('aria-label', 'Close');
        this.#content.appendChild(this.#closeButton);
        
        this.#toast.appendChild(this.#content);

        this.#setInitializedState(true);

        document.body.append(this.#toast);

        this.#initWrapper();
    }

    constructor({id}, text = "") {
        this.#init(id);
        this.setText(text);
    }

    show() {this.getWrapper().show();}
    hide() {this.getWrapper().hide();}
}