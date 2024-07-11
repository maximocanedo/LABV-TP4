import { findAll } from '../api/actions/appointments.js';
import { login } from "../api/actions/users.js";

addEventListener("DOMContentLoaded", async (event) => {
    const user = await login("alicia.schimmel", "12345678");
    const turnos = await findAll();
    
    // @ts-ignore
    new DataTable('#tableListadoTurnos', {
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'patient', title: 'Paciente' },
            { data: 'doctor', title: 'Doctor' },
            { data: 'date', title: 'Fecha' },
            { data: 'notes', title: 'Notas' },
            { data: 'active', title:'Activo'},
            { data: 'id', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary" onclick="${data}">Modificar</button>`;
            }}
        ],
        data: turnos,
    });
    
});

