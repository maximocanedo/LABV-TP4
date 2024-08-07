<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Medicos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
		integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.datatables.net/2.0.8/css/dataTables.dataTables.css" />
	<!--<jsp:include page="../Templates/CommonHeader"></jsp:include>-->
	<style>
		html,
		body {
			height: 100%;
			overflow-y: hidden;
		}
	</style>
</head>

<body>
	<!--<jsp:include page="Navbar.html"></jsp:include>-->
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">Administrador</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
				aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav">
					<a class="nav-link active" aria-current="page" href="#">Pacientes</a>
					<a class="nav-link" href="#">Medicos</a> <a class="nav-link">Informes</a>
				</div>
			</div>
		</div>
		<!--
          <div class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              <img src="..." class="img-thumbnail rounded-circle" alt="...">
            </button>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="#">Log Out</a></li>
            </ul>
          </div>--> <!--<button type="button" class="btn btn-secondary">Log in</button>-->
	</nav>
	<div class="container-md h-75">
		<div class="row h-75">
			<div class="col-3"></div>
			<div class="col d-flex align-items-center">
				<form action="" method="post" id="formLogin" class="flex-fill needs-validation" novalidate>
					<div class="input-group input-group-sm mb-3">
						<span class="input-group-text" id="login-input-group" for="txtUsername">Nombre de
							Usuario:</span>
						<input type="text" class="form-control" aria-label="Username input"
							aria-describedby="login-input-group" id="txtUsername" name="txtUsername"
							pattern="^[a-z]+\.[a-z]+$" placeholder="alice.manhattan" title="El nombre de usuario no debe contener espacios" required>
						<div class="invalid-feedback">
							Invalido. El nombre de usuario no debe contener espacios
						</div>
					</div>
					<div class="input-group input-group-sm mb-3">
						<span class="input-group-text" id="login-input-group" for="txtPassword">Password</span>
						<input type="password" class="form-control" aria-label="Password input"
							aria-describedby="login-input-group" id="txtPassword" name="txtPassword" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"
							placeholder="verySecurePassword" required>
						<div class="invalid-feedback">
							Invalido. La contrasenia debe contener como minimo 8 caracteres, 1 minuscula, 1 Mayuscula, 1 digito y un numero.
						</div>
					</div>
					<div class="justify-content-end">
						<button class="btn btn-primary" type="submit" id="btnLogin">Ingresar</button>
					</div>
				</form>
			</div>
			<div class="col-3"></div>
		</div>
	</div>
	<!--<jsp:include page="../Templates/CommonFooter"></jsp:include>-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="https://cdn.datatables.net/2.0.8/js/dataTables.js"></script>
	<script type="module" src="./Login.js"></script>
</body>

</html>