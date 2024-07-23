import { Toast } from './../../lib/toast.js';
import { Query, enable, disable } from '../../actions/appointments.js';
import * as headerDoctorService from "../services/headerDoctorService.js";
import { FilterStatus } from './../../actions/users.js';

let dataTableAppointments;
let page = 1;
let searchText = "";
let isLoading = true;
// Barra Filtros //
const ddlEntriesPerPage = document.getElementById("ddlEntriesPerPage");
const txtBuscar = document.getElementById("txtBuscar");
const btnBuscar = document.getElementById("btnBuscar");
const ddlStatusFilter = document.getElementById("ddlStatusFilter");
const ddlStatusFilterAppointment = document.getElementById("ddlStatusFilterAppointment");
// Table
const loadingSpinner = document.getElementById("loadingSpinner");
// Paginacion
const btnPrevPage = document.getElementById("btnPrevPage");
const btnNextPage = document.getElementById("btnNextPage");

(() => {
    const header = headerDoctorService.load();
})();

const load = async () => {
    
    const appointments = await new Query()
        .paginate(page, parseInt(ddlEntriesPerPage.value))
        .filterByAppointmentStatus(ddlStatusFilterAppointment.value)
        .filterByStatus(FilterStatus[ddlStatusFilter.value])
        .search();    
    // @ts-ignore
    
    dataTableAppointments = new DataTable('#tableListadoAppointments', {
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'assignedDoctor.name', title: 'Doctor Asignado' },
            { data: 'date', title: 'Fecha Turno', render: function ( data, type, row ) {
                const dateEpoch = new Date(data);
                const date = new Date(dateEpoch).toISOString().split("T")[0];
                return date;
            }},
            { data: 'patient.name', title: 'Paciente Asignado' },
            { data: 'patient.dni', title: 'DNI Paciente' },
            { data: 'remarks', title: 'Observaciones'},
            { data: 'status', title: 'Estado' },
            { data: 'active', render: function ( data, type, row ) {
                return data ? "Activo" : "Inactivo";
            }},
            { data: '', render: function ( data, type, row ) {
                return `<form action="./manage/index.html?Id=${row.id}" method="post"><button type="submit" class="btn btn-primary">Modificar Turno</button></form>`;
            }},
            { data: '', render: function ( data, type, row ) {
                return `<button type="button" class="btn btn-primary" onclick="${row.active ? "disable" : "enable"}Appointment(${row.id});">${row.active ? "Desactivar" : "Activar"}</button>`;
            }}
        ],
        data: appointments,
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
        dataTableAppointments.clear();
        const appointments = await new Query()
            .setQueryText(searchText)
            // @ts-ignore
            .paginate(page, parseInt(ddlEntriesPerPage.value))
            // @ts-ignore
            .filterByAppointmentStatus(ddlStatusFilterAppointment.value)
            .filterByStatus(FilterStatus[ddlStatusFilter.value])
            .search();
        dataTableAppointments.rows.add(appointments);
        dataTableAppointments.draw();
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

    ddlStatusFilterAppointment.onchange = () => {
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
            dataTableAppointments.clear();
            try {
                dataTableUpdate()
            } catch (error) {
                console.log(error);
            }
        }
    })
    
    btnNextPage.addEventListener("click", async () => {
        page++;
        dataTableAppointments.clear();
        try {
            dataTableUpdate()
        } catch (error) {
            console.log(error);
        }
    })
    
    
    const enableAppointment = async (id) => {
        const enableResponse = await enable(id).then(() => {
            dataTableUpdate();            
            notification.setText("Turno Activado");
            notification.setWarnColor(false);
            notification.show();
        })
    }
    
    const disableAppointment = async (id) => {
        const disableResponse = await disable(id).then(() => {
            dataTableUpdate();
            notification.setText("Turno Desactivado");
            notification.setWarnColor(true);
            notification.show();
        })
    }
    
    // @ts-ignore
    window.enableAppointment = enableAppointment;
    // @ts-ignore
    window.disableAppointment = disableAppointment;
});

