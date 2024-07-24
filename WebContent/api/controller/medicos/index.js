import { Toast } from './../../lib/toast.js';
import { Query, enable, disable } from '../../actions/doctors.js';
import { FilterStatus } from "../../actions/users.js";
import * as headerAdminService from "../services/headerAdminService.js";
import { control } from "./../../controller/web.auth.js";
import { PERMIT } from "./../../actions/users.js";
import { toastAPIErrors } from './../../actions/commons.js';

(async () => {
    // @ts-ignore
    window.me = await control(true, [PERMIT.READ_DOCTOR, PERMIT.CREATE_APPOINTMENT, PERMIT.UPDATE_APPOINTMENT, PERMIT.UPDATE_APPOINTMENT]);
})();
let dataTableMedicos;
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
    try {
        const header = headerAdminService.load();
        const navPacientes = document.getElementById("navPacientes");
        const navMedicos = document.getElementById("navMedicos");
        navPacientes.classList.remove("active");
        navMedicos.classList.add("active");
    } catch(expected) {

    }
})();

const load = async () => {
    // const user = await login("alicia.schimmel", "12345678");
    // @ts-ignore
    const medicos = await new Query().paginate(page, parseInt(ddlEntriesPerPage.value)).filterByStatus(FilterStatus[ddlStatusFilter.value]).search();
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
            { data: '', render: function ( data, type, row ) {
                return `<form action="./manage/?id=${row.id}" method="post"><button type="submit" class="btn btn-primary">Ver</button></form>`;
            }},
            { data: '', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary" onclick="${row.active ? "disable" : "enable"}Doctor(${row.id});">${row.active ? "Desactivar" : "Activar"}</button>`;
            }}
        ],
        data: medicos,
        paging: false,
        searching: false
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
        dataTableMedicos.clear();
        const medicos = await new Query()
            .setQueryText(searchText)
            // @ts-ignore
            .paginate(page, parseInt(ddlEntriesPerPage.value))
            // @ts-ignore
            .filterByStatus(FilterStatus[ddlStatusFilter.value])
            .search();
        dataTableMedicos.rows.add(medicos);
        dataTableMedicos.draw()
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
            dataTableMedicos.clear();
            try {
                dataTableUpdate();
            } catch (error) {
                toastAPIErrors(error);
            }
        }
    })
    
    btnNextPage.addEventListener("click", async () => {
        page++;
        dataTableMedicos.clear();
        try {
            dataTableUpdate();
        } catch (error) {
            toastAPIErrors(error);
        }
    })
    
    
    const enableDoctor = async (id) => {
        const enableResponse = await enable(id).then(() => {
            dataTableUpdate();            
            notification.setText("Medico Activado");
            notification.setWarnColor(false);
            notification.show();
        })
    }
    
    const disableDoctor = async (id) => {
        const disableResponse = await disable(id).then(() => {
            dataTableUpdate();
            notification.setText("Medico Desactivado");
            notification.setWarnColor(true);
            notification.show();
        })
    }
    
    // @ts-ignore
    window.enableDoctor = enableDoctor;
    // @ts-ignore
    window.disableDoctor = disableDoctor;
})

