import { Toast } from './../../lib/toast.js';
import { Query, enable, disable } from '../../actions/patients.js';
import { FilterStatus, login } from "../../actions/users.js";
import * as headerAdminService from "../services/headerAdminService.js";
import { control } from "./../../controller/web.auth.js";

(async () => {
    // @ts-ignore
    window.me = await control(true, []);
})();
let dataTablePacientes;
let page = 1;
let searchText = "";
let isLoading = true;
// Barra Filtros //
const ddlEntriesPerPage = document.getElementById("ddlEntriesPerPage");
const txtBuscar = document.getElementById("txtBuscar");
const btnBuscar = document.getElementById("btnBuscar");
const ddlStatusFilter = document.getElementById("ddlStatusFilter");
// Table
const loadingSpinner = document.getElementById("loadingSpinner");
// Paginacion
const btnPrevPage = document.getElementById("btnPrevPage");
const btnNextPage = document.getElementById("btnNextPage");

(() => {
    
})();

const load = async () => {
   // const user = await login("alicia.schimmel", "12345678");
    // @ts-ignore
    const pacientes = await new Query().paginate(page, parseInt(ddlEntriesPerPage.value)).filterByStatus(FilterStatus[ddlStatusFilter.value]).search();    
    // @ts-ignore
    dataTablePacientes = new DataTable('#tableListadoPacientes', {
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'name', title: 'Nombre' },
            { data: 'surname', title: 'Apellido' },
            { data: 'dni', title: 'Dni' },
            { data: 'active', render: function ( data, type, row ) {
                return data ? "Activo" : "Inactivo";
            }},
            { data: '', render: function ( data, type, row ) {
                return `<form action="./manage/?id=${row.id}" method="post"><button type="submit" class="btn btn-primary">Ver</button></form>`;
            }} /*,
            { data: '', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary" onclick="${row.active ? "disable" : "enable"}Patient(${row.id});">${row.active ? "Desactivar" : "Activar"}</button>`;
            }} */
        ],
        data: pacientes,
        paging: false,
        searching: false,
    });
    btnPrevPage.classList.add("disabled");
};

load().then(() => {
    isLoading = false;
    if(!isLoading){
        loadingSpinner.classList.add("d-none")
    }

    const notification = new Toast({id: "notification"});

    const dataTableUpdate = async () => {
        dataTablePacientes.clear();
        const pacientes = await new Query()
            .setQueryText(searchText)
            // @ts-ignore
            .paginate(page, parseInt(ddlEntriesPerPage.value))
            // @ts-ignore
            .filterByStatus(FilterStatus[ddlStatusFilter.value])
            .search();
        dataTablePacientes.rows.add(pacientes);
        dataTablePacientes.draw();
        if(page == 1){
            btnPrevPage.classList.add("disabled");
        } else {
            btnPrevPage.classList.remove("disabled")
        }
    }
    
    ddlEntriesPerPage.onchange = () => {
        page = 1;
        loadingSpinner.classList.remove("d-none");
        isLoading = true;
        dataTableUpdate().then(()=>{
            isLoading = false;
            if(!isLoading){
                loadingSpinner.classList.add("d-none");
            }
        });
    };

    ddlStatusFilter.onchange = () => {
        page = 1;
        dataTableUpdate();
    };

    btnBuscar.addEventListener("click", async () => {
        page = 1;
        // @ts-ignore
        searchText = txtBuscar.value;
        dataTableUpdate();
    });
    
    btnPrevPage.addEventListener("click", async () => {
        if (page > 1) {
            page--;
            dataTablePacientes.clear();
            try {
                dataTableUpdate()
            } catch (error) {
                console.log(error);
            }
        }
    })
    
    btnNextPage.addEventListener("click", async () => {
        page++;
        dataTablePacientes.clear();
        try {
            dataTableUpdate()
        } catch (error) {
            console.log(error);
        }
    })
    
    
    const enablePatient = async (id) => {
        const enableResponse = await enable(id).then(() => {
            dataTableUpdate();            
            notification.setText("Paciente Activado");
            notification.setWarnColor(false);
            notification.show();
        })
    }
    
    const disablePatient = async (id) => {
        const disableResponse = await disable(id).then(() => {
            dataTableUpdate();
            notification.setText("Paciente Desactivado");
            notification.setWarnColor(true);
            notification.show();
        })
    }
    
    // @ts-ignore
    window.enablePatient = enablePatient;
    // @ts-ignore
    window.disablePatient = disablePatient;
});

