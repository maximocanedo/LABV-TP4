import { login, myself, logout } from "../api/actions/users.js";

const navLogout = document.getElementById("navLogout");
const navLogin = document.getElementById("navLogin");
const txtUsername = document.getElementById("txtUsername");
const txtPassword = document.getElementById("txtPassword");
const btnLogin = document.getElementById("btnLogin");
const formLogin = document.getElementById("formLogin");

navLogout.addEventListener("click", () => {
    logout();
    location.reload();
});

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const user = await myself();
        navLogout.className = "nav-link";
        navLogin.className = "nav-link d-none";
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
