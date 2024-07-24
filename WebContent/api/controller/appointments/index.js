import { Toast } from './../../lib/toast.js';
import { Query, update, findById } from '../../actions/appointments.js';
import * as headerDoctorService from "../services/headerDoctorService.js";
import { FilterStatus } from './../../actions/users.js';
import { control } from "./../../controller/web.auth.js";
import { PERMIT } from "./../../actions/users.js";

(async () => {
    // @ts-ignore
    window.me = await control(true, []);
})();
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


const load = async () => {

    const appointments = await new Query()
        .filterByAppointmentStatus(ddlStatusFilterAppointment.value)
        .filterByDateBetween(new Date("2025-01-01").toISOString().split("T")[0], new Date("2025-02-01").toISOString().split("T")[0])
        //.filterByDoctor()
        .filterByStatus(FilterStatus.ONLY_ACTIVE)
        .paginate(page, parseInt(ddlEntriesPerPage.value))
        .search();
    // @ts-ignore

    dataTableAppointments = new DataTable('#tableListadoAppointments', {
        columns: [
            { data: 'id', title: 'Id' },
            { data: 'assignedDoctor.name', title: 'Médico' },
            {
                data: 'date', title: 'Fecha Turno', render: function (data, type, row) {
                    const dateEpoch = new Date(data);
                    const date = new Date(dateEpoch).toLocaleString();
                    return date;
                }
            },
            { data: 'patient.name', title: 'Paciente' },
            { data: 'patient.dni', title: '(DNI)' },
            { data: 'statusDescription', title: 'Estado' },
            {
                data: 'active', render: function (data, type, row) {
                    return data ? "Activo" : "Inactivo";
                }
            },
            {
                data: '', render: function (data, type, row) {
                    return `<button type="button" class="btn btn-success" onclick="window.location.href = './manage?id=(${row.id})';">Ver</button>`;
                }
            }/* ,
            {
                data: '', render: function (data, type, row) {
                    return `<button type="button" class="btn btn-primary" onclick="ausentAppointment(${row.id});">Ausente</button>`;
                }
            } */
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
            .filterByAppointmentStatus(ddlStatusFilterAppointment.value)
            .filterByDateBetween(new Date("2025-01-01").toISOString().split("T")[0], new Date("2025-02-01").toISOString().split("T")[0])
            //.filterByDoctor()
            .filterByStatus(FilterStatus.ONLY_ACTIVE)
            // @ts-ignore
            .paginate(page, parseInt(ddlEntriesPerPage.value))
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
        update(id, {
            status: "ABSENT", 
            remarks: ''}
        )
            .then(() => {
                dataTableUpdate();
                notification.setText("Marcado Ausente");
                notification.setWarnColor(true);
                notification.show();
            }).catch(console.error);
    
});

