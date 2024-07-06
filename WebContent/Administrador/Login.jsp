<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	<%@ include file="CommonFooter.html" %>
	<script type="module" src="${pageContext.request.contextPath}/login.js"></script>
</body>
</html>