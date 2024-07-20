'use strict';
import { ElementBuilder } from "./../../controller/dom.js";
import * as users from "./../../actions/users.js";
import { getNumericParam, getParam, resolveLocalUrl } from "./../../lib/commons.js";

const nextBtn = document.querySelector("#nextBtn");
const btnSearch = document.querySelector("#btnSearch");
/** @type {HTMLInputElement} */
const qText = document.querySelector("#user_q");
/** @type {HTMLSelectElement} */
const fs = document.querySelector("#filterStatus__user");
let page = getNumericParam("p", 1);
let size = getNumericParam("size", 10);
let q = getParam("q");

/** @type {users.Query} */
const query = new users.Query()
                    .paginate(page, size)
                    .setQueryText(q);

/**
 * @param {IUser} user 
 */
const userListElement = user => {
    const li = new ElementBuilder("a")
                    .classList("list-group-item", "list-group-item-action")
                    .click((e, el) => {
                        window.location.href = resolveLocalUrl("/users/manage?u=" + user.username);
                    });
    const meAuto = new ElementBuilder("div")
                        .classList("ms-2", "me-auto")
                        .appendTo(li.getTarget());
    const ttl = new ElementBuilder("div")
                        .classList("fw-bold")
                        .text(user.name)
                        .appendTo(meAuto.getTarget());
    const a = new ElementBuilder("div")
                    .href()
                    .text('@' + user.username)
                    .appendTo(meAuto.getTarget());
    if(!user.active) {
        const st = new ElementBuilder("small")
                    .classList("text-muted")
                    .text("Deshabilitado")
                    .appendTo(meAuto.getTarget())
    }
    return li;
};
const clearResults = () => {
    document.querySelector(".results").innerHTML = "";
    query.paginate(1, size);
}
const loadResults = results => {
    results.map(result => {
        document.querySelector(".results").append(userListElement(result).getTarget());
    });
};

const load = async () => query.search().then(loadResults).catch(console.error);
const next = async () => query.next().then(loadResults).catch(console.error);

nextBtn.addEventListener("click", next);
btnSearch.addEventListener("click", async () => {
    clearResults();
    await load();
});
fs.addEventListener("change", e => {
    query.filterByStatus(fs.value);
});
qText.addEventListener("change", e => {
    query.setQueryText(qText.value);
});

(async () => {
    await load();
})();