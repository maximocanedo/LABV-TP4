import * as doctorActions from './actions/doctors.js';

document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('register-form');
    const doctorsContainer = document.getElementById('doctors-container');

    loadDoctors();

    registerForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const form = event.target; 
        if (!(form instanceof HTMLFormElement)) {
            throw new Error('Unexpected event target');
        }

        const formData = new FormData(form);

        const doctorData = {
            schedules: [
                {
                    id: 727,
                    beginDay: "FRIDAY",
                    finishDay: "FRIDAY",
                    startTime: [6, 17],
                    endTime: [11, 17],
                    active: true
                },
                {
                    id: 728,
                    beginDay: "SUNDAY",
                    finishDay: "SUNDAY",
                    startTime: [14, 0],
                    endTime: [17, 0],
                    active: true
                },
                {
                    id: 729,
                    beginDay: "THURSDAY",
                    finishDay: "FRIDAY",
                    startTime: [16, 4],
                    endTime: [3, 4],
                    active: true
                },
                {
                    id: 730,
                    beginDay: "MONDAY",
                    finishDay: "MONDAY",
                    startTime: [17, 44],
                    endTime: [18, 44],
                    active: true
                },
                {
                    id: 731,
                    beginDay: "SATURDAY",
                    finishDay: "SUNDAY",
                    startTime: [20, 15],
                    endTime: [0, 15],
                    active: true
                }
            ],
            file: formData.get('file'),
            name: formData.get('name'),
            surname: formData.get('surname'),
            sex: formData.get('sex'),
            birth: formData.get('birth'),
            address: formData.get('address'),
            localty: formData.get('localty'),
            email: formData.get('email'),
            phone: formData.get('phone'),
            specialty: {
                id: formData.get('specialty')
            }
        };

        try {
            const doctor = await doctorActions.create(doctorData);
            console.log('Doctor registrado:', doctor);
            displayDoctor(doctor);
        } catch (error) {
            console.error('Error al registrar el doctor:', error);
        }
    });

    function displayDoctor(doctor) {
        const doctorsContainer = document.querySelector('#doctors-container');
        const doctorElement = document.createElement('div');
        doctorElement.innerHTML = `
            <p>ID: ${doctor.id}</p>
            <p>Nombre: ${doctor.name} ${doctor.surname}</p>
            <p>Email: ${doctor.email}</p>
            <p>Teléfono: ${doctor.phone}</p>
            <p>Especialidad: ${doctor.specialty.id}</p>
        `;
        doctorsContainer.appendChild(doctorElement);
    }

    async function loadDoctors() {
        try {
            const doctors = await doctorActions.findAll();
            doctors.forEach(doctor => appendDoctorToList(doctor));
        } catch (error) {
            console.error('Error al cargar los doctores:', error);
        }
    }

    function appendDoctorToList(doctor) {
        const doctorElement = document.createElement('div');
        doctorElement.className = 'doctor';
        doctorElement.innerHTML = `
            <p><strong>ID:</strong> ${doctor.id}</p>
            <p><strong>Nombre:</strong> ${doctor.name} ${doctor.surname}</p>
            <p><strong>Email:</strong> ${doctor.email}</p>
            <p><strong>Teléfono:</strong> ${doctor.phone}</p>
            <p><strong>Especialidad:</strong> ${doctor.specialty.id}</p>
        `;
        doctorsContainer.appendChild(doctorElement);
    }
});
