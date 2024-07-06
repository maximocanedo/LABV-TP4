'use strict';
/// <reference path="../types/entities.js" />

const DB_NAME = "doctorDB";
const STORE_NAME = "doctors";

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
            const objectStore = db.createObjectStore(STORE_NAME, { keyPath: 'id', autoIncrement: true });
            objectStore.createIndex("name", "name", { unique: false });
            objectStore.createIndex("specialty", "specialty", { unique: false });
          }
        };
    });
};

/**
 * Actualiza un doctor en la base de datos local y devuelve una copia.
 * @param {Doctor} doctor Doctor con información actualizada
 * @returns {Promise<Doctor>} Copia del doctor almacenado en la base de datos.
 */
export const update = async (doctor) => {
    const db = await open();
    const legacyDoctor = await getById(doctor.id);

    return new Promise((resolve, reject) => {
        doctor._lastOfflineSaved = Date.now();
        const transaction = db.transaction(STORE_NAME, "readwrite");
        const store = transaction.objectStore(STORE_NAME);
        const updatedDoctor = {...legacyDoctor, ...doctor};
        const request = store.put(updatedDoctor);
        request.onsuccess = () => {
            resolve(updatedDoctor);
        };
        request.onerror = (event) => {
            reject("Error al actualizar el doctor");
        };
    });
};

/**
 * Busca un doctor en la base de datos local por su ID.
 * @param {number} id ID del doctor
 * @returns {Promise<Doctor>} Doctor encontrado.
 */
export const getById = async (id) => {
    const db = await open();
  
    return new Promise((resolve, reject) => {
      const transaction = db.transaction(STORE_NAME, "readonly");
      const store = transaction.objectStore(STORE_NAME);
      const getRequest = store.get(id);
  
      getRequest.onsuccess = () => {
        const doctor = getRequest.result;
        resolve(doctor ? doctor : null);
      };
  
      getRequest.onerror = (event) => {
        reject("Error al buscar el doctor por ID");
      };
    });
};

/**
 * Devuelve todos los doctores guardados localmente, sin ningún tipo de filtro ni paginación.
 * @returns {Promise<Doctor[]>}
 */
export const getAll = async () => {
    const db = await open();
    return new Promise((resolve, reject) => {
        const transaction = db.transaction(STORE_NAME, "readonly");
        const store = transaction.objectStore(STORE_NAME);
        const request = store.getAll();

        request.onsuccess = (event) => {
            resolve(event.target.result);
        };

        request.onerror = (event) => {
            reject("Error al obtener los doctores");
        };
    });
};

/**
 * Crea un nuevo doctor en la base de datos local.
 * @param {Doctor} doctor Doctor a crear
 * @returns {Promise<Doctor>} Doctor creado.
 */
export const create = async (doctor) => {
    const db = await open();

    return new Promise((resolve, reject) => {
        const transaction = db.transaction(STORE_NAME, "readwrite");
        const store = transaction.objectStore(STORE_NAME);
        const request = store.add(doctor);

        request.onsuccess = (event) => {
            resolve(doctor);
        };

        request.onerror = (event) => {
            reject("Error al añadir el doctor");
        };
    });
};

/**
 * Función de ejemplo para buscar un doctor por nombre.
 * @param {string} name Nombre del doctor a buscar
 * @returns {Promise<Doctor[]>} Array de doctores encontrados.
 */
export const findByName = async (name) => {
    const db = await open();

    return new Promise((resolve, reject) => {
        const transaction = db.transaction(STORE_NAME, "readonly");
        const store = transaction.objectStore(STORE_NAME);
        const index = store.index('name');
        const getRequest = index.getAll(name);

        getRequest.onsuccess = () => {
            resolve(getRequest.result);
        };

        getRequest.onerror = (event) => {
            reject("Error al buscar doctores por nombre");
        };
    });
};
