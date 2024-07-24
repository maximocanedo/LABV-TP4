'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import { CommonModal } from "./../modals.js";
import { GenericQuery } from "./../../actions/commons.js";

/**
 * @template T
 * @class
 */
export class Selector {

    static NO_FILE_SELECTED_LABEL = "Ningún registro seleccionado. ";
    static ALL_FILES = "Todos los registros";
    static ONLY_ACTIVE_FILES = "Sólo registros activos";
    static ONLY_INACTIVE_FILES = "Sólo registros deshabilitados";
    static MODAL_TITLE = "Seleccionar un registro"
    static CHANGE = "Cambiar";
    static SELECT = "Seleccionar";
    static LOAD_MORE = "Cargar más";
    static LOADING = "Cargando";
    static PAGE_SIZE = 10;

    #strings = {
        NO_FILE_SELECTED_LABEL: Selector.NO_FILE_SELECTED_LABEL,
        ALL_FILES: Selector.ALL_FILES,
        ONLY_ACTIVE_FILES: Selector.ONLY_ACTIVE_FILES,
        ONLY_INACTIVE_FILES: Selector.ONLY_INACTIVE_FILES,
        MODAL_TITLE: Selector.MODAL_TITLE,
        CHANGE: Selector.CHANGE,
        SELECT: Selector.SELECT,
        LOAD_MORE: Selector.LOAD_MORE,
        LOADING: Selector.LOADING,
        PAGE_SIZE: Selector.PAGE_SIZE
    };

    getStrings() { return this.#strings; }
    setStrings(strings) {
        this.#strings = {
            ...this.#strings,
            ...strings
        };
    }


    /** @type {string} */ #id;
    /** @type {T | null } */ #file = null;
    /** @type {CommonModal} */ #modal;
    // Form control private properties
    /** @type {Text} */ #selectedLabelTextNode;
    /** @type {HTMLButtonElement} */ #clearButton;
    /** @type {HTMLButtonElement} */ #mainButton;
    /** @type {Text} */ #mainButtonTextNode;
    // End of form control private properties.
    /** @type {HTMLDivElement} */ #trigger;
    /** @type {GenericQuery<T>} */#query = this.initQuery();
    // Modal list
    /** @type {HTMLDivElement} */ #listElement;
    /** @type {Text} */ #moreBtnText;
    /** @type {HTMLButtonElement} */ #moreBtn;
    /** @type {HTMLDivElement} */ #searchBox;
    // End of modal list
    /** @type {boolean} */ #cleanable = false;

    /**
     * @abstract
     * @param {T} file 
     * @returns {string}
     */
    print(file) {
        // @ts-ignore
        return `Elemento seleccionado. ${file.id ? "ID: " + file.id : ""}`
    }

    /**
     * @returns {GenericQuery<T>}
     */
    initQuery() {
        return new GenericQuery();
    }


    getSelectedFile() { return this.#file; } updateSelection(file) { 
        if(!file) {
            this.#selectedLabelTextNode.nodeValue = this.getStrings().NO_FILE_SELECTED_LABEL;
            this.#mainButtonTextNode.nodeValue = this.getStrings().SELECT;
            this.#clearButton.classList.add("d-none");
        }
        else {
            this.#selectedLabelTextNode.nodeValue = this.print(file);
            this.#mainButtonTextNode.nodeValue = this.getStrings().CHANGE;
            this.#clearButton.classList.remove("d-none");
        }
        this.#file = file;
        (this.getTrigger()).dispatchEvent(new Event('change'));
        this.#modal.hide();
        return this;
    }

    getId() { return this.#id; } #setId(id) { this.#id = id; }

    #countExistingModals() {
        return document.querySelectorAll("#selector").length;
    }

    #initializeFormControl() {
        this.#trigger = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#trigger.setAttribute('class', 'input-group');

        var selectedLabel = document.createElement('SPAN');
        selectedLabel.setAttribute('class', 'form-control');
        this.#trigger.appendChild(selectedLabel);

        this.#selectedLabelTextNode = document.createTextNode(this.getStrings().NO_FILE_SELECTED_LABEL);
        selectedLabel.appendChild(this.#selectedLabelTextNode);

        this.#clearButton = /** @type {HTMLButtonElement} */(document.createElement("BUTTON"));
        this.#clearButton.classList.add("input-group-text");
        this.#trigger.appendChild(this.#clearButton);
        this.#clearButton.innerText = 'Limpiar';
        this.#clearButton.classList.add("d-none");
        this.#clearButton.addEventListener('click', e => {
            this.updateSelection(null);
        });

        this.#mainButton = /** @type {HTMLButtonElement} */(document.createElement('BUTTON'));
        this.#mainButton.setAttribute('class', 'input-group-text');
        this.#trigger.appendChild(this.#mainButton);

        this.#mainButtonTextNode = document.createTextNode(this.getStrings().SELECT);
        this.#mainButton.appendChild(this.#mainButtonTextNode);

        return this.#trigger;
    }

    #initializeSearchBox() {
        this.#searchBox = /** @type {HTMLDivElement} */(document.createElement('DIV'));
        this.#searchBox.setAttribute('class', 'container-fluid mb-3 ');

        const row = document.createElement('DIV');
        row.setAttribute('class', 'row');
        this.#searchBox.appendChild(row);

        const inputGroup = document.createElement('DIV');
        inputGroup.setAttribute('class', 'col input-group mb-3');
        row.appendChild(inputGroup);

        const searchInput = /** @type {HTMLInputElement} */(document.createElement('INPUT'));
        searchInput.setAttribute('type', 'search');
        searchInput.setAttribute('class', 'form-control');
        searchInput.setAttribute('id', 'file_q');
        inputGroup.appendChild(searchInput);
        searchInput.addEventListener('change', e => {
            this.#query.setQueryText(searchInput.value);
        });

        const select = /** @type {HTMLSelectElement} */(document.createElement('SELECT'));
        select.setAttribute('name', 'filterStatus');
        select.setAttribute('class', 'form-select dropdown-toggle');
        select.setAttribute('id', 'filterStatus__user');
        select.addEventListener('change', e => {
            this.#query.filterByStatus(select.value);
        });
        inputGroup.appendChild(select);

        const onlyActiveOption = document.createElement('OPTION');
        onlyActiveOption.setAttribute('value', 'ONLY_ACTIVE');
        select.appendChild(onlyActiveOption);

        const onlyActiveOptionTextNode = document.createTextNode(this.getStrings().ONLY_ACTIVE_FILES);
        onlyActiveOption.appendChild(onlyActiveOptionTextNode);

        const onlyInactiveOption = document.createElement('OPTION');
        onlyInactiveOption.setAttribute('value', 'ONLY_INACTIVE');
        select.appendChild(onlyInactiveOption);

        const onlyInactiveTextNode = document.createTextNode(this.getStrings().ONLY_INACTIVE_FILES);
        onlyInactiveOption.appendChild(onlyInactiveTextNode);

        const bothOption = document.createElement('OPTION');
        bothOption.setAttribute('value', 'BOTH');
        select.appendChild(bothOption);

        const bothTextNode = document.createTextNode(this.getStrings().ALL_FILES);
        bothOption.appendChild(bothTextNode);

        const secondRow = document.createElement('DIV');
        secondRow.setAttribute('class', 'row');
        this.#searchBox.appendChild(secondRow);

        const secondRowUniqueColumn = document.createElement('DIV');
        secondRowUniqueColumn.setAttribute('class', 'col input-group');
        secondRow.appendChild(secondRowUniqueColumn);

        const btnSearch = document.createElement('BUTTON');
        btnSearch.setAttribute('id', 'btnSearch');
        btnSearch.setAttribute('class', 'btn btn-primary col');
        btnSearch.addEventListener('click', async (e) => {
            await this.firstload();
        });
        
        secondRowUniqueColumn.appendChild(btnSearch);

        const btnSearchTextNode = document.createTextNode((("Buscar")));
        btnSearch.appendChild(btnSearchTextNode);


    }

    #initialize() {
        const length = this.#countExistingModals();
        this.#setId("selector" + (length == 0 ? "" : `__${length}`));
        this.#initializeFormControl();
        this.#initializeSearchBox();
        this.#modal = new CommonModal({ id: this.getId(), scrollable: true }).setTitle(this.getStrings().MODAL_TITLE);
        this.#modal.getDialogElement().classList.add("custom-selector");
        this.#initializeUserBox();
        this.#mainButton.addEventListener('click', (e) => this.show());
    }

    show() {
        this.resetQuery();
        this.#modal.show();
        this.firstload();
    }

    setLoadingState(l) {
        this.#moreBtn.disabled = l;
        if(l) {
            this.#moreBtnText.textContent = this.getStrings().LOADING;
        } else {
            this.#moreBtnText.textContent = this.getStrings().LOAD_MORE;
        }
        return this;
    }

    #initializeUserBox() {
        this.#listElement = /** @type {HTMLDivElement} */(document.createElement("div"));
        this.#listElement.classList.add("list-group", "list-group-flush", "container-fluid");
        this.#moreBtn = /** @type {HTMLButtonElement} */(new ElementBuilder("button").classList("btn", "fullWidth", "mb-3").getTarget());
        this.#moreBtnText = document.createTextNode(this.getStrings().LOAD_MORE);
        this.#moreBtn.append(this.#moreBtnText);
        this.#moreBtn.addEventListener('click', async (e) => {
            await this.next();
        });
        this.#modal.getBody().append(this.#searchBox, this.#listElement, this.#moreBtn);
    }

    resetQuery() {
        this.#query = this.initQuery();
    }


    async firstload() {
        this.setLoadingState(true);
        this.#listElement.innerHTML = '';
        this.#query.paginate(1, this.getStrings().PAGE_SIZE);
        this.#query.isSelector(true);
        return this.#query.search()
            .then(results => {
                results.map(result => {
                    // @ts-ignore
                    if(!result.active) return;
                    const item = this.#generateItem(result);
                    this.#listElement.append(item.getTarget());
                });
            }).catch(console.error).finally(() => {
                this.setLoadingState(false);
            });
    }

    async next() {
        this.setLoadingState(true);
        this.#query.isSelector(true);
        return this.#query.next()
            .then(results => {
                results.map(result => {
                    // @ts-ignore
                    if(!result.active) return;
                    const item = this.#generateItem(result);
                    this.#listElement.append(item.getTarget());
                });
            }).catch(console.error).finally(() => {
                this.setLoadingState(false);
            });
    }

    /**
     * @abstract
     * @param {T} file
     * @param {ElementBuilder} parent
     * @returns {ElementBuilder}
     */
    itemGenerator(file, parent) {
        const m = new ElementBuilder("div").classList("ms-2", "me-auto").appendTo(parent.getTarget());
        new ElementBuilder("div").classList("fw-bold").text('Elemento').appendTo(m.getTarget());
        new ElementBuilder("div").text('').appendTo(m.getTarget());
        return parent;
    }

    #generateItem(/** @type {T} */file) {
        const el = this.itemGenerator(
            file, 
            new ElementBuilder("div")
                .classList("list-group-item", "list-group-item-action")
                .click((ev, el) => {
                    this.updateSelection(file);
                })
        );
        return el;
    }

    constructor() {
        this.#initialize();
    }

    getTrigger() {
        return this.#trigger;
    }
}