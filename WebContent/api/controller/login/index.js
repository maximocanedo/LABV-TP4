import { login } from "./../../actions/users.js";
import { ElementBuilder } from "./../dom.js";
import { resolveUrl } from "./../../auth.js";
import { toastAPIErrors } from "./../../actions/commons.js";

const txtUsername = document.getElementById("txtUsername");
const txtPassword = document.getElementById("txtPassword");
const txtUsernameEB = ElementBuilder.from(txtUsername);
const txtPasswordEB = ElementBuilder.from(txtPassword);
const btnLogin = ElementBuilder.from(document.getElementById("btnLogin"));
const formLogin = ElementBuilder.from(document.getElementById("formLogin"));

export const resolveLocalUrl = (relativeUrl) => {
    const PREFIX = "";
    if(window.location.port == '81')
        return resolveUrl(PREFIX + (!relativeUrl.startsWith("/") ? "/": "") + relativeUrl);
    return (!relativeUrl.startsWith("/") ? "/": "") + relativeUrl;
}

const onValid = () => {
    clearVal();
    const search = new URLSearchParams(window.location.search);
    const next = search.get("next")?? "/";
    window.location.href = resolveLocalUrl(next);
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
            toastAPIErrors(err);
            onInvalid(err.error);
        });
};

btnLogin.click(async (ev, el) => {
    //formLogin.classList('was-validated');
    await tryLogin();
});

