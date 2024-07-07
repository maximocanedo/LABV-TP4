import { findAll } from './api/actions/patients.js';
import { login } from "./api/actions/users.js";

addEventListener("DOMContentLoaded", async (event) => {
    const user = await login("alicia.schimmel", "12345678");
    const pacientes = await findAll();
    
    // @ts-ignore
    new DataTable('#tableListadoPacientes', {
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'name', title: 'Nombre' },
            { data: 'surname', title: 'Apellido' },
            { data: 'dni', title: 'Dni' },
            { data: 'email', title: 'Email'},
            { data: 'phone', title: 'Telefono' },
            { data: 'active', title:'Activo' }
        ],
        data: pacientes,
    });
    
});

