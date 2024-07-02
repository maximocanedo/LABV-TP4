'use strict';
import { getAccessToken, getRefreshToken } from "./security.js";
import { login, getUsers, getUser, update } from "./actions/users.js";

(async () => {

    document.querySelector("#___rTClick").addEventListener('click', e => {
        console.log("Access token: ", getAccessToken());
        console.log("Refresh token: ", getRefreshToken());
    });

    
    document.querySelector("#___login").addEventListener('click', async (e) => {
        const xe = await login("root", "12345678");
        console.log(xe);
    });

    document.querySelector("#___uTClick").addEventListener('click', async (e) => {
        const xe = await getUsers("");
        console.log(xe);
    });

    document.querySelector("#_ab").addEventListener('click', async (e) => {
        let alicia = "alicia.schimmel";
        // {User}
        const xe = await getUser(alicia);
        console.log(xe);
    });

    document.querySelector("#_abc").addEventListener('click', async (e) => {
        let alicia = "alicia.schimmel";
        
        const xe = await getUser(alicia);
        
        xe.name = "Alicia Schimmel";

        const up = await update(alicia, xe);

        console.log(up);
    });


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