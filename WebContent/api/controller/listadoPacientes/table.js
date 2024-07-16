'use strict';

import { ElementBuilder } from "./../../controller/dom.js";

export const load = (listaObjetos) => {
    const keys = Object.keys(listaObjetos[0]);

    const table = new ElementBuilder("table")
        .classList("table");
    const thead = new ElementBuilder("thead")
    .appendTo(table.getTarget());
    const trHead = new ElementBuilder("tr")
        .appendTo(thead.getTarget());

    keys.forEach(key => {
        const th = new ElementBuilder("th")
        .appendTo(trHead.getTarget()).text(key.toLocaleUpperCase());
    });

    const tbody = new ElementBuilder("tbody")
    .appendTo(table.getTarget());
    listaObjetos.forEach(objeto => {
        const trBody = new ElementBuilder("tr")
        .appendTo(tbody.getTarget());
        keys.forEach(key => {
            const td = new ElementBuilder("td")
            .appendTo(trBody.getTarget()).text(objeto[key]);
        });
    });
    return table;
};

/*
<table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">First</th>
      <th scope="col">Last</th>
      <th scope="col">Handle</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">1</th>
      <td>Mark</td>
      <td>Otto</td>
      <td>@mdo</td>
    </tr>
    <tr>
      <th scope="row">2</th>
      <td>Jacob</td>
      <td>Thornton</td>
      <td>@fat</td>
    </tr>
    <tr>
      <th scope="row">3</th>
      <td colspan="2">Larry the Bird</td>
      <td>@twitter</td>
    </tr>
  </tbody>
</table>
*/