import { login, myself, logout } from "./../../actions/users.js";
import { ElementBuilder } from "./../dom.js";
import { createStore } from "./../../lib/redux.js";
import * as headerService from "./../services/headerService.js";

(() => {
    const header = headerService.load();
    console.log(header.update);
})();

const event = {
    UPDATE_USER: "UPDATE_USER",
    UPDATE_USERNAME: "UPDATE_USERNAME",
    UPDATE_PASSWORD: "UPDATE_PASSWORD"
};

const reducer = (state = { user: null, username: "", password: "" }, action) => {
    switch(action.type) {
        case event.UPDATE_USER:
            return { ...state, user: action.payload };
        case event.UPDATE_USERNAME:
            return { ...state, username: action.payload };
        case event.UPDATE_PASSWORD:
            return { ...state, password: action.payload };
        default:
            return { ...state };
    }
};

const store = createStore(reducer, {
    user: null,
    username: "",
    password: ""
});

const navLogout = ElementBuilder.from(document.getElementById("navLogout"));
const navLogin = ElementBuilder.from(document.getElementById("navLogin"));
const ddMenu = ElementBuilder.from(document.getElementById("ddMenu"));
const ddMenuButton = ElementBuilder.from(document.getElementById("ddMenuButton"));
const navRegister = ElementBuilder.from(document.getElementById("navRegister"));
const txtUsername = ElementBuilder
                        .from(document.getElementById("txtUsername"))
                        .linkValue(store, event.UPDATE_USERNAME, "username");
const txtPassword = ElementBuilder
                        .from(document.getElementById("txtPassword"))
                        .linkValue(store, event.UPDATE_PASSWORD, "password");
const btnLogin = ElementBuilder.from(document.getElementById("btnLogin"));
const formLogin = ElementBuilder.from(document.getElementById("formLogin"));

navLogout.click(() => {
    logout();
    location.reload();
});

(async () => {
    try {
        const user = await myself();
        ddMenu.classList("dropdown");
        ddMenuButton.text(user.username);
        navLogin.classList("d-none");
        navRegister.classList("d-none");
        formLogin.classList("d-none");
    } catch (error) {
        console.log(error);
        formLogin.classList("flex-fill", "needs-validation");
    }
})();


formLogin.on("submit", async (event, _element) => {
    event.preventDefault();
    // @ts-ignore
    if (!formLogin.getTarget().checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
    }
    formLogin.classList('was-validated');
    const loginResponse = await login(store.getState().username, store.getState().password);
    const user = await myself();
    // @ts-ignore
    if(user.access.length >= 30){
        location.replace("/pacientes/index.html")
    } else {
        //location.replace("/turnos/index.html")
    }
});
