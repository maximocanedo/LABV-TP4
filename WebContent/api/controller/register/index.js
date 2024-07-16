import * as headerService from "./../services/headerService.js";
import { signup } from "../../actions/users.js";
import { createStore } from "./../../lib/redux.js";
import { ElementBuilder } from "../dom.js";

(() => {
    const header = headerService.load();
})();

const event = {
    UPDATE_NAME: "UPDATE_NAME",
    UPDATE_SURNAME: "UPDATE_SURNAME",
    UPDATE_USERNAME: "UPDATE_USERNAME",
    UPDATE_EMAIL: "UPDATE_EMAIL",
    UPDATE_BIRTHDATE: "UPDATE_BIRTHDATE",
    UPDATE_GENRE: "UPDATE_GENRE",
    UPDATE_ADDRESS: "UPDATE_ADDRESS",
    UPDATE_LOCALTY: "UPDATE_LOCALTY",
    UPDATE_PHONE: "UPDATE_PHONE",
    UPDATE_PASSWORD: "UPDATE_PASSWORD"
};

const reducer = (state = { user: null, name: "", surname: "", username: "", email: "", birthdate: "", genre: "", address: "", localty: "", phone: "", password: "" }, action) => {
    switch(action.type) {
        case event.UPDATE_USER:
            return { ...state, user: action.payload };
        case event.UPDATE_NAME:
            return { ...state, name: action.payload };
        case event.UPDATE_SURNAME:
            return { ...state, surname: action.payload };
        case event.UPDATE_USERNAME:
            return { ...state, username: action.payload };
        case event.UPDATE_EMAIL:
            return { ...state, email: action.payload };
        case event.UPDATE_BIRTHDATE:
            return { ...state, birthdate: action.payload };
        case event.UPDATE_GENRE:
            return { ...state, genre: action.payload };
        case event.UPDATE_ADDRESS:
            return { ...state, address: action.payload };
        case event.UPDATE_LOCALTY:
            return { ...state, localty: action.payload };
        case event.UPDATE_PHONE:
            return { ...state, phone: action.payload };
        case event.UPDATE_PASSWORD:
            return { ...state, password: action.payload };
        default:
            return { ...state };
    }
};

const store = createStore(reducer, {
    user: null, 
    name: "", 
    surname: "", 
    username: "", 
    email: "", 
    birthdate: "", 
    genre: "", 
    address: "", 
    localty: "", 
    phone: "", 
    password: ""
});

const formRegister = document.getElementById("formRegister");

const txtName = ElementBuilder.from(document.getElementById("txtName")).linkValue(store, event.UPDATE_NAME, "name");
const txtSurname = ElementBuilder.from(document.getElementById("txtSurname")).linkValue(store, event.UPDATE_USERNAME, "surname");
const txtUsername = ElementBuilder.from(document.getElementById("txtUsername")).linkValue(store, event.UPDATE_USERNAME, "username");
const txtEmail = ElementBuilder.from(document.getElementById("txtEmail")).linkValue(store, event.UPDATE_EMAIL, "email");
const txtBirthDate = ElementBuilder.from(document.getElementById("txtBirthDate")).linkValue(store, event.UPDATE_BIRTHDATE, "birthdate");
const txtGenre = ElementBuilder.from(document.getElementById("txtGenre")).linkValue(store, event.UPDATE_GENRE, "genre");
const txtAddress = ElementBuilder.from(document.getElementById("txtAddress")).linkValue(store, event.UPDATE_ADDRESS, "address");
const txtLocalty = ElementBuilder.from(document.getElementById("txtLocalty")).linkValue(store, event.UPDATE_LOCALTY, "localty");
const txtPhone = ElementBuilder.from(document.getElementById("txtPhone")).linkValue(store, event.UPDATE_PHONE, "phone");
const txtPassword = ElementBuilder.from(document.getElementById("txtPassword")).linkValue(store, event.UPDATE_PASSWORD, "password");

formRegister.addEventListener("submit", async (event) => {
    event.preventDefault()
    console.log(store.getState())
    /*
    // @ts-ignore
    if (!formRegister.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
    }
    formRegister.classList.add('was-validated');
    */
    /*
    const registerResponse = await signup();
    console.log(registerResponse);
    location.href = "http://localhost/Administrador/AsignarTurno.html";
    */
}, false);