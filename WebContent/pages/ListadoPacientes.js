import { findAll } from '../api/actions/patients.js';
import { login, myself } from "../api/actions/users.js";

addEventListener("DOMContentLoaded", async (event) => {
    try {
        const user = await myself();
        if (!user.doctor) {
            location.href = "listadoTurnos.html"
        }
    } catch (error) {
        location.href = "login.html"
    }
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
            { data: 'active', title:'Activo'},
            { data: '', render: function ( data, type, row ) {
                return '<button type="button" class="btn btn-primary">Example Button</button>';
            }}
        ],
        data: pacientes,
    });
    
});

