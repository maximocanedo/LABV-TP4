<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <!-- Header content -->
    <link rel="stylesheet" href="./../assets/css/commons.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/2.0.8/css/dataTables.dataTables.css" />
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
    <main class="container-md h-75">
        <div class="row h-75">
            <div class="col-3"></div>
            <div class="col d-flex align-items-center">
                <form action="#" method="post" id="formLogin" class="flex-fill needs-validation" novalidate>
                    <div class="input-group input-group-sm mb-3">
                        <span class="input-group-text" id="login-input-group" for="txtUsername">Nombre de
                            Usuario:</span>
                        <input type="text" class="form-control" aria-label="Username input"
                            aria-describedby="login-input-group" id="txtUsername" name="txtUsername" required>
                            <div class="valid-feedback">
                                OK.
                              </div>
                              <div class="invalid-feedback" id="iF_username">
                                Credenciales inválidas.
                              </div>
                    </div>
                    <div class="input-group input-group-sm mb-3">
                        <span class="input-group-text" id="login-input-group" for="txtPassword">Password</span>
                        <input type="password" class="form-control" aria-label="Password input"
                            aria-describedby="login-input-group" id="txtPassword" name="txtPassword" required>
                    </div>
                    <div class="justify-content-end">
                        <button class="btn btn-primary" type="button" id="btnLogin">Ingresar</button>
                    </div>
                </form>
            </div>
            <div class="col-3"></div>
        </div>
    </main>
    <!-- Footer content -->
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/2.0.8/js/dataTables.js"></script>
    <script type="module" src="../api/controller/login/index.js"></script>
</body>

</html>