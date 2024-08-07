import * as headerAdminService from "../../services/headerAdminService.js";
import { createStore } from "../../../lib/redux.js";
import { ElementBuilder } from "../../dom.js";
import { findById, update } from "../../../actions/patients.js";

const event = {
    UPDATE_NAME: "UPDATE_NAME",
    UPDATE_SURNAME: "UPDATE_SURNAME",
    UPDATE_DNI: "UPDATE_DNI",
    UPDATE_PHONE: "UPDATE_PHONE",
    UPDATE_EMAIL: "UPDATE_EMAIL",
    UPDATE_ADDRESS: "UPDATE_ADDRESS",
    UPDATE_LOCALTY: "UPDATE_LOCALTY",
    UPDATE_PROVINCE: "UPDATE_PROVINCE",
    UPDATE_BIRTH: "UPDATE_BIRTH"
};

const reducer = (state = 
    { id: null, name: "", surname: "", dni: "", email: "", phone: "", address: "", localty: "", province: "", birth: "", active: false, _lastOfflineSaved: null }, action) => {
    switch(action.type) {
        case event.UPDATE_NAME:
            return { ...state, name: action.payload };
        case event.UPDATE_SURNAME:
            return { ...state, surname: action.payload };
        case event.UPDATE_DNI:
            return { ...state, dni: action.payload };
        case event.UPDATE_EMAIL:
            return { ...state, email: action.payload };
        case event.UPDATE_PHONE:
            return { ...state, phone: action.payload };
        case event.UPDATE_ADDRESS:
            return { ...state, address: action.payload };
        case event.UPDATE_LOCALTY:
            return { ...state, localty: action.payload };
        case event.UPDATE_PROVINCE:
            return { ...state, province: action.payload };
        case event.UPDATE_BIRTH:
            return { ...state, birth: action.payload };
        default:
            return { ...state };
    }
};

const load = async () => {
    const header = headerAdminService.load();
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const patient = await findById(parseInt(urlParams.get("Id")));
    return patient
};

load().then((patient) => {
    const store = createStore(reducer, patient);
    const formModificarPaciente = document.getElementById("formModificarPaciente");

    const txtName = ElementBuilder.from(document.getElementById("txtName")).linkValue(store, event.UPDATE_NAME, "name");
    const txtSurname = ElementBuilder.from(document.getElementById("txtSurname")).linkValue(store, event.UPDATE_SURNAME, "surname");
    const txtDNI = ElementBuilder.from(document.getElementById("txtDNI")).linkValue(store, event.UPDATE_DNI, "dni");
    const txtEmail = ElementBuilder.from(document.getElementById("txtEmail")).linkValue(store, event.UPDATE_EMAIL, "email");
    const txtPhone = ElementBuilder.from(document.getElementById("txtPhone")).linkValue(store, event.UPDATE_PHONE, "phone");
    const txtAddress = ElementBuilder.from(document.getElementById("txtAddress")).linkValue(store, event.UPDATE_ADDRESS, "address");
    const txtLocalty = ElementBuilder.from(document.getElementById("txtLocalty")).linkValue(store, event.UPDATE_LOCALTY, "localty");
    const txtProvince = ElementBuilder.from(document.getElementById("txtProvince")).linkValue(store, event.UPDATE_PROVINCE, "province");
    const txtBirth = ElementBuilder.from(document.getElementById("txtBirth")).linkValue(store, event.UPDATE_BIRTH, "birth");

    formModificarPaciente.addEventListener("submit", async (event) => {
        event.preventDefault();
        event.stopPropagation();

        try {
            // @ts-ignore
            if (formModificarPaciente.checkValidity()) {
                await update(store.getState().id, store.getState());
                location.replace("/pacientes/index.html")
            }
            formModificarPaciente.classList.add("was-validated");
        } catch (error) {
            console.log(error)
        }
    });
})

