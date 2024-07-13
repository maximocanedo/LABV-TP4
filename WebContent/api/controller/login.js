import { login, myself, logout } from "./../actions/users.js";

const navLogout = document.getElementById("navLogout");
const navLogin = document.getElementById("navLogin");
const ddMenu = document.getElementById("ddMenu");
const ddMenuButton = document.getElementById("ddMenuButton");
const navRegister = document.getElementById("navRegister");
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
        ddMenu.className = "dropdown";
        ddMenuButton.innerText = user.username;
        navLogin.className = "nav-link d-none";
        navRegister.className = "nav-link d-none";
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
