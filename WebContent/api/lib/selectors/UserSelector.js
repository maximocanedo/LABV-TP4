'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import * as users from "./../../actions/users.js";
import { CommonModal } from "./../modals.js";

export class UserSelector {

    static NO_USER_SELECTED_LABEL = "Ningún usuario seleccionado. ";
    static CHANGE_USER = "Cambiar";
    static SELECT_USER = "Seleccionar";
    static LOAD_MORE = "Cargar más";
    static LOADING = "Cargando";
    static PAGE_SIZE = 10;

    /** @type {string} */ #id;
    /** @type {IUser | null } */ #user = null;
    /** @type {CommonModal} */ #modal;
    // Form control private properties
    /** @type {Text} */ #selectedLabelTextNode;
    /** @type {HTMLButtonElement} */ #mainButton;
    /** @type {Text} */ #mainButtonTextNode;
    // End of form control private properties.
    #trigger;
    /** @type {users.Query} */#query;
    // Modal's user list
    /** @type {HTMLDivElement} */ #listElement;
    /** @type {Text} */ #moreBtnText;
    /** @type {HTMLButtonElement} */ #moreBtn;
    /** @type {HTMLDivElement} */ #searchBox;
    // End of modal's user list
    /** @type {boolean} */ #cleanable = false;

    getSelectedUser() { return this.#user; } setSelectedUser(user) { 
        if(!user) {
            this.#selectedLabelTextNode.nodeValue = UserSelector.NO_USER_SELECTED_LABEL;
            this.#mainButtonTextNode.nodeValue = UserSelector.SELECT_USER;
        }
        else {
            this.#selectedLabelTextNode.nodeValue = `${user.name} (@${user.username})`;
            this.#mainButtonTextNode.nodeValue = UserSelector.CHANGE_USER;
        }
        this.#user = user;
        this.#modal.hide();
        return this;
    }

    getId() { return this.#id; } #setId(id) { this.#id = id; }

    #countExistingModals() {
        return document.querySelectorAll("#userSelector").length;
    }

    #initializeFormControl() {
        this.#trigger = document.createElement('DIV');
        this.#trigger.setAttribute('class', 'input-group mb-3');

        var selectedLabel = document.createElement('SPAN');
        selectedLabel.setAttribute('class', 'form-control');
        this.#trigger.appendChild(selectedLabel);

        this.#selectedLabelTextNode = document.createTextNode("Ningún usuario seleccionado. ");
        selectedLabel.appendChild(this.#selectedLabelTextNode);

        this.#mainButton = /** @type {HTMLButtonElement} */(document.createElement('BUTTON'));
        this.#mainButton.setAttribute('class', 'input-group-text');
        this.#trigger.appendChild(this.#mainButton);

        this.#mainButtonTextNode = document.createTextNode(UserSelector.SELECT_USER);
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
        searchInput.setAttribute('id', 'user_q');
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

        const onlyActiveOptionTextNode = document.createTextNode((("Usuarios activos")));
        onlyActiveOption.appendChild(onlyActiveOptionTextNode);

        const onlyInactiveOption = document.createElement('OPTION');
        onlyInactiveOption.setAttribute('value', 'ONLY_INACTIVE');
        select.appendChild(onlyInactiveOption);

        const onlyInactiveTextNode = document.createTextNode((("Usuarios deshabilitados")));
        onlyInactiveOption.appendChild(onlyInactiveTextNode);

        const bothOption = document.createElement('OPTION');
        bothOption.setAttribute('value', 'BOTH');
        select.appendChild(bothOption);

        const bothTextNode = document.createTextNode((("Todos los usuarios")));
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
        this.#setId("userSelector" + (length == 0 ? "" : `__${length}`));
        this.#initializeFormControl();
        this.#initializeSearchBox();
        this.#modal = new CommonModal({ id: this.getId(), scrollable: true }).setTitle("Seleccionar usuario");
        this.#modal.getDialogElement().classList.add("custom-selector", "user-selector");
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
            this.#moreBtnText.textContent = UserSelector.LOADING;
        } else {
            this.#moreBtnText.textContent = UserSelector.LOAD_MORE;
        }
        return this;
    }

    #initializeUserBox() {
        this.#listElement = /** @type {HTMLDivElement} */(document.createElement("div"));
        this.#listElement.classList.add("list-group", "list-group-flush", "container-fluid");
        this.#moreBtn = /** @type {HTMLButtonElement} */(new ElementBuilder("button").classList("btn", "fullWidth", "mb-3").getTarget());
        this.#moreBtnText = document.createTextNode(UserSelector.LOAD_MORE);
        this.#moreBtn.append(this.#moreBtnText);
        this.#moreBtn.addEventListener('click', async (e) => {
            await this.next();
        });
        this.#modal.getBody().append(this.#searchBox, this.#listElement, this.#moreBtn);
    }

    resetQuery() {
        this.#query = new users.Query();
    }

    async firstload() {
        this.setLoadingState(true);
        this.#listElement.innerHTML = '';
        this.#query.paginate(1, UserSelector.PAGE_SIZE);
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

    #generateItem(/** @type {IUser} */user) {
        const item = new ElementBuilder("div")
                            .classList("list-group-item", "list-group-item-action");
        const m = new ElementBuilder("div").classList("ms-2", "me-auto").appendTo(item.getTarget());
        const n = new ElementBuilder("div").classList("fw-bold").text(user.name).appendTo(m.getTarget());
        const u = new ElementBuilder("div").text(`@${user.username}`).appendTo(m.getTarget());
        item.click((ev, el) => {
            this.setSelectedUser(user);
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