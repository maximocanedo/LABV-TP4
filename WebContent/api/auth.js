'use strict';
import { getAccessToken, getRefreshToken, updateSessionToken } from './security.js';
import { emitAPIException, emitConnectionFailure } from './events.js';

/**
 * Base de todas las URLs de las peticiones hechas con los métodos de este módulo.
 */
export const BASE_URL = "http://localhost:81/TP4_GRUPO3/";

/**
 * Métodos HTTP.
 */
export const HTTP_METHOD = Object.freeze({
    GET: "GET",
    POST: "POST",
    PUT: "PUT",
    PATCH: "PATCH",
    DELETE: "DELETE",
    OPTIONS: "OPTIONS",
    HEAD: "HEAD"
});

/**
 * Resuelve una URL para ser usada en una consulta.
 * @param {string} relativeUrl URL relativa.
 * @returns La URL resuelta.
 */
export const resolveUrl = (relativeUrl) => BASE_URL + relativeUrl;

const resolveBody = (body) => {
    if(body == null || body == "") return "";
    return JSON.stringify(body);
};

export const resolveURLParams = (url, params) => {
    const urlObj = new URL(url, BASE_URL); 
    const searchParams = new URLSearchParams(params);
    urlObj.search = searchParams.toString();
    return urlObj.toString();
};


/**
 * Realiza una petición a la API.
 * @param {string} url URL relativa a la {@link BASE_URL URL Base}.
 * @param {string} method Método HTTP.
 * @param {object} body Cuerpo de la petición.
 * @returns {Promise<Response>} Respuesta del servidor.
 */
const req = async (url, method, body) => {
    const makeRequest = async (token) => {
            const response = await fetch(url, {
                method,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
                ...(method === "GET" || method === "HEAD" ? {} : { body: resolveBody(body) })
            });

            if (response.headers.has("Authorization")) {
                updateSessionToken(response.headers.get("Authorization"));
            }

            return response;
    };

    try {
        console.log("Autenticando solicitud con token de ACCESO.");
        const firstTry = await makeRequest(getAccessToken());
        if (firstTry.status === 498) {
            console.warn("Token de ACCESO expirado: Se autenticará con el token de REFRESCO disponible.");
            return await makeRequest(getRefreshToken());
        }
        if (!firstTry.ok) throw (await firstTry.json());
        return firstTry;
    } catch (error) {
        if(error.name == "TypeError" && error.message == 'Failed to fetch')
            emitConnectionFailure(url, method, body, error);
        if(error.path !== undefined)
            emitAPIException(error);
        throw error; 
    }
};

/**
 * Realiza una petición de tipo GET a la API.
 * @param {String} relativeUrl URL relativa a la {@link BASE_URL URL Base}
 * @param {Object} params Objeto con los parámetros en línea, de ser necesarios.
 * @returns {Promise<Response>} Respuesta del servidor.
 */
export const get = async (relativeUrl, params) => await req(resolveURLParams(relativeUrl, params), HTTP_METHOD.GET, "");

/**
 * Realiza una petición de tipo POST a la API.
 * @param {String} relativeUrl URL relativa a la {@link BASE_URL URL Base}
 * @param {any} body Objeto parseable a JSON, con los parámetros.
 * @returns {Promise<Response>} Respuesta del servidor.
 */
export const post = async (relativeUrl, body) => await req(resolveUrl(relativeUrl), HTTP_METHOD.POST, body);

/**
 * Realiza una petición de tipo PATCH a la API.
 * @param {String} relativeUrl URL relativa a la {@link BASE_URL URL Base}
 * @param {any} body Objeto parseable a JSON, con los parámetros.
 * @returns {Promise<Response>} Respuesta del servidor.
 */
export const patch = async (relativeUrl, body) => await req(resolveUrl(relativeUrl), HTTP_METHOD.PATCH, body);

/**
 * Realiza una petición de tipo PUT a la API.
 * @param {String} relativeUrl URL relativa a la {@link BASE_URL URL Base}
 * @param {any} body Objeto parseable a JSON, con los parámetros.
 * @returns {Promise<Response>} Respuesta del servidor.
 */
export const put = async (relativeUrl, body) => await req(resolveUrl(relativeUrl), HTTP_METHOD.PUT, body);

/**
 * Realiza una petición de tipo DELETE a la API.
 * @param {String} relativeUrl URL relativa a la {@link BASE_URL URL Base}
 * @param {any} body Objeto parseable a JSON, con los parámetros.
 * @returns {Promise<Response>} Respuesta del servidor.
 */
export const del = async (relativeUrl, body) => await req(resolveUrl(relativeUrl), HTTP_METHOD.DELETE, body);
