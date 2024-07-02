'use strict';
/// <reference path="../types/entities.js" />

const DB_NAME = "utn";
const STORE_NAME = "users";

const open = () => {
    return new Promise((resolve, reject) => {
        const request = indexedDB.open(DB_NAME, 1);
  
        request.onerror = (event) => {
          reject("Error al abrir la base de datos");
        };
  
        request.onsuccess = (event) => {
          resolve(event.target.result);
        };
  
        request.onupgradeneeded = (event) => {
          const db = event.target.result;
          if (!db.objectStoreNames.contains(STORE_NAME)) {
            const objectStore = db.createObjectStore(STORE_NAME, { keyPath: 'username' });
            objectStore.createIndex("username", "username", { unique: true });
            objectStore.createIndex("name", "name", { unique: false });
          }
        };
    });
};

/**
 * Actualiza un elemento en la base de datos local y devuelve una copia.
 * @param {IUser} user Usuario con información actualizada
 * @returns {Promise<IUser>} Copia del elemento almacenado en la base de datos.
 */
export const update = async (user) => {
    const db = await open();
    const legacyUser = await getByUsername(user.username);

    return new Promise((resolve, reject) => {
        user._lastOfflineSaved = Date.now();
        const transaction = db.transaction(STORE_NAME, "readwrite");
        const store = transaction.objectStore(STORE_NAME);
        const updatedUser = {...legacyUser, ...user};
        const request = store.put(updatedUser);
        request.onsuccess = () => {
            //console.log(request);
            resolve(updatedUser);
        };
        request.onerror = (event) => {
            reject("Error al añadir el usuario");
        };
  
    });
  };

/**
 * Busca un usuario en la base de datos interna del navegador.
 * @param {string} username Nombre de usuario
 * @returns {Promise<IUser>} Usuario encontrado.
 */
export const getByUsername = async (username) => {
    const db = await open();
  
    return new Promise((resolve, reject) => {
      /** @type {IDBTransaction} */
      const transaction = db.transaction(STORE_NAME, "readonly");
      /** @type {IDBObjectStore} */
      const store = transaction.objectStore(STORE_NAME);
      /** @type {IDBIndex} */
      const index = store.index('username');
      /** @type {IDBRequest<IUser>} */
      const getRequest = index.get(username);
  
      getRequest.onsuccess = () => {
        const user = getRequest.result;
        resolve(user ? user : null);
      };
  
      getRequest.onerror = (event) => {
        reject("Error al buscar el usuario por nombre de usuario");
      };
    });
  };
  
/**
 * Devuelve todos los usuarios guardados localmente, sin ningún tipo de filtro ni paginación.
 * @returns {Promise<IUser[]>}
 */
export const getAll = async () => {
    const db = await open();
    return new Promise((resolve, reject) => {
        /** @type {IDBTransaction} */
        const transaction = db.transaction(STORE_NAME, "read");
        const store = transaction.objectStore(STORE_NAME);
        /** @type {IDBRequest<IUser[]>} */
        const request = store.getAll();

        request.onsuccess = (event) => {
            resolve(event);
        };

        request.onerror = (event) => {
            reject("ERR");
        };

    });
};

export const getUsers = async (page = 1, size = 15) => {
  const db = await open();

  return new Promise((resolve, reject) => {
      const transaction = db.transaction(STORE_NAME, "readonly");
      const store = transaction.objectStore(STORE_NAME);

      const results = [];
      let counter = 0;
      const start = (page - 1) * size;

      store.openCursor().onsuccess = (event) => {
          /** @type {IDBCursorWithValue} */
          const cursor = event.target.result;

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

      transaction.onerror = (event) => {
          reject("Error al obtener los usuarios");
      };
  });
};
