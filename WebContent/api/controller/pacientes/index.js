import { Query, enable, disable } from '../../actions/patients.js';
import { FilterStatus, login } from "../../actions/users.js";
import * as headerAdminService from "../services/headerAdminService.js";

let dataTablePacientes;

(() => {
    const header = headerAdminService.load();
})();

(async () => {
    const user = await login("alicia.schimmel", "12345678");
    const pacientes = await new Query().paginate(1, 100).filterByStatus(FilterStatus.BOTH).search();    
    // @ts-ignore
    dataTablePacientes = new DataTable('#tableListadoPacientes', {
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'name', title: 'Nombre' },
            { data: 'surname', title: 'Apellido' },
            { data: 'dni', title: 'Dni' },
            { data: 'email', title: 'Email'},
            { data: 'phone', title: 'Telefono' },
            { data: 'active', render: function ( data, type, row ) {
                return data ? "Activo" : "Inactivo";
            }},
            { data: 'id', render: function ( data, type, row ) {
                return `<form action="./modificarPaciente.html?Id=${data}" method="post"><button type="submit" class="btn btn-primary">Modificar Paciente</button></form>`;
            }},
            { data: 'id', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary" onclick="enablePatient(${data});">Activar</button>`;
            }},
            { data: 'id', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary" onclick="disablePatient(${data});">Desactivar</button>`;
            }}
        ],
        data: pacientes,
    });
})();

const dataTableUpdate = async () => {
    const actualPage = dataTablePacientes.page();
    dataTablePacientes.clear();
    const pacientes = await new Query().paginate(1, 100).filterByStatus(FilterStatus.BOTH).search();    
    dataTablePacientes.rows.add(pacientes);
    dataTablePacientes.draw()
    dataTablePacientes.page(actualPage).draw("page");
}


const enablePatient = async (id) => {
    const enableResponse = await enable(id);
    dataTableUpdate()
}

const disablePatient = async (id) => {
    const disableResponse = await disable(id);
    dataTableUpdate();
}

// @ts-ignore
window.enablePatient = enablePatient;
// @ts-ignore
window.disablePatient = disablePatient;