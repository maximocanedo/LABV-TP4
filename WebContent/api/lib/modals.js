'use strict';
import bootstrap from "./bootstrap/bootstrap.bundle.js";

export class CommonModal {

    static CLOSE_BUTTON_LABEL_DEFAULT = "Close";
    static MAIN_BUTTON_LABEL_DEFAULT = "Save changes";
    static DEFAULT_TITLE = "Modal title";

    /** @type {HTMLDivElement} */ #modalFade;
    /** @type {HTMLDivElement} */ #dialog;
    /** @type {HTMLDivElement} */ #modalContent;
    /** @type {HTMLDivElement} */ #header;
    /** @type {HTMLHeadingElement} */ #titleElement;
    /** @type {Text} */ #titleTextNode;
    /** @type {HTMLButtonElement} */ #closeButton;
    /** @type {HTMLDivElement} */ #body;
    /** @type {HTMLDivElement} */ #footer;
    /** @type {HTMLButtonElement} */ #btnCloseFooter;
    /** @type {Text} */ #btnCloseFooterTextNode;
    /** @type {HTMLButtonElement} */ #mainButton;
    /** @type {Text} */ #mainButtonTextNode;
    /** @type {boolean} */ #initialized = false;
    /** @type {string} */ #id; 
    /** @type {bootstrap.Modal} */ #bootstrap;
    
    #setInitializedState(x) { this.#initialized = x; }
    
    getDialogElement() { return this.#dialog; }

    isInitialized() { return this.#initialized; }
    getId() { return this.#id; } #setId(id) { 
        this.#id = id; 
        this.#modalFade.setAttribute('id', this.getId());
        return this; 
    }
    getTitleId() { return `${this.#id}__title`; }

    getTitle() { return this.#titleTextNode.wholeText; } 
    setTitle(title) { this.#titleTextNode.nodeValue = title; return this; }

    isScrollable() { return this.#dialog.classList.contains("modal-dialog-scrollable"); }
    setScrollable(x) {
        this.#dialog.classList.remove("modal-dialog-scrollable");
        if(x) this.#dialog.classList.add("modal-dialog-scrollable");
        return this;
    }

    getCloseButtonLabel() { return this.#btnCloseFooterTextNode.wholeText; }
    setCloseButtonLabel(label) { this.#btnCloseFooterTextNode.nodeValue = label; return this; }

    getMainButtonLabel() { return this.#mainButtonTextNode.wholeText; }
    setMainButtonLabel(text) { this.#mainButtonTextNode.nodeValue = text; return this; }

    getWrapper() { return this.#bootstrap; }
    #initWrapper() {
        this.#bootstrap = new bootstrap.Modal(this.#modalFade, {});
    }

    #initializeDOM() {
        this.#modalFade = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#dialog = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#modalContent = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#header = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#titleElement = /** @type {HTMLHeadingElement} */(document.createElement('H1'));
        this.#titleTextNode = document.createTextNode(CommonModal.DEFAULT_TITLE);
        this.#closeButton = /** @type {HTMLButtonElement} */(document.createElement('BUTTON'));
        this.#body = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#footer = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#btnCloseFooter = /** @type {HTMLButtonElement} */(document.createElement('BUTTON'));
        this.#btnCloseFooterTextNode = document.createTextNode(CommonModal.CLOSE_BUTTON_LABEL_DEFAULT);
        this.#mainButton = /** @type {HTMLButtonElement} */(document.createElement('BUTTON'));
        this.#mainButtonTextNode = document.createTextNode(CommonModal.MAIN_BUTTON_LABEL_DEFAULT);

    }

    #init(id) {
        this.#initializeDOM();
        this.#setId(id);
        this.#modalFade.setAttribute('class', 'modal fade');
        this.#modalFade.setAttribute('tabindex', '-1');
        this.#modalFade.setAttribute('aria-labelledby', this.getTitleId());
        this.#modalFade.setAttribute('aria-hidden', 'true');

        this.#dialog.setAttribute('class', 'modal-dialog');
        this.#modalFade.appendChild(this.#dialog);

        this.#modalContent.setAttribute('class', 'modal-content');
        this.#dialog.appendChild(this.#modalContent);

        this.#header.setAttribute('class', 'modal-header');
        this.#modalContent.appendChild(this.#header);

        this.#titleElement.setAttribute('class', 'modal-title fs-5');
        this.#titleElement.setAttribute('id', this.getTitleId());
        this.#header.appendChild(this.#titleElement);

        this.#titleElement.appendChild(this.#titleTextNode);

        this.#closeButton.setAttribute('type', 'button');
        this.#closeButton.setAttribute('class', 'btn-close');
        this.#closeButton.setAttribute('data-bs-dismiss', 'modal');
        this.#closeButton.setAttribute('aria-label', 'Close');
        this.#header.appendChild(this.#closeButton);

        this.#body.setAttribute('class', 'modal-body');
        this.#modalContent.appendChild(this.#body);

        this.#footer.setAttribute('class', 'modal-footer');
        this.#modalContent.appendChild(this.#footer);

        this.#btnCloseFooter.setAttribute('type', 'button');
        this.#btnCloseFooter.setAttribute('class', 'btn btn-secondary');
        this.#btnCloseFooter.setAttribute('data-bs-dismiss', 'modal');
        this.#footer.appendChild(this.#btnCloseFooter);

        this.#btnCloseFooter.appendChild(this.#btnCloseFooterTextNode);

        this.#mainButton.setAttribute('type', 'button');
        this.#mainButton.setAttribute('class', 'btn btn-primary');
        this.#footer.appendChild(this.#mainButton);

        this.#mainButton.appendChild(this.#mainButtonTextNode);

        this.#setInitializedState(true);

        document.body.append(this.#modalFade);

        this.#initWrapper();
    }

    constructor({ id, scrollable }) {
        this.#init(id);
        this.setScrollable(scrollable);
    }

    show() {this.getWrapper().show();}
    hide() {this.getWrapper().hide();}

    getBody() {
        return this.#body;
    }

    onHide(fun) {
        return this.#modalFade.addEventListener("hide.bs.modal", fun);
    }

    removeMainButton() {
        this.#mainButton.classList.add("d-none");
    }

}