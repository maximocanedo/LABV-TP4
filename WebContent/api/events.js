'use strict';
/// <reference path="./types/entities" />

/**
 * El evento onConnectionFailure se lanza cuando una solicitud HTTP no 
 * puede realizarse correctamente por problemas de conexión.
 * @param {String} url URL de la solicitud.
 * @param {string} method Método HTTP de la solicitud.
 * @param {JSON} body Cuerpo de la solicitud.
 * @param {TypeError} error Excepción obtenida.
 */
export const emitConnectionFailure = (url, method, body, error) => {
    const event = new CustomEvent('onConnectionFailure', {
        detail: { url, method, body, error }
    });
    document.dispatchEvent(event);
};

/**
 * El evento onAPIException se lanza cuando el servidor responde con un error controlado.
 * @param {CommonException} error Error devuelto por el servidor.
 */
export const emitAPIException = (error) => {
    const event = new CustomEvent('onAPIException', {
        detail: error
    });
    document.dispatchEvent(event);
};