'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import * as specialties from "./../../actions/specialties.js";
import { CommonModal } from "./../modals.js";
import { Selector } from "./Selector.js";
import { GenericQuery } from "./../../actions/commons.js";

/**
 * @class
 * @extends Selector<Specialty>
 */
export class SpecialtySelector extends Selector {

    /**
     * @returns {GenericQuery<Specialty>}
     */
    initQuery() { return new specialties.Query(); }

    /**
     * @param {Specialty} file 
     * @param {ElementBuilder} parent 
     * @returns {ElementBuilder}
     */
    itemGenerator(file, parent) {
        const m = new ElementBuilder("div").classList("ms-2", "me-auto").appendTo(parent.getTarget());
        new ElementBuilder("div").classList("fw-bold").text(file.name).appendTo(m.getTarget());
        new ElementBuilder("div").text(file.description).appendTo(m.getTarget());
        return parent;
    }   

    /**
     * @param {Specialty} file 
     * @returns {string}
     */
    print(file) {
        return file.name;
    }

    getStrings() {
        return {
            ...super.getStrings(),
            NO_FILE_SELECTED_LABEL: "Ningúna seleccionada",
            ALL_FILES: "Todas",
            ONLY_ACTIVE_FILES: "Sólo habilitadas",
            ONLY_INACTIVE_FILES: "Sólo deshabilitadas",
            MODAL_TITLE: "Seleccione una especialidad"
        }
    }

    constructor() {
        super();
    }

}