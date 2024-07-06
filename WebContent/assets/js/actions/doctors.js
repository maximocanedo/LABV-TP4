'use strict';
import * as u from '../../../api/auth.js';
import { FilterStatus } from '../../../api/actions/users.js';

export const create = async (data) => {
    return u.post("http://localhost:81/TP4_GRUPO3/doctors", data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

export const findAll = async () => {
    return u.get("http://localhost:81/TP4_GRUPO3/doctors")
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

export const findById = async (id) => {
    return u.get("http://localhost:81/TP4_GRUPO3/doctors/id/" + id)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

export const findByFile = async (file) => {
    return u.get("http://localhost:81/TP4_GRUPO3/doctors/file/" + file)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

export const update = async (id, data) => {
    return u.patch("http://localhost:81/TP4_GRUPO3/doctors/id/" + id, data)
        .then(response => response.json())
        .catch(err => {
            throw err;
        });
};

export const disable = async (id) => {
    const response = await u.del("http://localhost:81/TP4_GRUPO3/doctors/id/" + id);
    return response.ok;
};

export const enable = async (id) => {
    const response = await u.post("http://localhost:81/TP4_GRUPO3/doctors/id/" + id);
    return response.ok;
};

export class Query {
    #q;
    #status = "";
    #day = "";
    #specialty = null;
    #page = 1;
    #size = 10;

    constructor(q = "") {
        this.#q = q;
    }

    async search() {
        return u.get("http://localhost:81/TP4_GRUPO3/doctors", {
            q: this.#q,
            status: this.#status,
            day: this.#day,
            specialty: this.#specialty,
            page: this.#page,
            size: this.#size
        }).then(response => response.json())
        .catch(err => {
            throw err;
        });
    }

    paginate(page, size) {
        if(page != null && page > 0) this.#page = page;
        if(size != null && size > 0) this.#size = size;
        return this;
    }

    filterByStatus(status) {
        this.#status = status;
        return this;
    }

    filterByDay(day) {
        this.#day = day;
        return this;
    }

    filterBySpecialty(specialty) {
        this.#specialty = specialty;
        return this;
    }
};
