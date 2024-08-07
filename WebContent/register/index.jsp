<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Medicos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link href="https://cdn.datatables.net/v/bs5/dt-2.0.8/datatables.min.css" rel="stylesheet">
</head>

<body>
    <br><br><br>
    <div class="container-md h-75">
        <div class="row h-75">
            <div class="col-3"></div>
            <div class="col d-flex align-items-center">
                <form class="g-3 needs-validation" id="formRegister" action="" method="post">
                    <div class="row mb-3">
                        <div class="col-md-4">
                            <label for="txtName" class="form-label">Nombre</label>
                            <input type="text" class="form-control" id="txtName" placeholder="Alice" pattern="^[A-Za-z]+$" title="Solo puede contener texto"
                                required>
                            <div class="invalid-feedback">
                                Invalido.
                            </div>
                        </div>
                        <div class="col-md-4">
                            <label for="txtSurname" class="form-label">Apellido</label>
                            <input type="text" class="form-control" id="txtSurname" placeholder="Manhattan" pattern="^[A-Za-z]+$" title="Solo puede contener texto" 
                            required>
                            <div class="invalid-feedback">
                                Invalido.
                            </div>
                        </div>
                        <div class="col-md-4">
                            <label for="validationCustomUsername" class="form-label">Username</label>
                            <div class="input-group has-validation">
                                <span class="input-group-text" id="txtUsername">@</span>
                                <input type="text" class="form-control" id="validationCustomUsername"
                                    aria-describedby="username-input" id="txtUsername" name="txtUsername"
                                    pattern="^[a-z]+\.[a-z]+$" placeholder="alice.manhattan"
                                    title="El nombre de usuario no debe contener espacios" required>
                                <div class="invalid-feedback">
                                    Invalido.
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col">
                            <label for="txtEmail" class="form-label">Email</label>
                            <input type="text" class="form-control" id="txtEmail" placeholder="example@example.com" pattern="^[A-Za-z]+$" title="Solo puede contener texto"
                                required>
                            <div class="invalid-feedback">
                                Invalido.
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="txtBirthDate" class="form-label">Fecha Nacimiento</label>
                            <input type="date" class="form-control" id="txtBirthDate" required>
                            <div class="invalid-feedback">
                                Invalido. No hay fecha seleccionada
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="txtGenre" class="form-label">Genero</label>
                            <select class="form-select" id="txtGenre" required>
                                <option selected value="0">Femenino</option>
                                <option value="1">Masculino</option>
                                <option value="2">Otros</option>
                            </select>
                            <div class="invalid-feedback">
                                Invalido.
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="txtAddress" class="form-label">Direccion</label>
                            <input type="text" class="form-control" id="txtAddress" placeholder="Example Address 854" required pattern="^[A-Za-záéíóúÁÉÍÓÚñÑ\s]+ \d+$" title="Direccion incorrecta">
                            <div class="invalid-feedback">
                                Invalido. Ingrese una direccion de correo valida Example Address 854
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="txtLocalty" class="form-label">Provincia</label>
                            <select class="form-select" id="txtLocalty" required>
                                <option selected value="">Buenos Aires</option>
                                <option>CABA</option>
                                <option>Mendoza</option>
                            </select>
                            <div class="invalid-feedback">
                                Invalido.
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="txtPhone" class="form-label">Telefono</label>
                            <input type="text" class="form-control" id="txtPhone" placeholder="911 2658 9584" pattern="^[0-9-]+$" title="El formato es incorrecto solo deben tener 8 caracteres y sin espacios" 
                            required>
                            <div class="invalid-feedback">
                                Invalido. El telefono no cumple el formato
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="txtPassword" class="form-label">Contrasenia</label>
                            <input type="text" class="form-control" id="txtPassword" placeholder="Password123$!" required pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$" title="Direccion incorrecta">
                            <div class="invalid-feedback">
                                Invalido. Debe contener una mayuscula, minuscula y un simbolo y 8 caracteres
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <button class="btn btn-primary" type="submit">Registrarse</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-3"></div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.datatables.net/v/bs5/dt-2.0.8/datatables.min.js"></script>
	<script type="module" src="../api/controller/register/index.js"></script>
</body>

</html>