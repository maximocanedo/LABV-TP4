<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Turno</title>
    <link rel="stylesheet" href="./../../assets/css/commons.css" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
    crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/google-libphonenumber/3.2.37/libphonenumber.min.js" integrity="sha512-MVSgRRDm2gT+ms4budOJYLBXx+mxpntMwSmx2jHAQNof74bIo0/sThKak5+CsjPYP+4WLilz31BzUHst838NYA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
    <header class="bg-dark text-white py-3">
        <div class="container d-flex justify-content-between align-items-center">
            <h1 class="m-0">TIF LAB V</h1>
            <nav>
                <a href="#" user-link class="text-white mx-2 d-none"></a>
                <a href="#" user-logout class="text-white mx-2 d-none">Cerrar sesión</a>
                <a href="#" signup-link show-if-no-user-logged-in class="text-white mx-2 d-none">Registrate</a>
                <a href="#" login-link show-if-no-user-logged-in class="text-white mx-2 d-none">Iniciar sesión</a>
            </nav>
        </div>
    </header>
    <main class="container">
        <h4>Registrar un turno</h4>
        <br>
        <div class="mb-3">
            <label for="specialtySelector" class="form-label">Especialidad:</label>
            <div class="specialtySelector"></div>
            <div class="valid-feedback" id="specialtySelectorValid"></div>
            <div class="invalid-feedback" id="specialtySelectorInvalid"></div>
        </div>
        <div class="mb-3">
            <label for="selectorDoctor" class="form-label">Doctor:</label>
            <div class="selectorDoctor">
                
                <div class="valid-feedback" id="selectorDoctorValid"></div>
                <div class="invalid-feedback" id="selectorDoctorInvalid"></div>
            </div>
        </div>
        <div class="mb-3">
            <label for="selectorPatient" class="form-label">Paciente:</label>
            <div class="selectorPatient">
                <div class="valid-feedback" id="selectorPatientValid"></div>
                <div class="invalid-feedback" id="selectorPatientInvalid"></div>
            </div>
        </div>
        <div class="mb-3 d-none" id="selectorDateWrapper">
            <label for="selectorDate" class="form-label">Fecha:</label>
            <div class="input-group mb-3">
                <button class="btn btn-outline-secondary input-group-text d-none" id="prevMonth">Anterior</button>
                <select name="" class="form-control form-select" id="fecha"></select>
                <button class="btn btn-outline-secondary input-group-text" id="nextMonth" disabled>Siguiente</button>
            </div>
            <div class="valid-feedback" id="selectorDateValid"></div>
            <div class="invalid-feedback" id="selectorDateInvalid"></div>
        </div>
        <div class="mb-3 d-none" id="selectorTimeWrapper">
            <label for="selectorTime" class="form-label">Hora:</label>
            <select name="" class="form-control form-select" id="hora"></select>
            <div class="valid-feedback" id="selectorTimeValid"></div>
            <div class="invalid-feedback" id="selectorTimeInvalid"></div>
        </div>
        <div class="mb-3">
            <button type="button" class="btn btn-success" id="btnAsignarTurno">Asignar Turno</button>
        </div>
    </main>
    <script type="module" src="./../../api/controller/appointments/add/index.js"></script>
</body>
</html>