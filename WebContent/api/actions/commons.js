'use strict';

import * as u from './../auth.js';

const q = s => document.querySelector(s);

export const button = selector => /** @type {HTMLButtonElement} */(q(selector));
export const div = selector => /** @type {HTMLDivElement} */(q(selector));
export const input = selector => /** @type {HTMLInputElement} */(q(selector));
export const select = selector => /** @type {HTMLSelectElement} */(q(selector));
export const a = selector => /** @type {HTMLAnchorElement} */(q(selector));

/**
 * Clase para construir y ejecutar consultas de búsqueda de registros.
 * @template T Entidad.
 * @example 
 * // Búsqueda simple:
 * const list: Promise<T[]> = await new Query("Algo").search();
 * 
 * @example
 * // Búsqueda con filtros:
 * const query: Query = new Query("Algo");
 * query
 *   .filterByStatus(FilterStatus.ONLY_ACTIVE)
 *   .filterByDay("MONDAY")
 *   .filterBySpecialty(1)
 *   .paginate(2, 5);
 * const list: Promise<T[]> = await query.search();
 */
export class GenericQuery {
    
    #q;
    #status = "";
    #page = 1;
    #size = 10;
    #localDatabase = null;
    #APIPrefix = "commons";

    /**
     * Crea una nueva instancia de Query.
     * @param {string} q Texto de búsqueda.
     */
    constructor(q = "") {
        this.#q = q;
    }

    setQueryText(q) {
        this.#q = q;
        return this;
    }

    setLocalDatabase(localDatabase) {
        this.#localDatabase = localDatabase;
        return this;
    }

    setPrefix(prefix = "commons") {
        this.#APIPrefix = prefix;
        return this;
    }

    fromSelector = false;
    isSelector(s) {
        this.fromSelector = s;
        return this;
    }


    getParams() {
        return {
            q: this.#q,
            status: this.#status,
            page: this.#page,
            size: this.#size,
            fromSelector: this.fromSelector
        };
    }

    #getCleanedParams() {
        const params = this.getParams();
        const keys = Object.keys(params);
        let newParams = {};
        keys.forEach(key => {
            if(params[key] != undefined && params[key] != null) newParams[key] = params[key];
        });
        return newParams;
    }

    /**
     * Ejecuta la búsqueda de registros según los parámetros configurados.
     * @returns {Promise<T[]>} Promesa con un array de registros encontrados.
     */
    async search() {
        const params = this.#getCleanedParams();
        return u.get(this.#APIPrefix, params)
            .then(response => response.json())
            /*.then(results => {
                const updatePromises = results.map(result => this.#localDatabase.update(result));
                return Promise.all(updatePromises);
            }) */
            .catch(err => {
                throw err;
            });
    }

    async next() {
        this.paginate(this.#page + 1, this.#size);
        return this.search();
    }

    async prev() {
        this.paginate((this.#page == 1 ? 1 : this.#page - 1), this.#size);
        return this.search();
    }

    /**
     * Configura la paginación para la búsqueda.
     * @param {number} page Número de página.
     * @param {number} size Tamaño de la página.
     * @returns {GenericQuery} Instancia actual de GenericQuery.
     */
    paginate(page, size) {
        if (page != null && page > 0) this.#page = page;
        if (size != null && size > 0) this.#size = size;
        return this;
    }

    /**
     * Filtra los registros por estado.
     * @param {string} status Estado por el cual filtrar.
     * @returns {GenericQuery} Instancia actual de GenericQuery.
     */
    filterByStatus(status) {
        this.#status = status;
        return this;
    }
};