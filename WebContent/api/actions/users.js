'use strict';
import * as u from './../auth.js';
import { updateAccessToken, updateRefreshToken } from './../security.js';
import * as db from "./../store/users.js";
import { Permit, PermitTemplate } from './../types/models.js';
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
};

/**
 * Cerrar sesión. 
 */
export const logout = () => {
    updateAccessToken(null);
    updateRefreshToken(null);
};

/**
 * Crea una cuenta.
 * @param {SignUpRequest} request Formulario de solicitud de alta de cuenta.
 * @returns {Promise<User>} Usuario creado.
 */
export const signup = async (request) => {
    const response = await u.post("users", request);
    return response.json();
};

/**
 * Busca usuarios a partir de los parámetros dados.
 * Si hay errores de conexión, intenta recuperar los usuarios en la base de datos interna.
 * 
 * Importante: Cuando se consulta a la base de datos local, **no se aplica ningún tipo de filtro**, salvo la paginación.
 * 
 * @param {String} q Texto de búsqueda
 * @param {number} page Número de página (Comenzando en 1)
 * @param {number} size Tamaño de página (Por defecto en 15)
 * @param {FilterStatus} status Tipo de filtro de estado. Por defecto se filtran sólo {@link FilterStatus.ONLY_ACTIVE elementos activos}.
 * @returns {Promise<UserMinimalView[]>} Array con usuarios.
 */
export const getUsers = async (q, page = 1, size = 15, status = FilterStatus.ONLY_ACTIVE) => {
    return u.get("users", { q, page, size, status })
    .then(response => response.json())
    .then(users => {
        /** @type {IUser[]} **/
        const updatePromises = users.map(user =>
            db.update(user).then(updatedUser => {
                return updatedUser;
            })
        );
        return Promise.all(updatePromises);
    })
    .catch(error => {
        console.error("Error al obtener o actualizar usuarios:", error);
        throw error;
    });

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
                    throw e;
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
            throw err;
        });
};

/**
 * Cambia la contraseña de un usuario.
 * @param {string} username Nombre de usuario
 * @param {string} newPassword Nueva contraseña
 * @returns {Promise<boolean>} Booleano indicando si se cambió correctamente.
 */
export const resetPassword = async (username, newPassword) => {
    const response = await u.post("users/u/"+username+"/reset-password", { newPassword });
    return response.ok;
};

/**
 * Deshabilita un usuario.
 * @param {string} username Nombre de usuario.
 * @returns {Promise<boolean>} Booleano indicando si se deshabilitó correctamente.
 */
export const disable = async (username) => {
    const response = await u.del("users/u/" + username);
    return response.ok;
};

/**
 * Devuelve la información del usuario actualmente autenticado.
 * @returns {Promise<User>} Usuario autenticado.
 */
export const myself = async () => {
    return u.get("users/me")
        .then(response => {
            if(!response.ok) {
                response.json().then(x => {
                    throw x;
                });
                return null;
            }
            return response.json();
        })
        .then(user => db.update(user))
        .catch(err => {
            console.error(err);
            throw err;
        });
};

/**
 * Actualiza la información del usuario actualmente autenticado.
 * Tiene el mismo efecto que realizar {@link update update}.
 * @param {User} updatedInfo Información actualizada.
 * @see {@link update update(string, IUser)}
 */
export const updateMe = async (updatedInfo) => {
    return u.put("users/me", updatedInfo)
        .then(response => response.json())
        .then(user => db.update(user))
        .catch(err => {
            console.error(err);
            throw err;
        });
};

/**
 * Cambia la contraseña del usuario actual.
 * @param {string} username Nombre de usuario
 * @param {string} password Contraseña actual
 * @param {string} newPassword Nueva contraseña
 * @returns {Promise<boolean>} Resultado de la operación.
 */
export const resetMyPassword = async (username, password, newPassword) => {
    const response = await u.post("users/me/reset-password", { username, password, newPassword });
    return response.ok;
};

/**
 * Deshabilita el usuario autenticado.
 * @returns {Promise<boolean>} Resultado de la operación.
 */
export const disableMe = async () => {
    const response = await u.del("users/me");
    return response.ok;
};

/**
 * Concede un permiso a un usuario.
 * @param {string} username Nombre de usuario
 * @param {Permit} permit Permiso a conceder
 * @returns {Promise<UserPermit>}
 */
export const grantOne = async (username, permit) => {
    return u.post("users/u/" + username + "/grant/p/" + permit)
    .then(response => response.json())
    .catch(err => {
        throw err;
    });
};

/**
 * Deniega un permiso a un usuario.
 * @param {string} username Nombre de usuario
 * @param {Permit} permit Permiso a denegar
 * @returns {Promise<UserPermit>}
 */
export const denyOne = async (username, permit) => {
    return u.post("users/u/" + username + "/deny/p/" + permit)
    .then(response => response.json())
    .catch(err => {
        throw err;
    });
};

/**
 * Concede todos los permisos a un usuario.
 * @param {string} username Nombre de usuario
 * @returns {Promise<boolean>} Resultado de la operación.
 */
export const grantAll = async (username) => {
    const response = await u.post("users/u/" + username + "/grant/all");
    return response.ok;
};

/**
 * Deniega todos los permisos a un usuario.
 * @param {string} username Nombre de usuario
 * @returns {Promise<boolean>} Resultado de la operación.
 */
export const denyAll = async (username) => {
    const response = await u.post("users/u/" + username + "/deny/all");
    return response.ok;
};

/**
 * Concede todos los permisos a un usuario.
 * @param {string} username Nombre de usuario
 * @param {PermitTemplate} permitTemplate Rol a asignar / Plantilla de permisos.
 * @returns {Promise<boolean>} Resultado de la operación.
 */
export const grantTemplate = async (username, permitTemplate) => {
    const response = await u.post("users/u/" + username + "/grant/t/" + permitTemplate);
    return response.ok;
};