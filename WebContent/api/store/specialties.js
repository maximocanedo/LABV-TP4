'use strict';
import { useDatabase, req } from "./index.js";
/// <reference path="../types/entities.js" />

const db = useDatabase("specialties");

export const update = async (specialty) => db.transact(store => 
    req(store.get(specialty.id)).then(legacySpecialty => {
    const updated = { ...legacySpecialty, ...specialty, _lastOfflineSaved: Date.now() };
    if(!specialty.active) {
        return req(store.delete(specialty.id)).then(() => specialty);
    }
    return req(store.put(updated)).then(() => updated);
}));

export const getById = async (id) => db.read(store => req(store.get(id)));