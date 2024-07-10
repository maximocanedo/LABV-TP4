import { signup } from "../api/actions/users.js";

const formRegister = document.getElementById("formRegister");

const txtName = document.getElementById("txtName");
const txtSurname = document.getElementById("txtSurname");
const txtUsername = document.getElementById("txtUsername");
const txtEmail = document.getElementById("txtEmail");
const txtBirthDate = document.getElementById("txtBirthDate");
const txtGenre = document.getElementById("txtGenre");
const txtDireccion = document.getElementById("txtDireccion");
const txtLocalty = document.getElementById("txtLocalty");
const txtPhone = document.getElementById("txtPhone");

formRegister.addEventListener("submit", async (event) => {
    event.preventDefault()
    // @ts-ignore
    if (!formRegister.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
    }
    formRegister.classList.add('was-validated');
    /*
    const registerResponse = await signup();
    console.log(registerResponse);
    location.href = "http://localhost/Administrador/AsignarTurno.html";
    */
}, false);