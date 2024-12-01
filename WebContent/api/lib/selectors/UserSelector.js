'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import * as users from "./../../actions/users.js";
import { CommonModal } from "./../modals.js";
import { Selector } from "./Selector.js";
import { GenericQuery } from "./../../actions/commons.js";

/**
 * @class
 * @extends Selector<IUser>
 */
export class UserSelector extends Selector {
    
    queryL = new users.Query();
    /**
     * @returns {GenericQuery<IUser>}
     */
    initQuery() { return this.queryL; }
    setInitialQuery(query = new users.Query()) { this.queryL = query; }

    /**
     * @param {IUser} file 
     * @param {ElementBuilder} parent 
     * @returns {ElementBuilder}
     */
    itemGenerator(file, parent) {
        const m = new ElementBuilder("div").classList("ms-2", "me-auto").appendTo(parent.getTarget());
        new ElementBuilder("div").classList("fw-bold").text(file.name).appendTo(m.getTarget());
        new ElementBuilder("div").text(`@${file.username}` + (!file.active ? "(Deshabilitado)" : "")).appendTo(m.getTarget());
        return parent;
    }   

    /**
     * @param {IUser} file 
     * @returns {string}
     */
    print(file) {
        return `${file.name} (@${file.username})`;
    }

    getStrings() {
        return {
            ...super.getStrings(),
            NO_FILE_SELECTED_LABEL: "Ningún usuario seleccionado",
            ALL_FILES: "Todos",
            ONLY_ACTIVE_FILES: "Sólo habilitados",
            ONLY_INACTIVE_FILES: "Sólo deshabilitados",
            MODAL_TITLE: "Seleccione un usuario"
        }
    }

    constructor() {
        super();
    }
}
