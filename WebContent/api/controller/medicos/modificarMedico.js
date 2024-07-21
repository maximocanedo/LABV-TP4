import * as headerAdminService from "../services/headerAdminService.js";
import { createStore } from "../../lib/redux.js";
import { ElementBuilder } from "../dom.js";
import { findById } from "../../actions/doctors.js";
import { login } from "../../actions/users.js";

const event = {
    UPDATE_NAME: "UPDATE_NAME",
    UPDATE_SURNAME: "UPDATE_SURNAME",
    UPDATE_EMAIL: "UPDATE_EMAIL",
    UPDATE_ADDRESS: "UPDATE_ADDRESS",
    UPDATE_SPECIALTYNAME: "UPDATE_SPECIALTYNAME",
    UPDATE_SEX: "UPDATE_SEX",
    UPDATE_BIRTH: "UPDATE_BIRTH",
    UPDATE_PHONE: "UPDATE_PHONE",
    UPDATE_LOCALTY: "UPDATE_LOCALTY",
};

const reducer = (state = {
    id: null,
    file: null,
    schedules: [],
    _lastOfflineSaved: null,
    sex: "",
    birth: "",
    phone: "",
    active: false,
    name: "",
    surname: "",
    email: "",
    address: "",
    localty: "",
    specialty: {
        id: null,
        name: "",
        active: false,
        description: "",
        _lastOfflineSaved: null
    }
}, action) => {
    switch (action.type) {
        case event.UPDATE_NAME:
            return { ...state, name: action.payload };
        case event.UPDATE_SURNAME:
            return { ...state, surname: action.payload };
        case event.UPDATE_SEX:
            return { ...state, sex: action.payload };
        case event.UPDATE_BIRTH:
            return { ...state, birth: action.payload };
        case event.UPDATE_PHONE:
            return { ...state, phone: action.payload };
        case event.UPDATE_LOCALTY:
            return { ...state, localty: action.payload };
        case event.UPDATE_EMAIL:
            return { ...state, email: action.payload };
        case event.UPDATE_ADDRESS:
            return { ...state, address: action.payload };
        case event.UPDATE_SPECIALTYNAME:
            return {
                ...state,
                specialty: {
                    ...state.specialty,
                    name: action.payload
                }
            };
        default:
            return { ...state };
    }
};

const load = async () => {
    const user = await login("alicia.schimmel", "12345678");
    const header = headerAdminService.load();
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const doctor = await findById(parseInt(urlParams.get("Id")));
    return doctor;
};

load().then((doctor) => {
    const store = createStore(reducer, doctor);
    const formModificar = document.getElementById("formModificar");

    const txtName = ElementBuilder.from(document.getElementById("txtName")).linkValue(store, event.UPDATE_NAME, "name");
    const txtSurname = ElementBuilder.from(document.getElementById("txtSurname")).linkValue(store, event.UPDATE_SURNAME, "surname");
    const txtEmail = ElementBuilder.from(document.getElementById("txtEmail")).linkValue(store, event.UPDATE_EMAIL, "email");
    const txtPhone = ElementBuilder.from(document.getElementById("txtPhone")).linkValue(store, event.UPDATE_PHONE, "phone");
    const txtAddress = ElementBuilder.from(document.getElementById("txtAddress")).linkValue(store, event.UPDATE_ADDRESS, "address");
    const txtLocalty = ElementBuilder.from(document.getElementById("txtLocalty")).linkValue(store, event.UPDATE_LOCALTY, "localty");
    const txtBirth = ElementBuilder.from(document.getElementById("txtBirth")).linkValue(store, event.UPDATE_BIRTH, "birth");
    const txtGenre = ElementBuilder.from(document.getElementById("txtGenre")).linkValue(store, event.UPDATE_SEX, "sex");
    const txtSpecialtyName = ElementBuilder.from(document.getElementById("txtSpecialtyName")).linkValue(store, event.UPDATE_SPECIALTYNAME, 'specialty.name');
    const txtSpecialtyDescription = ElementBuilder.from(document.getElementById("txtSpecialtyDescription")).linkValue(store, event.UPDATE_SPECIALTYNAME, 'specialty.description');
    
})
