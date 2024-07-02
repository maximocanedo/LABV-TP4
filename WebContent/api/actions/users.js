'use strict';
import * as u from './../auth.js';
import { updateAccessToken } from './../security.js';
import * as db from "./../store/users.js";
/// <reference path="../types/entities.js" />

/**
 * Opciones de filtro de estado de objetos.
 * @enum {string}
 */
export const FilterStatus = Object.freeze({

    /**
     * Sólo elementos habilitados o activos.
     */
    ONLY_ACTIVE: "ONLY_ACTIVE",

    /**
     * Sólo elementos deshabilitados o inactivos.
     */
    ONLY_INACTIVE: "ONLY_INACTIVE",

    /**
     * Todos los elementos.
     */
    BOTH: "BOTH"
});

/**
 * Inicia sesión. 
 * @param {string} username Nombre de usuario
 * @param {string} password Contraseña
 * @returns {Promise<Response>} Respuesta del servidor.
 */
export const login = async (username, password) => {
    updateAccessToken(null);
    return u.post("users/login", { username, password });
}

/**
 * Busca usuarios a partir de los parámetros dados.
 * Si hay errores de conexión, intenta recuperar los usuarios en la base de datos interna.
 * 
 * @param {String} q Texto de búsqueda
 * @param {number} page Número de página (Comenzando en 1)
 * @param {number} size Tamaño de página (Por defecto en 15)
 * @param {FilterStatus} status Tipo de filtro de estado. Por defecto se filtran sólo {@link FilterStatus.ONLY_ACTIVE elementos activos}.
 * @returns {Promise<UserMinimalView[]>} Array con usuarios.
 */
export const getUsers = async (q, page = 1, size = 15, status = FilterStatus.ONLY_ACTIVE) => {
    try {
        const response = await u.get("users", { q, page, size, status });
        const users = await response.json();
        const updatePromises = users.map(async (user) => {
            const updatedUser = await db.update(user);
            console.log({ updatedUser });
            return updatedUser;
        });
        return await Promise.all(updatePromises);
    } catch (error) {
        console.error("Error al obtener o actualizar usuarios:", error);
        throw error;
    }
};

/**
 * Busca un usuario por su nombre de usuario.
 * @param {string} username Nombre de usuario.
 * @returns {Promise<IUser>} Usuario.
 */
export const getUser = async (username) => {
    return u.get("users/u/"+username)
        .then(response => response.json())
        .then(user => db.update(user))
        .catch(err => {
            if(true) { // Actualizar condición
                return db.getByUsername(username).then(user => {
                    return user;
                }).catch(e => {
                    console.error(e);
                });
            }
        });
};

/**
 * Actualiza los datos de un usuario en la base de datos.
 * @param {string} username Nombre de usuario
 * @param {User} user Usuario a actualizar.
 * @returns {Promise<User>} Usuario actualizado.
 */
export const update = async (username, user) => {
    return u.put("users/u/"+username, user)
        .then(response => response.json())
        .then(user => db.update(user))
        .catch(err => {
            console.error(err);
        });
};


