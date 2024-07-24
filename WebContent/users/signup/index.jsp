<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar una cuenta</title>
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

    <main class="container-sm my-4">
        <form name="signup" action="#" class="container">
            <h3>Registrar una cuenta</h3>
            <br>
            <div class="mb-3">
                <label for="txtUsername" class="form-label">Nombre de usuario</label>
                <div class="input-group has-validation mb-3">
                    <span class="input-group-text" id="basic-addon1">@</span>
                    <input  type="text" 
                            name="username"
                            id="txtUsername" 
                            class="form-control" 
                            placeholder="Únicamente letras, números y guiones."
                            required
                            />
                    <div class="invalid-feedback" id="usernameInvalidFeedback"></div>
                </div>
            </div>
            <div class="mb-3">
                <label for="txtName" class="form-label">Nombre</label>
                <input type="text" required name="name" id="txtName" class="form-control" />
                <div class="invalid-feedback" id="nameInvalidFeedback"></div>
            </div>
            <div class="mb-3">
                <label for="txtPassword" class="form-label">Contraseña</label>
                <input type="password" required name="password" id="txtPassword" class="form-control" aria-describedby="passwordHelpBlock">
                <div class="invalid-feedback" id="passwordInvalidFeedback"></div>
            </div>
            <div class="mb-3">
                <label for="txtRepeatPassword" class="form-label">Repita su contraseña</label>
                <input type="password" required name="repeatPassword" id="txtRepeatPassword" class="form-control" aria-describedby="passwordRepeatHelpBlock" />
                <div class="invalid-feedback" id="passwordRepeatInvalidFeedback"></div>
            </div>
            <div class="mb-3">
                <button type="submit" class="btn btn-primary" disabled id="btnCrear">Crear</button>
            </div>
        </form>
    </main>


    <script type="module" src="./../../api/controller/signup/index.js"></script>
</body>
</html>
