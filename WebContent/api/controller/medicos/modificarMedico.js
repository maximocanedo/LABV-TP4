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
    UPDATE_SPECIALTY: "UPDATE_SPECIALTY"
};

const reducer = (state = { name: "", surname: "", email: "", address: "", specialty: "" }, action) => {
    switch(action.type) {
        case event.UPDATE_NAME:
            return { ...state, name: action.payload };
        case event.UPDATE_SURNAME:
            return { ...state, surname: action.payload };
        case event.UPDATE_EMAIL:
            return { ...state, email: action.payload };
        case event.UPDATE_ADDRESS:
            return { ...state, address: action.payload };
        case event.UPDATE_SPECIALTY:
            return { ...state, specialty: action.payload };
        default:
            return { ...state };
    }
};

const store = createStore(reducer, {
    name: "", 
    surname: "",
    email: "",
    address: "",
    specialty: ""
});

const formRegister = document.getElementById("formRegister");

const txtName = ElementBuilder.from(document.getElementById("txtName")).linkValue(store, event.UPDATE_NAME, "name");
const txtSurname = ElementBuilder.from(document.getElementById("txtSurname")).linkValue(store, event.UPDATE_SURNAME, "surname");
const txtEmail = ElementBuilder.from(document.getElementById("txtEmail")).linkValue(store, event.UPDATE_SURNAME, "email");
const txtAddress = ElementBuilder.from(document.getElementById("txtAddress")).linkValue(store, event.UPDATE_SURNAME, "address");
const txtSpecialty = ElementBuilder.from(document.getElementById("txtSpecialty")).linkValue(store, event.UPDATE_SPECIALTY, "specialty");

addEventListener("DOMContentLoaded", async (event) => {
    const user = await login("alicia.schimmel", "12345678");
    const header = headerAdminService.load();
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const doctor = await findById(parseInt(urlParams.get("Id")));
    txtName.value(doctor.name);
    txtSurname.value(doctor.surname);
    txtSpecialty.value(doctor.specialty.name);
    txtEmail.value(doctor.email);
    txtAddress.value(doctor.address);
})

