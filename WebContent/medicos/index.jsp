<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado Medicos</title>
    <link href="./../assets/css/commons.css" rel="stylesheet">
        <link href="https://cdn.datatables.net/v/bs5/dt-2.0.8/datatables.min.css" rel="stylesheet">
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
    <main id="container-md">
        <div class="row">
            <div class="col-2"></div>
            <div class="col w-100">
                <div class="container justify-content-end">
                    <div class="d-flex" role="search">
                        <select class="form-select me-2" style="width: 80px;" aria-label="Default select example" id="ddlEntriesPerPage">
                            <option value="10" selected>10</option>
                            <option value="25">25</option>
                            <option value="50">50</option>
                        </select>
                        <select class="form-select me-2" style="width: 140px;" aria-label="Default select example" id="ddlStatusFilter">
                            <option value="ONLY_ACTIVE" selected>Activo</option>
                            <option value="ONLY_INACTIVE">Inactivo</option>
                            <option value="BOTH">Ambos</option>
                        </select>
                        <input class="form-control me-2" type="search" id="txtBuscar" placeholder="Buscar..." aria-label="Search">
                        <button class="btn btn-outline-success me-2" type="submit" id="btnBuscar">Buscar</button>
                        <button class="btn btn-outline-primary" type="button" id="btnAddDoctor">Agregar</button>
                    </div>
                </div>

            </div>
            <div class="col-2"></div>
        </div>
        <div class="row mt-3">
            <div class="col-2"></div>
            <div class="col text-center" id="tableContainer">
                <table id="tableListadoMedicos" class="display" style="width:100%"></table>
                <div class="spinner-border" role="status" id="loadingSpinner">
                    <span class="visually-hidden">Loading...</span>
                  </div>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row mt-2">
            <div class="col w-100">
                <ul class="pagination justify-content-center">
                    <li class="page-item" id="btnPrevPage">
                        <a class="page-link" type="button">Anterior</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" type="button" id="btnNextPage">Siguiente</a>
                    </li>
                </ul>
            </div>
        </div>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/v/bs5/dt-2.0.8/datatables.min.js"></script>
    <script type="module" src="../api/controller/medicos/index.js"></script>
</body>

</html>