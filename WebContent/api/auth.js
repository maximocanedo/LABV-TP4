'use strict';
import { getAccessToken, getRefreshToken, updateSessionToken } from './security.js';

/**
 * Base de todas las URLs de las peticiones hechas con los métodos de este módulo.
 */
export const BASE_URL = "http://localhost:81/TP4_GRUPO3/";

export const resolveUrl = (relativeUrl) => BASE_URL + relativeUrl;

const resolveBody = (body) => {
    if(body == null || body.trim() == "") return "";
    return JSON.stringify(body);
};

const resolveURLParams = (url, params) => {
    const urlObj = new URL(url, BASE_URL); 
    const searchParams = new URLSearchParams(params);
    urlObj.search = searchParams.toString();
    return urlObj.toString();
};

const req = async (relativeUrl, method, body) => {
    const makeRequest = async (token) => {
        const response = await fetch(resolveUrl(relativeUrl), {
            method,
            headers: {
             'Content-Type': 'application/json',
             'Authorization': token
            },
            body: resolveBody(body)
         });
         if(response.headers.has("Authorization")) 
             updateSessionToken(response.headers.get("Authorization"));
         return response;
    };
    const firstTry = await makeRequest(getAccessToken());
    if(firstTry.status == 498) 
        return await makeRequest(getRefreshToken());
    return firstTry;
};


export const get = async (relativeUrl, params) => await req(resolveURLParams(relativeUrl, params), "GET", "");
export const post = async (relativeUrl, body) => await req(relativeUrl, "POST", body);
export const patch = async (relativeUrl, body) => await req(relativeUrl, "PATCH", body);
export const put = async (relativeUrl, body) => await req(relativeUrl, "PUT", body);
export const del = async (relativeUrl, body) => await req(relativeUrl, "DELETE", body);
