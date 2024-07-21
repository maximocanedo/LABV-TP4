'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import * as doctors from "./../../actions/doctors.js";
import { CommonModal } from "./../modals.js";
import { Selector } from "./Selector.js";
import { GenericQuery } from "./../../actions/commons.js";

/**
 * @class
 * @extends Selector<IDoctor>
 */
export class DoctorSelector extends Selector {

    static NO_FILE_SELECTED_LABEL = "Ningún médico seleccionado";

    /**
     * @returns {GenericQuery<IDoctor>}
     */
    initQuery() { return new doctors.Query(); }

    /**
     * @param {IDoctor} file 
     * @param {ElementBuilder} parent 
     * @returns {ElementBuilder}
     */
    itemGenerator(file, parent) {
        const m = new ElementBuilder("div").classList("ms-2", "me-auto").appendTo(parent.getTarget());
        new ElementBuilder("div").classList("fw-bold").text(
            `Dr. ${file.surname}, ${file.name}`
        ).appendTo(m.getTarget());
        new ElementBuilder("div").text(
            `Legajo N.º ${file.file}`
        ).appendTo(m.getTarget());
        return parent;
    }   

    /**
     * @param {IDoctor} file 
     * @returns {string}
     */
    print(file) {
        return `Dr. ${file.surname}, ${file.name}`;
    }

    getStrings() {
        return {
            ...super.getStrings(),
            NO_FILE_SELECTED_LABEL: "Ningún médico seleccionado",
            ALL_FILES: "Todos los médicos",
            ONLY_ACTIVE_FILES: "Sólo médicos activos",
            ONLY_INACTIVE_FILES: "Sólo médicos deshabilitados",
            MODAL_TITLE: "Seleccione un médico"
        }
    }

    constructor() {
        super();
    }

}