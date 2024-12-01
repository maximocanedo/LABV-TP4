<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Usuarios</title>
    <link href="./../assets/css/commons.css" rel="stylesheet" crossorigin="anonymous">
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
        <center class="mb-3">
            <h2>Usuarios</h2>
        </center>
        <div class="container-fluid mb-3">
            <div class="row">
                <div class="col input-group mb-3">
                    <input type="search" class="form-control" id="user_q">
                    <select name="filterStatus" class="form-select dropdown-toggle" id="filterStatus__user">
                        <option value="ONLY_ACTIVE">Usuarios activos</option>
                        <option value="ONLY_INACTIVE">Usuarios deshabilitados</option>
                        <option value="BOTH">Todos los usuarios</option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col input-group">
                    <button id="btnSearch" class="btn btn-primary col">Buscar</button>
                </div>
            </div>
        </div>    
        <div class="list-group results container-fluid mb-3" style="padding-left: inherit; cursor: pointer;">
        </div>
        <div class="container-fluid mb-3">
            <button class="btn btn-primary" id="nextBtn">Cargar más</button>
        </div>
    </main>

    <footer class="bg-dark text-white py-3 mt-auto">
        <div class="container">
            <p class="m-0"> Tecnicatura Universitaria en Sistemas Informaticos 2024.</p>
        </div>
    </footer>

    <script type="module" src="./../api/controller/userSearch/index.js"></script>
</body>
</html>
