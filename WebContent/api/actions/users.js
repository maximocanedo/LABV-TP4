'use strict';
import { resolveLocalUrl } from './../lib/commons.js';
import * as u from './../auth.js';
import { updateAccessToken,
     updateRefreshToken } from './../security.js';
import * as db from "./../store/users.js";
import { Permit, PermitTemplate } from './../types/models.js';
import { GenericQuery } from './commons.js';
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
    window.location.href = resolveLocalUrl('/login?next=/users');
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
 * Comprueba si existe una cuenta con el nombre de usuario especificado.
 * @param {string} username Nombre de usuario.
 * @returns {Promise<boolean>}
 */
export const existsByUsername = async (username) => {
    try {
        const response = await u.head(`users/u/${username}`);
        return response.status == 200;
    } catch(err) {
        console.error(err);
    }
}

/**
 * @extends {GenericQuery<IUser>}
 */
export class Query extends GenericQuery {

    unassigned = false;

    constructor(q = "") {
        super(q);
        super.setLocalDatabase(db);
        super.setPrefix("users");
    }

    filterByUnassigned(x) {
        this.unassigned = x;
        return this;
    }

    getParams() {
        return {
            ...super.getParams(),
            checkUnassigned: this.unassigned ? "true" : "false"
        };
    }
}

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
            if(true) { // TODO: Actualizar condición
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
 * Habilita un usuario.
 * @param {string} username Nombre de usuario.
 * @returns {Promise<boolean>} Booleano indicando si se deshabilitó correctamente.
 */
export const enable = async (username) => {
    const response = await u.post("users/u/" + username);
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
        .then(user => {
            document.querySelectorAll('[user-link]').forEach((/** @type {HTMLAnchorElement} */a) => {
                a.setAttribute("href", resolveLocalUrl("/users/manage"));
                a.innerText = `${user.name} (@${user.username})`;
                a.classList.remove("d-none");
            });
            document.querySelectorAll('[user-logout]').forEach((/** @type {HTMLAnchorElement} */a) => {
                a.classList.remove("d-none");
            });
            document.querySelectorAll('[hide-if-no-user-logged-in]').forEach(e => {
                e.classList.add("d-none");
            });
            return user;
        })
        .catch(err => {
            document.querySelectorAll('[user-link]').forEach((/** @type {HTMLAnchorElement} */a) => {
                a.classList.add("d-none");
            });
            document.querySelectorAll('[user-logout]').forEach((/** @type {HTMLAnchorElement} */a) => {
                a.classList.add("d-none");
            });
            document.querySelectorAll('[show-if-no-user-logged-in]').forEach(e => {
                e.classList.remove("d-none");
            });
            throw err;
        });
};

document.querySelectorAll('[user-logout]').forEach((/** @type {HTMLAnchorElement} */a) => {
    a.addEventListener('click', () => {
        logout();
    });
});

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
    .then(json => {
        if(!json.error) return json;
        else throw json.err;
    })
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


export const permitDocs = {
	CREATE_SPECIALTY: "Crear especialidades",
	UPDATE_SPECIALTY: "Actualizar especialidades",
	READ_DISABLED_SPECIALTY_RECORDS: "Leer especialidades deshabilitadas",
	DISABLE_SPECIALTY: "Deshabilitar especialidades",
	ENABLE_SPECIALTY: "Habilitar especialidades",
	
	// Paciente
	// TODO: Funcionalidad y permisos para leer registros eliminados lógicamente.
	CREATE_PATIENT: "Registrar pacientes",
	READ_PATIENT_PERSONAL_DATA: "Leer datos personales de pacientes", // Para listados y búsqueda específica.
	READ_PATIENT_APPOINTMENTS: "Leer turnos de un paciente", // TODO: Crear funcionalidad de listado de turnos por paciente.
	UPDATE_PATIENT: "Actualizar pacientes",
	DISABLE_PATIENT: "Eliminar pacientes",
	ENABLE_PATIENT: "Habilitar pacientes",
	// Doctor
	CREATE_DOCTOR: "Registrar doctores",
	// Leer datos personales del doctor. No se necesitan permisos para leer un doctor.
	READ_DOCTOR: "Leer datos personales de un doctor",
	READ_DOCTOR_APPOINTMENTS: "Leer turnos de un doctor",
	UPDATE_DOCTOR_PERSONAL_DATA: "Actualizar datos de doctor",
	UPDATE_DOCTOR_SCHEDULES: "Actualizar horarios de doctor", // Reconsiderar
	DISABLE_DOCTOR: "Deshabilitar doctores",
	ENABLE_DOCTOR: "Habilitar doctores",
	// Appointment
	CREATE_APPOINTMENT: "Crear turno",
	READ_APPOINTMENT: "Leer turno", // Puede aparecer información del paciente o médico relacionados.
	UPDATE_APPOINTMENT: "Actualizar turno",
	DISABLE_APPOINTMENT: "Deshabilitar turno",
	ENABLE_APPOINTMENT: "Habilitar turno",
	// UserPermit
	GRANT_PERMISSIONS: "Conceder permisos a otros usuarios",
	// User
	ASSIGN_DOCTOR: "Asignar un doctor a un usuario", // Reconsiderar
	RESET_PASSWORD: "Cambiar la contraseña de otro usuario",
	UPDATE_USER_DATA: "Actualizar información de usuario",
	DELETE_OR_ENABLE_USER: "Eliminar o rehabilitar un usuario",
	READ_USER_DATA: "Leer datos de usuario",
	// Ticket
	READ_USER_SESSIONS: "Ver sesiones abiertas",
	CLOSE_USER_SESSIONS: "Cerrar sesiones de un usuario", 
    UPDATE_TICKET: "Actualizar ticket", 
    CREATE_TICKET: "Crear ticket", 
    ENABLE_TICKET: "Habilitar ticket"
}; //*/

export const PERMIT = {
    CREATE_SPECIALTY: "CREATE_SPECIALTY",
    UPDATE_SPECIALTY: "UPDATE_SPECIALTY",
    READ_DISABLED_SPECIALTY_RECORDS: "READ_DISABLED_SPECIALTY_RECORDS",
    DISABLE_SPECIALTY: "DISABLE_SPECIALTY",
    ENABLE_SPECIALTY: "ENABLE_SPECIALTY",
    CREATE_PATIENT: "CREATE_PATIENT",
    READ_PATIENT_PERSONAL_DATA: "READ_PATIENT_PERSONAL_DATA",
    READ_PATIENT_APPOINTMENTS: "READ_PATIENT_APPOINTMENTS",
    UPDATE_PATIENT: "UPDATE_PATIENT",
    DISABLE_PATIENT: "DISABLE_PATIENT",
    ENABLE_PATIENT: "ENABLE_PATIENT",
    CREATE_DOCTOR: "CREATE_DOCTOR",
    READ_DOCTOR: "READ_DOCTOR",
    READ_DOCTOR_APPOINTMENTS: "READ_DOCTOR_APPOINTMENTS",
    UPDATE_DOCTOR_PERSONAL_DATA: "UPDATE_DOCTOR_PERSONAL_DATA",
    UPDATE_DOCTOR_SCHEDULES: "UPDATE_DOCTOR_SCHEDULES",
    DISABLE_DOCTOR: "DISABLE_DOCTOR",
    ENABLE_DOCTOR: "ENABLE_DOCTOR",
    CREATE_APPOINTMENT: "CREATE_APPOINTMENT",
    READ_APPOINTMENT: "READ_APPOINTMENT",
    UPDATE_APPOINTMENT: "UPDATE_APPOINTMENT",
    DISABLE_APPOINTMENT: "DISABLE_APPOINTMENT",
    ENABLE_APPOINTMENT: "ENABLE_APPOINTMENT",
    GRANT_PERMISSIONS: "GRANT_PERMISSIONS",
    ASSIGN_DOCTOR: "ASSIGN_DOCTOR",
    RESET_PASSWORD: "RESET_PASSWORD",
    UPDATE_USER_DATA: "UPDATE_USER_DATA",
    DELETE_OR_ENABLE_USER: "DELETE_OR_ENABLE_USER",
    READ_USER_DATA: "READ_USER_DATA",
    READ_USER_SESSIONS: "READ_USER_SESSIONS",
    CLOSE_USER_SESSIONS: "CLOSE_USER_SESSIONS",
    UPDATE_TICKET: "UPDATE_TICKET",
    CREATE_TICKET: "CREATE_TICKET",
    ENABLE_TICKET: "ENABLE_TICKET"
};