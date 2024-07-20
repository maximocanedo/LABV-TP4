'use strict';

import { resolveUrl } from "./../auth.js";

/**
 * 
 * @param {string} name 
 * @param {string} defaultValue 
 * @returns {string}
 */
export const getParam = 
    (name, defaultValue = "") => new URLSearchParams(window.location.search).get(name) ?? defaultValue;

export const getNumericParam = 
    (name, defaultValue = 0) => {
        const y = parseInt(getParam(name, new String(defaultValue).valueOf()));
        if(isNaN(y)) return defaultValue;
        return y;
};

export const resolveLocalUrl = (relativeUrl) => {
    const PREFIX = "pages";
    if(window.location.port == '81')
        return resolveUrl(PREFIX + (!relativeUrl.startsWith("/") ? "/": "") + relativeUrl);
    return (!relativeUrl.startsWith("/") ? "/": "") + relativeUrl;
};