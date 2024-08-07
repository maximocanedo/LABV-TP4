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
            <a href="#" signup-link show-if-no-user-logged-in class="text-white mx-2 d-none">Registro</a>
            <a href="#" login-link show-if-no-user-logged-in class="text-white mx-2 d-none">Administrar</a>
        </nav>
    </div>
</header>
    <main class="container container-sm">
        <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
              <button class="nav-link active" id="nav-personal-info-tab" data-bs-toggle="tab" data-bs-target="#nav-personal-info" type="button" role="tab" aria-controls="nav-personal-info" aria-selected="false">Información básica</button>
              <button class="nav-link" id="nav-update-info-tab" data-bs-toggle="tab" data-bs-target="#nav-update-info" type="button" role="tab" aria-controls="nav-update-info" aria-selected="false">Acciones</button>
              <button class="nav-link hidable-if-disabled" id="nav-appointments-tab" data-bs-toggle="tab" data-bs-target="#nav-appointments" type="button" role="tab" aria-controls="nav-appointments" aria-selected="true">Turnos</button>
            </div>
          </nav>
          <div class="tab-content" id="nav-tabContent">
            <div class="tab-pane fade show active" id="nav-personal-info" role="tabpanel" aria-labelledby="nav-personal-info-tab" tabindex="0">
                <div class="list-group list-group-flush">
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">ID N.º:</div>
                            <div class="col col-6" data-field="doctor.id"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Legajo N.º:</div>
                            <div class="col col-6" data-field="doctor.file"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Nombre:</div>
                            <div class="col col-6" data-field="doctor.name"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Apellido:</div>
                            <div class="col col-6" data-field="doctor.surname"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Usuario vinculado:</div>
                            <div class="col col-6" id="userLinkedWrapper" data-field="doctor.user"></div>
                        </div>
                    </div>
                    <div class="list-group-item" sensible-field="doctor.sex">
                        <div class="row">
                            <div class="col col-6">Sexo:</div>
                            <div class="col col-6" data-field="doctor.sex"></div>
                        </div>
                    </div>
                    <div class="list-group-item" sensible-field="doctor.birth">
                        <div class="row">
                            <div class="col col-6">Fecha de nacimiento:</div>
                            <div class="col col-6" data-field="doctor.birth"></div>
                        </div>
                    </div>
                    <div class="list-group-item" sensible-field="doctor.address">
                        <div class="row">
                            <div class="col col-6">Dirección:</div>
                            <div class="col col-6" data-field="doctor.address"></div>
                        </div>
                    </div>
                    <div class="list-group-item" sensible-field="doctor.localty">
                        <div class="row">
                            <div class="col col-6">Localidad:</div>
                            <div class="col col-6" data-field="doctor.localty"></div>
                        </div>
                    </div>
                    <div class="list-group-item" sensible-field="doctor.email">
                        <div class="row">
                            <div class="col col-6">Correo electrónico:</div>
                            <div class="col col-6" data-field="doctor.email"></div>
                        </div>
                    </div>
                    <div class="list-group-item" sensible-field="doctor.phone">
                        <div class="row">
                            <div class="col col-6">Número de teléfono:</div>
                            <div class="col col-6" data-field="doctor.phone"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Especialidad:</div>
                            <div class="col col-6" data-field="doctor.specialty.name"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Horarios:</div>
                            <div class="col col-6" data-field="doctor.scheduleBr"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Estado:</div>
                            <div class="col col-6" data-field="doctor.active"></div>
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
                <div class="card mb-3 __basicInfo hidable-if-disabled">
                    <div class="card-header">
                        Información básica
                    </div>
                    <div class="card-body">
                        <label for="nameInput" class="form-label">Nombre: </label>
                        <input class="form-control me-auto mb-3" id="nameInput" data-value="doctor.name" type="text" placeholder="Nombre" />
                        <label for="surnameInput" class="form-label">Apellido: </label>
                        <input class="form-control me-auto mb-3" id="surnameInput" data-value="doctor.surname" type="text" placeholder="Apellido" />
                        <label class="form-label">Especialidad: </label>
                        <button type="button" id="updateBasicDataBtn" style="margin-top: 15px" class="btn btn-secondary">Actualizar</button>
                    </div>
                </div>
                <div class="card mb-3 __schedules hidable-if-disabled">
                    <div class="card-header">
                        Horarios
                    </div>
                    <div class="card-body _schedule-container list-group list-group-flush" style="padding: 0px"></div>
                </div>
                <div class="card mb-3 __addSchedule hidable-if-disabled">
                    <div class="card-header">
                        Agregar un horario
                    </div>
                    <div class="card-body">
                        <label for="daySelect" class="form-label">Día: </label>
                        <select class="form-control me-auto mb-3" name="day" id="daySelect">
                            <option value="SUNDAY">Domingo</option>
                            <option value="MONDAY">Lunes</option>
                            <option value="TUESDAY">Martes</option>
                            <option value="WEDNESDAY">Miércoles</option>
                            <option value="THURSDAY">Jueves</option>
                            <option value="FRIDAY">Viernes</option>
                            <option value="SATURDAY">Sábado</option>
                        </select>
                        <div class="mb-3">
                            <label for="startInput">Inicio:</label>
                            <input type="time" id="startInput" class="form-control me-auto" />
                            <div class="invalid-feedback" id="scheduleAddingIn"></div>
                        </div>
                        <label for="endInput">Fin:</label>
                        <input type="time" id="endInput" class="form-control me-auto " />
                        <div class="form-text mb-3" id="endInputText"></div>
                        <button class="btn btn-primary" disabled id="addScheduleBtn">Agregar</button>

                    </div>
                </div>
                <div class="card mb-3 __sensibleInfo hidable-if-disabled">
                    <div class="card-header">
                        Información sensible
                    </div>
                    <div class="card-body">
                        <label for="sexSelect" class="form-label">Sexo: </label>
                        <select class="form-control me-auto mb-3" name="sex" id="sexSelect" data-value="doctor.sex">
                            <option value="M">Masculino</option>
                            <option value="F">Femenino</option>
                        </select>
                        <label for="birthInput" class="form-label">Fecha de nacimiento: </label>
                        <input class="form-control me-auto mb-3" id="birthInput" data-value="doctor.birth.sql" type="date" placeholder="Fecha de nacimiento" />
                        <label for="addressInput" class="form-label">Dirección: </label>
                        <input class="form-control me-auto mb-3" id="addressInput" data-value="doctor.address" type="text" placeholder="Dirección" />
                        <label for="localtyInput" class="form-label">Localidad: </label>
                        <input class="form-control me-auto mb-3" id="localtyInput" data-value="doctor.localty" type="text" placeholder="Localidad" />
                        <label for="emailInput" class="form-label">Correo electrónico: </label>
                        <input class="form-control me-auto mb-3" id="emailInput" data-value="doctor.email" type="email" placeholder="Correo electrónico" />
                        <label for="phoneInput" class="form-label">Número de teléfono: </label>
                        <input class="form-control me-auto mb-3" id="phoneInput" data-value="doctor.phone" type="text" placeholder="Número de teléfono" />
                        <button type="button" id="updateSensibleDataBtn" class="btn btn-secondary">Actualizar</button>
                    </div>
                </div>
                <div class="card mb-3 disable">
                    <div class="card-body">
                        <h5 class="card-title">Deshabilitar o habilitar</h5>
                        <button class="btn btn-danger" id="btnState" data-field="doctor.active.switchButton"></button>
                    </div>
                </div><br><br>
            </div>
        
            <div class="tab-pane fade hidable-if-disabled" id="nav-appointments" role="tabpanel" aria-labelledby="nav-appointments-tab" tabindex="0">
               <br>
                <div class="row mb-3">
                    <div class="col">
                        <input class="form-control form-control-lg" id="appointment_q" type="text" placeholder="Buscar en los turnos" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-12 col-lg-6 mb-3 pSelectorWrapper"></div>
                    <div class="col-12 col-lg-6 mb-3">
                        <select class="form-select" id="selectStatus" aria-label="Default select example">
                            <option value="NONE" selected>Todos los turnos</option>
                            <option value="PENDING">Pendientes</option>
                            <option value="ABSENT">Ausentes</option>
                            <option value="PRESENT">Presentes</option>    
                        </select>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col">
                        <div class="input-group">
                            <label class="input-group-text" for="inputDesde">Desde</label>
                            <input type="date" class="form-control" id="inputDesde">
                            <label class="input-group-text" for="inputHasta">Hasta</label>
                            <input type="date" class="form-control" id="inputHasta">
                          </div>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col">
                        <button class="btn btn-primary" id="btnSearchForAppointments">Buscar</button>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <div class="list-group appointmentList list-group-flush mb-3">
                            
                        </div>
                        <span class="appointmentListErr"></span>
                        <button id="loadMoreAppointments" class="btn btn-outline-secondary d-none">Cargar más</button>
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
    <script type="module" src="./../../api/controller/doctorProfile/index.js"></script>
</body>
</html>