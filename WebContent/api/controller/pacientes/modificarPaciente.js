import * as headerAdminService from "../services/headerAdminService.js";
import { createStore } from "./../../lib/redux.js";
import { ElementBuilder } from "../dom.js";
import { findById } from "../../actions/patients.js";
import { login } from "../../actions/users.js";

const event = {
    UPDATE_NAME: "UPDATE_NAME",
    UPDATE_SURNAME: "UPDATE_SURNAME",
    UPDATE_DNI: "UPDATE_DNI",
    UPDATE_EMAIL: "UPDATE_EMAIL",
    UPDATE_PHONE: "UPDATE_PHONE",
};

const reducer = (state = { name: "", surname: "", dni: "", email: "", phone: "" }, action) => {
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
        default:
            return { ...state };
    }
};

const store = createStore(reducer, {
    name: "", 
    surname: "",
    dni: "",
    email: "", 
    phone: ""
});

const formRegister = document.getElementById("formRegister");

const txtName = ElementBuilder.from(document.getElementById("txtName")).linkValue(store, event.UPDATE_NAME, "name");
const txtSurname = ElementBuilder.from(document.getElementById("txtSurname")).linkValue(store, event.UPDATE_SURNAME, "surname");
const txtDNI = ElementBuilder.from(document.getElementById("txtDNI")).linkValue(store, event.UPDATE_DNI, "dni");
const txtEmail = ElementBuilder.from(document.getElementById("txtEmail")).linkValue(store, event.UPDATE_EMAIL, "email");
const txtPhone = ElementBuilder.from(document.getElementById("txtPhone")).linkValue(store, event.UPDATE_PHONE, "phone");

(async () => {
    const user = await login("alicia.schimmel", "12345678");
    const header = headerAdminService.load();
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const patient = await findById(parseInt(urlParams.get("Id")));
    store.dispatch({type: event.UPDATE_NAME, payload: patient.name});
    store.dispatch({type: event.UPDATE_SURNAME, payload: patient.surname});
    store.dispatch({type: event.UPDATE_DNI, payload: patient.dni});
    // @ts-ignore
    store.dispatch({type: event.UPDATE_EMAIL, payload: patient.email});
    // @ts-ignore
    store.dispatch({type: event.UPDATE_PHONE, payload: patient.phone});
})();

