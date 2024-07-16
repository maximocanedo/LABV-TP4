import { Query } from '../../actions/patients.js';
import { login } from "../../actions/users.js";
import * as headerAdminService from "../services/headerAdminService.js";
import * as table from "./table.js";

(() => {
    const header = headerAdminService.load();
})();

(async () => {
    const user = await login("alicia.schimmel", "12345678");
    const pacientes = await new Query().paginate(1, 100).search();    
    // @ts-ignore
    new DataTable('#tableListadoPacientes', {
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'name', title: 'Nombre' },
            { data: 'surname', title: 'Apellido' },
            { data: 'dni', title: 'Dni' },
            { data: 'email', title: 'Email'},
            { data: 'phone', title: 'Telefono' },
            { data: 'active', title:'Activo'},
            { data: 'id', render: function ( data, type, row ) {
                return `<form action="./modificarPaciente.html?Id=${data}" method="post"><button type="submit" class="btn btn-primary">Modificar Paciente</button></form>`;
            }},
            { data: 'id', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary">Activar</button>`;
            }},
            { data: 'id', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary">Desactivar</button>`;
            }}
        ],
        data: pacientes,
    });
    
    /*
    const user = await login("alicia.schimmel", "12345678");

    const tableContainer = document.getElementById("tableContainer");
    let pacientes = await new Query().paginate(1, 25).search();
    tableContainer.appendChild(table.load(pacientes).build());
    console.log(pacientes);
    */
})();
