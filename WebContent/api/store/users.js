'use strict';
import { useDatabase, req } from "./index.js";
import * as doctors from "./doctors.js";
/// <reference path="../types/entities.js" />

const db = useDatabase("users");

/**
 * Actualiza un elemento en la base de datos local y devuelve una copia.
 * @param {IUser} user Usuario con información actualizada
 * @returns {Promise<IUser>} Copia del elemento almacenado en la base de datos.
 */
export const update = async (user) => {
	let doctor = null;
	if(user.doctor != null) 
		doctor = await doctors.update(user.doctor);
	return db.transact(store => 
		req(store.get(user.username)).then(legacyUser => {
			const updatedUser = { ...legacyUser, ...user, ...(doctor != null && { doctor }), _lastOfflineSaved: Date.now() };
			if(!user.active) {
				return req(store.delete(user.username)).then(() => user);
			}
			return req(store.put(updatedUser)).then(() => updatedUser);
		})
	);
}

/**
 * Busca un usuario en la base de datos interna del navegador.
 * @param {string} username Nombre de usuario
 * @returns {Promise<IUser>} Usuario encontrado.
 */
export const getByUsername = async (username) => db.read(store => req(store.index('username').get(username)));
  
/**
 * Devuelve todos los usuarios guardados localmente, sin ningún tipo de filtro ni paginación.
 * @returns {Promise<IUser[]>}
 */
export const getAll = async () => db.read(store => req(store.getAll()));

/**
 * Obtener usuarios de la base de datos local.
 * @param {number} page Número de página.
 * @param {number} size Tamaño de página.
 * @returns {Promise<UserMinimalView[]>} Lista de usuarios.
 */
export const getPage = async (page = 1, size = 15) => db.read((store) => {
	const results = [];
	let counter = 0;
	const start = (page - 1) * size;

	return new Promise((resolve, reject) => {
		const request = store.openCursor();
		request.onsuccess = (event) => {
			/** @type {IDBCursorWithValue} */
			const cursor = request.result;

			if (cursor) {
				if (counter >= start && results.length < size) {
					results.push(cursor.value);
				}
				counter++;
				cursor.continue();
			} else {
				resolve(results);
			}
		};

		request.onerror = () => {
			reject("Error al obtener los usuarios");
		};
	});
});
