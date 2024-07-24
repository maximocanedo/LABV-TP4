<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="./assets/css/commons.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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

    <main>
        <div class="container">
            <div class="row">
                <div class="col col-12">
                    <h3>Inicio</h3>
                </div>
            </div>
            <div class="row">
                <div class="col col-6">
                    <a href="appointments/">Buscar turnos</a>
                </div>
                <div class="col col-6">
                    <a href="users/">Buscar usuarios</a>
                </div>
            </div>
            <div class="row">
                <div class="col col-6">
                    <a href="medicos/">Explorar registros de médicos</a>
                </div>
                <div class="col col-6">
                    <a href="pacientes/">Explorar registros de pacientes</a>
                </div>
            </div>
        </div>


    </main>
    <script type="module" src="./api/controller/home/index.js"></script>
</body>
</html>