'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import * as patients from "./../../actions/patients.js";
import { CommonModal } from "./../modals.js";
import { Selector } from "./Selector.js";
import { GenericQuery } from "./../../actions/commons.js";


const DNIFormat = (dni) => {
    let dniStr = String(dni).replace(/\D/g, '');
    return dniStr.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
}

/**
 * @class
 * @extends Selector<IPatient>
 */
export class PatientSelector extends Selector {

    /**
     * @returns {GenericQuery<IPatient>}
     */
    initQuery() { return new patients.Query(); }

    /**
     * @param {IPatient} file 
     * @param {ElementBuilder} parent 
     * @returns {ElementBuilder}
     */
    itemGenerator(file, parent) {
        const m = new ElementBuilder("div").classList("ms-2", "me-auto").appendTo(parent.getTarget());
        new ElementBuilder("div").classList("fw-bold").text(`${file.surname.toLocaleUpperCase()}, ${file.name.toLocaleUpperCase()}`).appendTo(m.getTarget());
        new ElementBuilder("div").text(`D.N.I. N.º ${DNIFormat(file.dni)}`).appendTo(m.getTarget());
        return parent;
    }   

    /**
     * @param {IPatient} file 
     * @returns {string}
     */
    print(file) {

        return `DNI ${DNIFormat(file.dni)} ${file.surname.toLocaleUpperCase()}, ${file.name.toLocaleUpperCase()}`;
    }

    getStrings() {
        return {
            ...super.getStrings(),
            NO_FILE_SELECTED_LABEL: "Ningún paciente seleccionado",
            ALL_FILES: "Todos",
            ONLY_ACTIVE_FILES: "Sólo habilitados",
            ONLY_INACTIVE_FILES: "Sólo deshabilitados",
            MODAL_TITLE: "Seleccione un paciente"
        }
    }

    constructor() {
        super();
    }

}