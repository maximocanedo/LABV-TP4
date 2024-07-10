import { login } from "../api/actions/users.js";

const txtUsername = document.getElementById("txtUsername");
const txtPassword = document.getElementById("txtPassword");
const btnLogin = document.getElementById("btnLogin");
const formLogin = document.getElementById("formLogin")
/*txtUsername.addEventListener("invalid", (event) => {
    txtUsername.setCustomValidity("Nombre de usuario")
})
*/
formLogin.addEventListener("submit", async (event) => {
    event.preventDefault()
    // @ts-ignore
    if (!formLogin.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }
    formLogin.classList.add('was-validated');
    /*
    const loginResponse = await login(txtUsername.value, txtPassword.value);
    console.log(loginResponse);
    location.href = "http://localhost/Administrador/AsignarTurno.html";
    */
}, false);