'use strict';

/**
 * Cambia el manejo de eventos por promesas.
 * @template T
 * @param {IDBRequest<T>} idbRequest Consulta.
 * @returns {Promise<T>}
 */
export const req = async (idbRequest) => {
    return new Promise((resolve, reject) => {
        idbRequest.onsuccess = (ev) => {
            resolve(idbRequest.result);
        };
        idbRequest.onerror = (ev) => {
            reject(idbRequest.error);
        };
    });
};

const STORES = [{
        name: "users",
        update: db => {
            const objectStore = db.createObjectStore("users", { keyPath: 'username' });
            objectStore.createIndex("username", "username", { unique: true });
            objectStore.createIndex("name", "name", { unique: false });
        }
    }, {
        name: "doctors",
        update: db => {
            const objectStore = db.createObjectStore("doctors", { keyPath: 'id' });
            objectStore.createIndex("id", "id", { unique: true });
            objectStore.createIndex("file", "file", { unique: true });
            objectStore.createIndex("name", "name");
            objectStore.createIndex("active", "active");
            objectStore.createIndex("surname", "surname");
            objectStore.createIndex("fullName", ["name", "surname"]);
            objectStore.createIndex("specialty", "specialty.id");
        }
    }, {
        name: "specialties",
        update: db => {
            const objectStore = db.createObjectStore("specialties", { keyPath: 'id' });
            objectStore.createIndex("id", "id", { unique: true });
            objectStore.createIndex("name", "name");
            objectStore.createIndex("description", "description");
        }
    }
];

/**
 * Configura una base de datos local.
 * @param {string} storeName 
 */
export const useDatabase = (storeName) => {
    const DB_NAME = "utn";
    const STORE_NAME = storeName;

    /**
     * @returns {Promise<IDBDatabase>}
     */
    const open = async () => new Promise((resolve, reject) => {
        const request = indexedDB.open(DB_NAME, 9);
        request.onerror = (event) => {
            reject(request.error);
        };
        request.onupgradeneeded = (/** @type {IDBVersionChangeEvent} */ _e) => {
            const db = request.result;
            STORES.map(store => {
                if(!db.objectStoreNames.contains(store.name)) {
                    store.update(db);
                }
            });
        };
        request.onsuccess = (event) => {
            resolve(request.result);
        };
    });

    /**
     * Leer datos.
     * @template T
     * @param {(store: IDBObjectStore, transaction?: IDBTransaction) => T} action Acción a ejecutar cuando se conecte a la DB.
     */
    const read = async (action) => {
        /** @type {IDBDatabase} */
        const db = await open();
        console.log(db);
        const transaction = db.transaction(storeName, "readonly");
        const store = transaction.objectStore(storeName);
        const e = action(store, transaction);
        transaction.commit();
        return e;
    };

    /**
     * Realizar una transacción.
     * @template T
     * @param {(store: IDBObjectStore, transaction?: IDBTransaction) => T} action Acción a ejecutar cuando se conecte a la DB.
     */
    const transact = async (action) => {
        /** @type {IDBDatabase} */
        const db = await open();
        const transaction = db.transaction(storeName, "readwrite");
        const store = transaction.objectStore(storeName);
        const e = action(store, transaction);
        return e;
    };

    return { read, transact };
};

