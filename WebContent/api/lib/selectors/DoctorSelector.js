'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import * as doctors from "./../../actions/doctors.js";
import { CommonModal } from "./../modals.js";

export class DoctorSelector {

    static NO_DOCTOR_SELECTED_LABEL = "Ningún doctor seleccionado. ";
    static CHANGE_DOCTOR = "Cambiar";
    static SELECT_DOCTOR = "Seleccionar";
    static LOAD_MORE = "Cargar más";
    static LOADING = "Cargando";
    static PAGE_SIZE = 10;

    /** @type {string} */ #id;
    /** @type {IDoctor | null } */ #doctor = null;
    /** @type {CommonModal} */ #modal;
    // Form control private properties
    /** @type {Text} */ #selectedLabelTextNode;
    /** @type {HTMLButtonElement} */ #mainButton;
    /** @type {Text} */ #mainButtonTextNode;
    // End of form control private properties.
    #trigger;
    /** @type {doctors.Query} */#query;
    // Modal's user list
    /** @type {HTMLDivElement} */ #listElement;
    /** @type {Text} */ #moreBtnText;
    /** @type {HTMLButtonElement} */ #moreBtn;
    /** @type {HTMLDivElement} */ #searchBox;
    // End of modal's user list
    /** @type {boolean} */ #cleanable = false;

    getSelected() { return this.#doctor; } setSelected(reg) { 
        if(!reg) {
            this.#selectedLabelTextNode.nodeValue = DoctorSelector.NO_DOCTOR_SELECTED_LABEL;
            this.#mainButtonTextNode.nodeValue = DoctorSelector.SELECT_DOCTOR;
        }
        else {
            this.#selectedLabelTextNode.nodeValue = `Dr. ${reg.surname}, ${reg.name} (L.N.º ${reg.file})`;
            this.#mainButtonTextNode.nodeValue = DoctorSelector.CHANGE_DOCTOR;
        }
        this.#doctor = reg;
        this.#modal.hide();
        return this;
    }

    getId() { return this.#id; } #setId(id) { this.#id = id; }

    #countExistingModals() {
        return document.querySelectorAll("#DoctorSelector").length;
    }

    #initializeFormControl() {
        this.#trigger = document.createElement('DIV');
        this.#trigger.setAttribute('class', 'input-group mb-3');

        var selectedLabel = document.createElement('SPAN');
        selectedLabel.setAttribute('class', 'form-control');
        this.#trigger.appendChild(selectedLabel);

        this.#selectedLabelTextNode = document.createTextNode(DoctorSelector.NO_DOCTOR_SELECTED_LABEL);
        selectedLabel.appendChild(this.#selectedLabelTextNode);

        this.#mainButton = /** @type {HTMLButtonElement} */(document.createElement('BUTTON'));
        this.#mainButton.setAttribute('class', 'input-group-text');
        this.#trigger.appendChild(this.#mainButton);

        this.#mainButtonTextNode = document.createTextNode(DoctorSelector.SELECT_DOCTOR);
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
        searchInput.setAttribute('id', 'doctor_q');
        inputGroup.appendChild(searchInput);
        searchInput.addEventListener('change', e => {
            this.#query.setQueryText(searchInput.value);
        });

        const select = /** @type {HTMLSelectElement} */(document.createElement('SELECT'));
        select.setAttribute('name', 'filterStatus');
        select.setAttribute('class', 'form-select dropdown-toggle');
        select.setAttribute('id', 'filterStatus__doctor');
        select.addEventListener('change', e => {
            this.#query.filterByStatus(select.value);
        });
        inputGroup.appendChild(select);

        const onlyActiveOption = document.createElement('OPTION');
        onlyActiveOption.setAttribute('value', 'ONLY_ACTIVE');
        select.appendChild(onlyActiveOption);

        const onlyActiveOptionTextNode = document.createTextNode((("Registros activos")));
        onlyActiveOption.appendChild(onlyActiveOptionTextNode);

        const onlyInactiveOption = document.createElement('OPTION');
        onlyInactiveOption.setAttribute('value', 'ONLY_INACTIVE');
        select.appendChild(onlyInactiveOption);

        const onlyInactiveTextNode = document.createTextNode((("Registros deshabilitados")));
        onlyInactiveOption.appendChild(onlyInactiveTextNode);

        const bothOption = document.createElement('OPTION');
        bothOption.setAttribute('value', 'BOTH');
        select.appendChild(bothOption);

        const bothTextNode = document.createTextNode((("Todos los registros")));
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
        this.#setId("DoctorSelector" + (length == 0 ? "" : `__${length}`));
        this.#initializeFormControl();
        this.#initializeSearchBox();
        this.#modal = new CommonModal({ id: this.getId(), scrollable: true }).setTitle("Seleccionar médico");
        this.#modal.getDialogElement().classList.add("custom-selector", "doctor-selector");
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
            this.#moreBtnText.textContent = DoctorSelector.LOADING;
        } else {
            this.#moreBtnText.textContent = DoctorSelector.LOAD_MORE;
        }
        return this;
    }

    #initializeUserBox() {
        this.#listElement = /** @type {HTMLDivElement} */(document.createElement("div"));
        this.#listElement.classList.add("list-group", "list-group-flush", "container-fluid");
        this.#moreBtn = /** @type {HTMLButtonElement} */(new ElementBuilder("button").classList("btn", "fullWidth", "mb-3").getTarget());
        this.#moreBtnText = document.createTextNode(DoctorSelector.LOAD_MORE);
        this.#moreBtn.append(this.#moreBtnText);
        this.#moreBtn.addEventListener('click', async (e) => {
            await this.next();
        });
        this.#modal.getBody().append(this.#searchBox, this.#listElement, this.#moreBtn);
    }

    resetQuery() {
        this.#query = new doctors.Query();
    }

    async firstload() {
        this.setLoadingState(true);
        this.#listElement.innerHTML = '';
        this.#query.paginate(1, DoctorSelector.PAGE_SIZE);
        return this.#query.search()
            .then(results => {
                results.map(result => {
                    if(!result.active) return;
                    const item = this.#generateItem(result);
                    this.#listElement.append(item.getTarget());
                });
            }).catch(console.error).finally(() => {
                this.setLoadingState(false);
            });
    }

    async next() {
        console.log("NEXT");
        this.setLoadingState(true);
        return this.#query.next()
            .then(results => {
                results.map(result => {
                    if(!result.active) return;
                    const item = this.#generateItem(result);
                    this.#listElement.append(item.getTarget());
                });
            }).catch(console.error).finally(() => {
                this.setLoadingState(false);
            });
    }

    #generateItem(/** @type {IDoctor} */doctor) {
        const item = new ElementBuilder("div")
                            .classList("list-group-item", "list-group-item-action");
        const m = new ElementBuilder("div").classList("ms-2", "me-auto").appendTo(item.getTarget());
        const n = new ElementBuilder("div").classList("fw-bold").text(`Dr. ${doctor.surname}, ${doctor.name}`).appendTo(m.getTarget());
        const u = new ElementBuilder("div").text(`Legajo N.º: ${doctor.file}`).appendTo(m.getTarget());
        item.click((ev, el) => {
            this.setSelected(doctor);
        });
        return item;
    }

    constructor() {
        this.#initialize();
    }

    getTrigger() {
        return this.#trigger;
    }
}