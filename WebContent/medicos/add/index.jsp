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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/google-libphonenumber/3.2.37/libphonenumber.min.js" integrity="sha512-MVSgRRDm2gT+ms4budOJYLBXx+mxpntMwSmx2jHAQNof74bIo0/sThKak5+CsjPYP+4WLilz31BzUHst838NYA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
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
    <main class="container">
        <h2>Registrar médico</h2>
        <br>
        <h4>Información básica</h4>
        <div class="mb-3">
            <label for="txtName" class="form-label">Nombre:</label>
            <input class="form-control" type="text" id="txtName">
            <div class="valid-feedback" id="txtNameValid"></div>
            <div class="invalid-feedback" id="txtNameInvalid"></div>
        </div>
        <div class="mb-3">
            <label for="txtSurname" class="form-label">Apellido:</label>
            <input class="form-control" type="text" id="txtSurname">
            <div class="valid-feedback" id="txtSurnameValid"></div>
            <div class="invalid-feedback" id="txtSurnameInvalid"></div>
        </div>
        <div class="mb-3">
            <label for="txtFile" class="form-label">Número de legajo:</label>
            <input class="form-control" type="text" id="txtFile">
            <div class="valid-feedback" id="txtFileValid"></div>
            <div class="invalid-feedback" id="txtFileInvalid"></div>
        </div>
        <div class="mb-3">
            <label for="txtUser" class="form-label">Usuario:</label>
            <div class="txtUserWrapper">
                <div class="valid-feedback" id="txtUserValid"></div>
                <div class="invalid-feedback" id="txtUserInvalid"></div>
            </div>
        </div>
        <div class="mb-3">
            <label for="txtSex" class="form-label">Sexo:</label>
            <select name="txtSex" class="form-control form-select" id="txtSex">
                <option selected disabled>Seleccione una opción</option>
                <option value="M">Masculino</option>
                <option value="F">Femenino</option>
            </select>
            <div class="valid-feedback" id="txtSexValid"></div>
            <div class="invalid-feedback" id="txtSexInvalid"></div>
        </div>
        <div class="mb-3">
            <label for="txtBirth" class="form-label">Fecha de nacimiento:</label>
            <input class="form-control" type="date" id="txtBirth">
            <div class="valid-feedback" id="txtBirthValid"></div>
            <div class="invalid-feedback" id="txtBirthInvalid"></div>
        </div>
        <div class="mb-3">
            <label for="specialtySelector" class="form-label">Especialidad:</label>
            <div class="specialtySelectorWrapper"></div>
            <div class="valid-feedback" id="specialtySelectorValid"></div>
            <div class="invalid-feedback" id="specialtySelectorInvalid"></div>
        </div>
        <hr>
        <h4>Contacto</h4>
        <div class="mb-3">
            <label for="txtPhone" class="form-label">Número de teléfono:</label>
            <input class="form-control" type="text" id="txtPhone">
            <div class="valid-feedback" id="txtPhoneValid"></div>
            <div class="invalid-feedback" id="txtPhoneInvalid"></div>
        </div>
        <div class="mb-3">
            <label for="txtEmail" class="form-label">Dirección de correo electrónico: </label>
            <input class="form-control" type="email" id="txtEmail">
            <div class="valid-feedback" id="txtEmailValid"></div>
            <div class="invalid-feedback" id="txtEmailInvalid"></div>
        </div>
        <hr>
        <h4>Dirección</h4>
        <div class="mb-3">
            <label for="txtAddress" class="form-label">Dirección: </label>
            <input class="form-control" type="text" id="txtAddress">
            <div class="valid-feedback" id="txtAddressValid"></div>
            <div class="invalid-feedback" id="txtAddressInvalid"></div>
        </div>
        <div class="mb-3">
            <label for="txtLocalty" class="form-label">Localidad: </label>
            <input class="form-control" type="text" id="txtLocalty">
            <div class="valid-feedback" id="txtLocaltyValid"></div>
            <div class="invalid-feedback" id="txtLocaltyInvalid"></div>
        </div>
        <div class="mb-3">
            <button class="btn btn-primary" disabled id="btnEnviar">Enviar</button>
        </div>
    </main>
    <script type="module" src="./../../api/controller/medicos/add/index.js"></script>
</body>
</html>