<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="CommonHeader.html" %>
<title>Insert title here</title>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Sistema Turnos</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="ms-auto navbar-nav">
                    <a class="nav-link active" id="navLogin" aria-current="page" href="./login.html">Iniciar Sesion</a>
					<a class="nav-link" id="navRegister" href="register.html">Registrarse</a>
					<div class="dropdown d-none" id="ddMenu">
						<button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" id="ddMenuButton" aria-expanded="false">
						  
						</button>
						<ul class="dropdown-menu">
						  <li><a class="nav-link" id="navLogout" href="javascript:void(0);" onclick="logoutSession()">Logout</a></li>
						</ul>
					  </div>
                </div>
            </div>
        </div>
    </nav>
	<div class="container-md h-75">
		<div class="row h-75">
			<div class="col-3"></div>
			<div class="col d-flex align-items-center">
				<form action="" method="post" id="formLogin" class="flex-fill needs-validation d-none" novalidate>
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
							aria-describedby="login-input-group" id="txtPassword" name="txtPassword" 
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
	<script type="module" src="../assets/js/login.js"></script>
	<%@include file="CommonFooter.html" %>
</body>
</html>