'use strict';
import { getAccessToken, getRefreshToken } from "./security.js";
import { login, getUsers, getUser, update, signup, resetPassword, disable, myself, updateMe, resetMyPassword, disableMe } from "./actions/users.js";
import { domTesterElement } from "./test.js";

(async () => {

    const PRINCIPAL_USER = "alicia.schimmel";
    const SECONDARY_USER = "alicia.schimmel";
    const CREATED_USER = "hector.silva";

    const printTokens = domTesterElement("Imprimir tokens", "Imprime en consola los tokens almacenados en memoria. ", e => {
        console.log("Access token: ", getAccessToken());
        console.log("Refresh token: ", getRefreshToken());
    });

    const loginTest = domTesterElement("Iniciar sesión", `Usa las credenciales del usuario @${PRINCIPAL_USER}. `, async (e) => {
        const xe = await login(PRINCIPAL_USER, "12345678");
        console.log(xe);
    });

    const findUserTest = domTesterElement("Buscar usuario", `Buscar al usuario @${SECONDARY_USER}. `, async (e) => {
        const xe = await getUser(SECONDARY_USER);
        console.log(xe);
    });

    let listUsers__page = 1;
    const listUsers = domTesterElement("Buscar usuarios", "Cargar de a diez usuarios e imprimir en consola. ", async (e) => {
        const xe = await getUsers("", listUsers__page, 10);
        listUsers__page++;
        console.log(xe);
    });

    const updateTest = domTesterElement("Actualizar un usuario", `Busca el usuario @${SECONDARY_USER}, y luego se intenta cambiar su nombre. `, async (e) => {
        const xe = await getUser(SECONDARY_USER);
        xe.name = "Alicia Schimmel";
        const up = await update(SECONDARY_USER, xe);
        console.log(up);
    });

    
    const signupTest = domTesterElement("Registrar un usuario", "Intenta registrar un usuario sin permisos, y lo imprime en pantalla. ", async (e) => {
        const xe = await signup({
            username: CREATED_USER,
            name: "Héctor Alejandro Silva",
            password: "12345678",
            doctor: null
        });

        console.log(xe);
    });

    const resetPasswordTest = domTesterElement(
        "Cambiar contraseña", "Del usuario de prueba @" + CREATED_USER, async (e) => {
            const bool = await resetPassword(CREATED_USER, "01234567");
            console.log(`La contraseña ${bool?"":"no "}se cambió ${bool&&"correctamente"}. `);
        }
    );

    const disableTest = domTesterElement(
        "Deshabilitar usuario", "Deshabilita el usuario de prueba @" + CREATED_USER, async (e) => {
            const bool = await disable(CREATED_USER);
            console.log(`El usuario ${bool?"":"no "}se deshabilitó ${bool&&"correctamente"}. `);
        }
    );

    const myselfTest = domTesterElement(
        "Obtener usuario actual", "Imprime en consola el usuario autenticado. ", async (e) => {
            console.log(await myself());
        }
    );

    const editMe = domTesterElement(
        "Cambiar el nombre del usuario actual", "Imprime en consola el usuario actualizado. ", async (e) => {
            let me = (await myself());
            me.name = "Campeón del Mundo";
            console.log(await updateMe(me));
        }
    );

    const resetMyPasswordTest = domTesterElement(
        "Cambiar mi contraseña", "Cambia la contraseña del usuario autenticado", async (e) => {
            const bool = await resetMyPassword(PRINCIPAL_USER, "12345678", "01234567");
            console.log(`La contraseña ${bool?"":"no "}se cambió ${bool&&"correctamente"}. `);
        }
    );

    const disableMyself = domTesterElement(
        "Deshabilitarme", "Deshabilita el usuario autenticado", async (e) => {
            const bool = await disableMe();
            console.log(`${bool?"T":"No t"}e deshabilitaste ${bool&&"exitosamente"}. `);
        }
    );



    document.addEventListener('onConnectionFailure', (event) => {
        // @ts-ignore
        console.error('Problema de conexión:', event.detail);
        // Mostrar notificación, manejar acá el error de conexión a internet.!
    });

    document.addEventListener('onAPIException', (event) => {
        // @ts-ignore
        console.warn('Excepción de API:', event.detail);
    });
    

})();