'use strict';

export const updateRefreshToken = (refreshToken) => {
    localStorage.setItem("_rT", refreshToken);
};

export const updateAccessToken = (accessToken) => {
    localStorage.setItem("_aT", accessToken);
};

export const updateSessionToken = (token) => {
    const [header, content] = token.split(" ");
    if(content !== undefined) {
        (header == "Refresh") && updateRefreshToken(token);
        (header == "Bearer") && updateAccessToken(token);
    }
};

const parseJwt = (token) => {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map((c) => {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload); 
    } catch (error) {
        console.error('Error decodificando JWT: ', error);
        return null;
    }
};

const isTokenExpired = (token) => {
    const payload = parseJwt(token);
    if (!payload || !payload.exp) {
        return true; 
    }
    const currentTime = Math.floor(Date.now() / 1000);
    return payload.exp < currentTime;
};

export const getRefreshToken = () => localStorage.getItem("_rT");
export const getAccessToken = () => localStorage.getItem("_aT");
export const getToken = () => {
    if(getAccessToken() == null || isTokenExpired(getAccessToken())) {
        console.warn("Token de ACCESO expirado: Se autenticará con el token de REFRESCO disponible. ");
        return getRefreshToken();
    }
    console.log("Autenticando solicitud con token de ACCESO.");
    return getAccessToken();
};