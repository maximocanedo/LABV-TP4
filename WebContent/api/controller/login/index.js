import { login } from "./../../actions/users.js";
import { ElementBuilder } from "./../dom.js";
import { resolveUrl } from "./../../auth.js";

const txtUsername = document.getElementById("txtUsername");
const txtPassword = document.getElementById("txtPassword");
const txtUsernameEB = ElementBuilder.from(txtUsername);
const txtPasswordEB = ElementBuilder.from(txtPassword);
const btnLogin = ElementBuilder.from(document.getElementById("btnLogin"));
const formLogin = ElementBuilder.from(document.getElementById("formLogin"));

const onValid = () => {
    clearVal();
    const PREFIX = ""; // "pages";
    const HOME = "/";
    const search = new URLSearchParams(window.location.search);
    const next = search.get("next")?? HOME;
    window.location.href = resolveUrl(PREFIX + (!next.startsWith("/") ? "/": "") + next);
};

const clearVal = () => {
    txtUsernameEB.removeClass("is-invalid").removeClass("is-valid");
    txtPasswordEB.removeClass("is-invalid").removeClass("is-valid");
};

const onInvalid = (error) => {
    clearVal();
    txtUsernameEB.classList("is-invalid");
    txtPasswordEB.classList("is-invalid");
    document.getElementById("iF_username").innerText = error.message;
};

const tryLogin = () => {
    clearVal();
    // @ts-ignore
    return login(txtUsername.value, txtPassword.value)
        .then(async (response) => {
            if(!response.ok) {
                const err = await response.json();
                onInvalid(err);
            } else {
                onValid();
            }
        }).catch(err => {
            onInvalid(err.error);
        });
};

btnLogin.click(async (ev, el) => {
    //formLogin.classList('was-validated');
    await tryLogin();
});

