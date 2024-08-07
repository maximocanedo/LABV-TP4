<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil de Usuario</title>
    <link rel="stylesheet" href="./../../assets/css/commons.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
    crossorigin="anonymous"></script>
    <style>
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        main {
            flex: 1;
        }
    </style>
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
    <main class="container container-sm my-4">
        <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                <button class="nav-link active" id="nav-personal-info-tab" data-bs-toggle="tab" data-bs-target="#nav-personal-info" type="button" role="tab" aria-controls="nav-personal-info" aria-selected="true">Información básica</button>
                <button class="nav-link" id="nav-update-info-tab" data-bs-toggle="tab" data-bs-target="#nav-update-info" type="button" role="tab" aria-controls="nav-update-info" aria-selected="false">Acciones</button>
                <button class="nav-link hidable-if-disabled" id="nav-permissions-tab" data-bs-toggle="tab" data-bs-target="#nav-permissions" type="button" role="tab" aria-controls="nav-permissions" aria-selected="false">Permisos</button>
            </div>
        </nav>
        <div class="tab-content" id="nav-tabContent">
            <div class="tab-pane fade show active" id="nav-personal-info" role="tabpanel" aria-labelledby="nav-personal-info-tab" tabindex="0">
                <div class="list-group list-group-flush">
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Nombre:</div>
                            <div class="col col-6" data-field="user.name"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Nombre de usuario:</div>
                            <div class="col col-6" data-field="user.username"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Médico vinculado:</div>
                            <div class="col col-6" id="linkedDoctor"></div>
                        </div>
                    </div>
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col col-6">Estado:</div>
                            <div class="col col-6" data-field="user.active"></div>
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
                <div class="card mb-3 __changeAccountName hidable-if-disabled">
                    <div class="card-header">
                        Cambiar nombre de la cuenta
                    </div>
                    <div class="card-body hstack gap-3">
                        <input class="form-control me-auto" id="nameInput" data-value="user.name" type="text" placeholder="Nuevo nombre">
                        <button type="button" id="updateDataBtn" class="btn btn-secondary">Actualizar</button>
                    </div>
                </div>
                <div class="card mb-3 __assignDoctor hidable-if-disabled">
                    <div class="card-header">
                        Médico vinculado
                    </div>
                    <div class="card-body hstack gap-3 medicoSelectorHere">
                        <button type="button" id="updateDoctorBtn" class="btn btn-secondary">Actualizar</button>
                    </div>
                </div>
                <div class="card mb-3 __resetPassword hidable-if-disabled">
                    <div class="card-header">
                        Cambiar contraseña
                    </div>
                    <div class="card-body">
                        <div class="form-floating mb-3 pass0">
                            <input type="password" class="form-control" id="password0" placeholder="">
                            <label for="password0">Contraseña actual</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="password1" placeholder="">
                            <label for="password1">Nueva contraseña</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="password2" placeholder="">
                            <label for="password2">Repita nueva contraseña</label>
                        </div>
                        <button class="btn btn-primary" id="btnUpdatePassword">Cambiar contraseña</button>
                    </div>
                </div>
                <div class="card mb-3 disable">
                    <div class="card-body">
                        <h5 class="card-title">Deshabilitar o habilitar</h5>
                        <button class="btn btn-danger" id="btnState" data-field="user.active.switchButton"></button>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade hidable-if-disabled" id="nav-permissions" role="tabpanel" aria-labelledby="nav-permissions-tab" tabindex="0">
                <br>
                <div class="card mb-3 __grantRole">
                    <div class="card-body">
                        <h5 class="card-title">Conceder un rol</h5>
                        <p class="card-text">Los roles son una serie de permisos concedidos preestablecidos. Al conceder un rol a un usuario, estos permisos se autoconcederán todos a la vez, lo que evita tener que concederlos uno a uno. </p>
                        <div class="hstack gap-3">
                            <select class="form-control me-auto form-select" id="rol_template_select">
                                <option selected value="NONE">Seleccione rol</option>
                                <option value="ROOT">Súperusuario</option>
                                <option value="PATIENT_MANAGER">Administrador de pacientes</option>
                                <option value="DOCTOR_MANAGER">Administrador de médicos</option>
                                <option value="EXTRA_MANAGER">Administrador de datos secundarios</option>
                                <option value="USER_OFFICE">Administrador de usuarios</option>
                                <option value="SECURITY_OFFICE">Responsable de seguridad</option>
                            </select>
                            <button disabled type="button" id="rol_template_select_btn" class="btn btn-warning">Conceder</button>
                        </div>
                    </div>
                </div>
                <div class="card mb-3">
                    <div class="card-header">Permisos</div>
                    <div class="card-body _permit-container list-group list-group-flush" style="padding: 0px">
                    </div>
                </div>
                <div class="card mb-3 __deletePermissions">
                    <div class="card-body">
                        <h5 class="card-title">Borrar permisos</h5>
                        <p class="card-text">Denegar todos los permisos concedidos a este usuario. </p>
                        <button class="btn btn-danger" id="denyAllbtn">Borrar todos los permisos</button>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <footer class="bg-dark text-white py-3 mt-auto">
        <div class="container text-center">
            &copy; 2024 TIF LAB V. Todos los derechos reservados.
        </div>
    </footer>

    <style>
        .spaced_chk {
            margin-right: 10px;
        }
    </style>
    <script type="module" src="./../../api/controller/userProfile/index.js"></script>
</body>
</html>
