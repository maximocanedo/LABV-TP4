import { Query, enable, disable } from '../../actions/doctors.js';
import { FilterStatus, login } from "../../actions/users.js";
import * as headerAdminService from "../services/headerAdminService.js";

let dataTableMedicos;
let page = 1;
let searchText = "";
let isLoading = true;
// Barra Filtros //
const ddlEntriesPerPage = document.getElementById("ddlEntriesPerPage");
const txtBuscar = document.getElementById("txtBuscar");
const btnBuscar = document.getElementById("btnBuscar");
const ddlStatusFilter = document.getElementById("ddlStatusFilter");
// Paginacion
const btnPrevPage = document.getElementById("btnPrevPage");
const btnNextPage = document.getElementById("btnNextPage");

(() => {
    const header = headerAdminService.load();
    const navPacientes = document.getElementById("navPacientes");
    const navMedicos = document.getElementById("navMedicos");
    navPacientes.classList.remove("active");
    navMedicos.classList.add("active");
})();

const load = async () => {
    const user = await login("alicia.schimmel", "12345678");
    // @ts-ignore
    const medicos = await new Query().paginate(page, parseInt(ddlEntriesPerPage.value)).filterByStatus(FilterStatus.BOTH).search();
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
                return `<form action="./modificarMedico.html?Id=${row.id}" method="post"><button type="submit" class="btn btn-primary">Modificar Medico</button></form>`;
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
        const loadingSpinner = document.getElementById("loadingSpinner");
        loadingSpinner.classList.add("d-none")
    }
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
        dataTableUpdate();
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
                console.log(error);
            }
        }
    })
    
    btnNextPage.addEventListener("click", async () => {
        page++;
        dataTableMedicos.clear();
        try {
            dataTableUpdate();
        } catch (error) {
            console.log(error);
        }
    })
    
    
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
})

