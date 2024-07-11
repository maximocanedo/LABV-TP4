import { login, myself } from "../api/actions/users.js";

const txtUsername = document.getElementById("txtUsername");
const txtPassword = document.getElementById("txtPassword");
const btnLogin = document.getElementById("btnLogin");
const formLogin = document.getElementById("formLogin");

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const user = await myself();
    } catch (error) {
        console.log(error);
        formLogin.className = "flex-fill needs-validation";
    }
})

formLogin.addEventListener("submit", async (event) => {
    event.preventDefault()
    // @ts-ignore
    if (!formLogin.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
    }
    formLogin.classList.add('was-validated');
    // @ts-ignore
    const loginResponse = await login(txtUsername.value, txtPassword.value);
    location.reload()
}, false);
