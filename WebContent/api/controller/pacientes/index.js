import { Query, enable, disable } from '../../actions/patients.js';
import { FilterStatus, login } from "../../actions/users.js";
import * as headerAdminService from "../services/headerAdminService.js";

let dataTablePacientes;

(() => {
    const header = headerAdminService.load();
})();

(async () => {
    const user = await login("alicia.schimmel", "12345678");
    const pacientes = await new Query().paginate(0, 10).filterByStatus(FilterStatus.BOTH).search();    
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
            { data: '', render: function ( data, type, row ) {
                return `<form action="./modificarPaciente.html?Id=${row.id}" method="post"><button type="submit" class="btn btn-primary">Modificar Paciente</button></form>`;
            }},
            { data: '', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary" onclick="${row.active ? "disable" : "enable"}Patient(${row.id});">${row.active ? "Desactivar" : "Activar"}</button>`;
            }}
        ],
        data: pacientes,
    });
})();

const dataTableUpdate = async () => {
    const actualPage = dataTablePacientes.page();
    dataTablePacientes.clear();
    const pacientes = await new Query().paginate(0, 10).filterByStatus(FilterStatus.BOTH).search();    
    dataTablePacientes.rows.add(pacientes);
    dataTablePacientes.draw()
    dataTablePacientes.page(actualPage).draw("page");
}


const enablePatient = async (id) => {
    const enableResponse = await enable(id).then(() => {
        dataTableUpdate()
    })
    
}

const disablePatient = async (id) => {
    const disableResponse = await disable(id).then(() => {
        dataTableUpdate()
    })
}

// @ts-ignore
window.enablePatient = enablePatient;
// @ts-ignore
window.disablePatient = disablePatient;