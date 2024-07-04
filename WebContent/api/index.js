'use strict';
import { getAccessToken, getRefreshToken } from "./security.js";
import { login, getUsers, getUser, update, signup, resetPassword, disable, myself, updateMe, resetMyPassword, disableMe } from "./actions/users.js";
import * as doctors from './actions/doctors.js';
import { domTesterElement, domSection } from "./test.js";

const usersSection = domSection("Usuarios");
const doctorsSection = domSection("Médicos");
const patientsSection = domSection("Pacientes");

(async (section) => {

    const PRINCIPAL_USER = "alicia.schimmel";
    const SECONDARY_USER = "alicia.schimmel";
    const CREATED_USER = "hector.silva";

    const printTokens = domTesterElement("Imprimir tokens", "Imprime en consola los tokens almacenados en memoria. ", e => {
        console.log("Access token: ", getAccessToken());
        console.log("Refresh token: ", getRefreshToken());
    }, section);

    const loginTest = domTesterElement("Iniciar sesión", `Usa las credenciales del usuario @${PRINCIPAL_USER}. `, async (e) => {
        const xe = await login(PRINCIPAL_USER, "12345678");
        console.log(xe);
    }, section);

    const findUserTest = domTesterElement("Buscar usuario", `Buscar al usuario @${SECONDARY_USER}. `, async (e) => {
        const xe = await getUser(SECONDARY_USER);
        console.log(xe);
    }, section);

    let listUsers__page = 1;
    const listUsers = domTesterElement("Buscar usuarios", "Cargar de a diez usuarios e imprimir en consola. ", async (e) => {
        const xe = await getUsers("", listUsers__page, 10);
        listUsers__page++;
        console.log(xe);
    }, section);

    const updateTest = domTesterElement("Actualizar un usuario", `Busca el usuario @${SECONDARY_USER}, y luego se intenta cambiar su nombre. `, async (e) => {
        const xe = await getUser(SECONDARY_USER);
        xe.name = "Alicia Schimmel";
        const up = await update(SECONDARY_USER, xe);
        console.log(up);
    }, section);
    
    const signupTest = domTesterElement("Registrar un usuario", "Intenta registrar un usuario sin permisos, y lo imprime en pantalla. ", async (e) => {
        const xe = await signup({
            username: CREATED_USER,
            name: "Héctor Alejandro Silva",
            password: "12345678",
            doctor: null
        });

        console.log(xe);
    }, section);

    const resetPasswordTest = domTesterElement(
        "Cambiar contraseña", "Del usuario de prueba @" + CREATED_USER, async (e) => {
            const bool = await resetPassword(CREATED_USER, "01234567");
            console.log(`La contraseña ${bool?"":"no "}se cambió ${bool&&"correctamente"}. `);
        }, section);

    const disableTest = domTesterElement(
        "Deshabilitar usuario", "Deshabilita el usuario de prueba @" + CREATED_USER, async (e) => {
            const bool = await disable(CREATED_USER);
            console.log(`El usuario ${bool?"":"no "}se deshabilitó ${bool&&"correctamente"}. `);
        }, section);

    const myselfTest = domTesterElement(
        "Obtener usuario actual", "Imprime en consola el usuario autenticado. ", async (e) => {
            console.log(await myself());
        }, section);

    const editMe = domTesterElement(
        "Cambiar el nombre del usuario actual", "Imprime en consola el usuario actualizado. ", async (e) => {
            let me = (await myself());
            me.name = "Campeón del Mundo";
            console.log(await updateMe(me));
        }, section);

    const resetMyPasswordTest = domTesterElement(
        "Cambiar mi contraseña", "Cambia la contraseña del usuario autenticado", async (e) => {
            const bool = await resetMyPassword(PRINCIPAL_USER, "12345678", "01234567");
            console.log(`La contraseña ${bool?"":"no "}se cambió ${bool&&"correctamente"}. `);
        }, section);

    const disableMyself = domTesterElement(
        "Deshabilitarme", "Deshabilita el usuario autenticado", async (e) => {
            const bool = await disableMe();
            console.log(`${bool?"T":"No t"}e deshabilitaste ${bool&&"exitosamente"}. `);
        }, section);

    document.addEventListener('onConnectionFailure', (event) => {
        // @ts-ignore
        console.error('Problema de conexión:', event.detail);
        // Mostrar notificación, manejar acá el error de conexión a internet.!
    }, section);

    document.addEventListener('onAPIException', (event) => {
        // @ts-ignore
        console.warn('Excepción de API:', event.detail);
    }, section);    

})(usersSection);

(async (section) => {
    const USER = 'arnette.abbott';
    const SAMPLE_FILE = 418;
    const SAMPLE_ID= 275;
    const createDoctor = domTesterElement("Crear un doctor", `Asignado al usuario @${USER}. `, async (e) => {
        const doctor = await doctors.create({
            file: SAMPLE_FILE,
            name: "Arnette",
            surname: "Abbott",
            specialty: { id: 1 },
            schedules: [
                {
                    beginDay: "MONDAY", 
                    finishDay: "MONDAY",
                    startTime: [10, 40],
                    endTime: [18, 40]
                }
            ]
        });
        console.log(doctor);
    }, section);

    const findDoctor = domTesterElement("Buscar un doctor", `Busca el doctor con legajo N.º ${SAMPLE_FILE}. `, async (e) => {
        const doctor = await doctors.findByFile(SAMPLE_FILE);
        console.log(doctor);
    }, section);

    const findIdDoctor = domTesterElement("Buscar un doctor (Por ID)", `Busca el doctor con ID ${SAMPLE_ID}. `, async (e) => {
        const doctor = await doctors.findById(SAMPLE_ID);
        console.log(doctor);
    }, section);

    const updateDoctor = domTesterElement("Actualizar datos de un doctor. ", "Ahora vive en Tierra del Fuego. ", async (e) => {
        const updatedDoctor = await doctors.update(SAMPLE_ID, {
            address: "Felipe Varela 65",
            localty: "Río Grande",
            sex: "F"
        });
        console.log(updatedDoctor);
    }, section);

    const disableDoctor = domTesterElement("Dar de baja al doctor", "", async (e) => {
        const ok = await doctors.disable(SAMPLE_ID);
        console.log("La operación " + (ok ? "se realizó" : "falló") + " exitosamente. ");
    }, section);

    const enableDoctor = domTesterElement("Rehabilitar al doctor", "", async (e) => {
        const ok = await doctors.enable(SAMPLE_ID);
        console.log("La operación " + (ok ? "se realizó" : "falló") + " exitosamente. ");
    }, section);

})(doctorsSection);