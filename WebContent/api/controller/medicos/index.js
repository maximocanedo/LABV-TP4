import { Query, enable, disable } from '../../actions/doctors.js';
import { FilterStatus, login } from "../../actions/users.js";
import * as headerAdminService from "../services/headerAdminService.js";

let dataTableMedicos;

(() => {
    const header = headerAdminService.load();
})();

(async () => {
    const user = await login("alicia.schimmel", "12345678");
    const medicos = await new Query().paginate(1, 100).filterByStatus(FilterStatus.BOTH).search();    
    console.log(medicos)
    // @ts-ignore
    dataTableMedicos = new DataTable('#tableListadoMedicos', {
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'file', title: 'Legajo' },
            { data: 'name', title: 'Nombre' },
            { data: 'surname', title: 'Apellido' },
            { data: 'specialty.name', title: 'Especialidad'},
            { data: 'active', render: function ( data, type, row ) {
                return data ? "Activo" : "Inactivo";
            }},
            { data: 'id', render: function ( data, type, row ) {
                return `<form action="./modificarMedico.html?Id=${data}" method="post"><button type="submit" class="btn btn-primary">Modificar Medico</button></form>`;
            }},
            { data: 'id', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary" onclick="enableDoctor(${data});">Activar</button>`;
            }},
            { data: 'id', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary" onclick="disableDoctor(${data});">Desactivar</button>`;
            }}
        ],
        data: medicos,
    });
})();

const dataTableUpdate = async () => {
    const actualPage = dataTableMedicos.page();
    dataTableMedicos.clear();
    const pacientes = await new Query().paginate(1, 100).filterByStatus(FilterStatus.BOTH).search();    
    dataTableMedicos.rows.add(pacientes);
    dataTableMedicos.draw()
    dataTableMedicos.page(actualPage).draw("page");
}


const enableDoctor = async (id) => {
    const enableResponse = await enable(id);
    dataTableUpdate()
}

const disableDoctor = async (id) => {
    const disableResponse = await disable(id);
    dataTableUpdate();
}

// @ts-ignore
window.enableDoctor = enableDoctor;
// @ts-ignore
window.disableDoctor = disableDoctor;