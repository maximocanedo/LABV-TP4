<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="./../../assets/css/commons.css" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
    crossorigin="anonymous"></script>
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
    <div class="wrapper d-none"> <!-- Dentro de este elemento todo lo necesario para control de horarios -->
        <br><br>
        <div class="sselector">

        </div>
        <div class="mselector">

        </div>
        <select name="" id="fecha">

        </select><button id="nextMonth">Siguiente mes</button>
        <select name="" id="hora"></select>
        <br><br>
    </div>
    <main class="container container-sm">
        <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
              <button class="nav-link active " id="nav-personal-info-tab" data-bs-toggle="tab" data-bs-target="#nav-personal-info" type="button" role="tab" aria-controls="nav-personal-info" aria-selected="false">Información básica</button>
              <button class="nav-link" id="nav-update-info-tab" data-bs-toggle="tab" data-bs-target="#nav-update-info" type="button" role="tab" aria-controls="nav-update-info" aria-selected="false">Acciones</button>
            </div>
          </nav>
          <div class="tab-content" id="nav-tabContent">
            <div class="tab-pane fade show active" id="nav-personal-info" role="tabpanel" aria-labelledby="nav-personal-info-tab" tabindex="0">
                <div class="list-group list-group-flush">
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">ID N.º:</div>
                            <div class="col col-6" data-field="appointment.id"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Paciente:</div>
                            <div class="col col-6 patientLinkHere" data-field="appointment.dni"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Doctor:</div>
                            <div class="col col-6 doctorLinkHere" data-field="appointment.doctor"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Estado del turno:</div>
                            <div class="col col-6" data-field="appointment.status"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Observaciones:</div>
                            <div class="col col-6" data-field="appointment.remarks"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Estado lógico:</div>
                            <div class="col col-6" data-field="appointment.active"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Estado local del registro:</div>
                            <div class="col col-6" data-field="localState"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Última vez descargado:</div>
                            <div class="col col-6" data-field="lastTimeLocallyUpdated"></div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="tab-pane fade" id="nav-update-info" role="tabpanel" aria-labelledby="nav-update-info-tab" tabindex="0">
                <br>
                <div class="card mb-3 disable mb-3 hidable-if-disabled">
                    <div class="card-body">
                        <h5 class="card-title">Cambiar estado</h5>
                        <div class="form-check">
                            <input class="form-check-input stateChk" type="radio" name="status" id="pendienteChk" checked>
                            <label class="form-check-label" for="pendienteChk">
                              Pendiente
                            </label>
                          </div>
                          <div class="form-check">
                            <input class="form-check-input stateChk" type="radio" name="status" id="ausenteChk">
                            <label class="form-check-label" for="ausenteChk">
                              Ausente
                            </label>
                          </div>
                          <div class="form-check mb-3">
                            <input class="form-check-input stateChk" type="radio" name="status" id="presenteChk">
                            <label class="form-check-label" for="presenteChk">
                              Presente
                            </label>
                          </div>
                          <div class="mb-3 d-none">
                            <label for="txtRemarks" class="form-label">Observaciones: </label>
                            <textarea class="form-control" data-field="appointment.remarks" id="txtRemarks" rows="3"></textarea>
                          </div>
                        <button class="btn btn-primary" id="btnChangeStatus">Actualizar</button>
                    </div>
                </div>
                <div class="card mb-3 disable">
                    <div class="card-body">
                        <h5 class="card-title">Deshabilitar o habilitar</h5>
                        <button class="btn btn-danger" id="btnState" data-field="appointment.active.switchButton"></button>
                    </div>
                </div>
            </div>
          </div>
    </main>
    <style>
        .spaced_chk {
            margin-right: 10px;
        }
    </style>
    <script type="module" src="./../../api/controller/appointments/manage/index.js"></script>
</body>
</html>