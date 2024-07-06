<!-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Medicos</title>
    <%@ include file="CommonHeader.html" %>
    <style>
        html,
        body {
            height: 100%;
            overflow-y: hidden;
        }
    </style>
</head>

<body>
    <%@ include file="Navbar.html" %>
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
                            <div class="input-group">
                                <span class="input-group-text" id="login-input-group" for="txtEmail">Email</span>
                                <input type="email" class="form-control" aria-label="Email input"
                                    aria-describedby="email-input" id="txtEmail" name="txtEmail"
                                    pattern="[^@\s]+@[^@\s]+\.[^@\s]+" placeholder="alice.manhattan@example.com"
                                    title="El email debe cumplir con el formato" required>
                                <div class="invalid-feedback">
                                    Invalido. El email debe cumplir con el formato example@example.com
                                </div>
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
                                <option selected value="">Femenino</option>
                                <option value="">Masculino</option>
                                <option value="">Otros</option>
                            </select>
                            <div class="invalid-feedback">
                                Invalido.
                            </div>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label for="txtDireccion" class="form-label">Direccion</label>
                            <input type="text" class="form-control" id="txtDireccion" placeholder="Example Address 854" required pattern="^[A-Za-záéíóúÁÉÍÓÚñÑ\s]+ \d+$" title="Direccion incorrecta">
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
                            <div class="input-group">
                                <span class="input-group-text" id="phone-input" for="txtPhone">+54 11</span>
                                <input type="text" class="form-control" aria-label="Phone input"
                                    aria-describedby="phone-input" id="txtPhone" name="txtPhone"
                                    pattern="^\d{8}$" title="El formato es incorrecto solo deben tener 8 caracteres y sin espacios" placeholder="56369594"
                                    title="El telefono debe cumplir con el formato " required>
                                <div class="invalid-feedback">
                                    Invalido. El telefono debe cumplir con el formato 
                                </div>
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
    <%@ include file="CommonFooter.html" %>
    <script type="module" src="${pageContext.request.contextPath}/register.js"></script>
</body>

</html>