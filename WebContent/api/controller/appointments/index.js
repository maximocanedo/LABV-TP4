import { Toast } from './../../lib/toast.js';
import { Query, update, findById } from '../../actions/appointments.js';
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
        .filterByDateBetween(new Date("2025-01-01").toISOString().split("T")[0], new Date("2025-02-01").toISOString().split("T")[0])
        //.filterByDoctor()
        .filterByStatus(FilterStatus.ONLY_ACTIVE)
        .search();
    // @ts-ignore

    dataTableAppointments = new DataTable('#tableListadoAppointments', {
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'assignedDoctor.name', title: 'Doctor Asignado' },
            {
                data: 'date', title: 'Fecha Turno', render: function (data, type, row) {
                    const dateEpoch = new Date(data);
                    const date = new Date(dateEpoch).toISOString().split("T")[0];
                    return date;
                }
            },
            { data: 'patient.name', title: 'Paciente Asignado' },
            { data: 'patient.dni', title: 'DNI Paciente' },
            { data: 'remarks', title: 'Observaciones' },
            { data: 'status', title: 'Estado' },
            {
                data: 'active', render: function (data, type, row) {
                    return data ? "Activo" : "Inactivo";
                }
            },
            {
                data: '', render: function (data, type, row) {
                    return `<button type="button" class="btn btn-success" onclick="presentAppointment(${row.id});">Presente</button>`;
                }
            },
            {
                data: '', render: function (data, type, row) {
                    return `<button type="button" class="btn btn-primary" onclick="ausentAppointment(${row.id});">Ausente</button>`;
                }
            }
        ],
        data: appointments,
        paging: false,
        searching: false,
    });
    btnPrevPage.classList.add("disabled");
};

load().then(() => {
    isLoading = false;
    if (!isLoading) {
        loadingSpinner.classList.add("d-none")
    }

    const notification = new Toast({ id: "notification" });

    const dataTableUpdate = async () => {
        dataTableAppointments.clear();
        const appointments = await new Query()
            .setQueryText(searchText)
            // @ts-ignore
            .paginate(page, parseInt(ddlEntriesPerPage.value))
            .filterByAppointmentStatus(ddlStatusFilterAppointment.value)
            .filterByDateBetween(new Date("2025-01-01").toISOString().split("T")[0], new Date("2025-02-01").toISOString().split("T")[0])
            //.filterByDoctor()
            .filterByStatus(FilterStatus.ONLY_ACTIVE)
            .search();
        dataTableAppointments.rows.add(appointments);
        dataTableAppointments.draw();
        if (page == 1) {
            btnPrevPage.classList.add("disabled");
        } else {
            btnPrevPage.classList.remove("disabled")
        }
    }

    ddlEntriesPerPage.onchange = () => {
        page = 1;
        loadingSpinner.classList.remove("d-none");
        isLoading = true;
        dataTableUpdate().then(() => {
            isLoading = false;
            if (!isLoading) {
                loadingSpinner.classList.add("d-none");
            }
        });
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


    // @ts-ignore
    window.presentAppointment = async (id) => 
        update(id, { 
                status: "PRESENT", 
                remarks: prompt("Observaciones Consulta:") 
            }).then(() => {
                dataTableUpdate();
                notification.setText("Marcado Presente");
                notification.setWarnColor(false);
                notification.show();
            }).catch(console.error);

    // @ts-ignore
    window.ausentAppointment  = async (id) => 
        // @ts-ignore
        update(id, {status: "ABSENT"})
            .then(() => {
                dataTableUpdate();
                notification.setText("Marcado Ausente");
                notification.setWarnColor(true);
                notification.show();
            }).catch(console.error);
    
});

