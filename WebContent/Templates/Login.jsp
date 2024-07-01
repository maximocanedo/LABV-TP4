<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Medicos</title>
<!--<jsp:include page="../Templates/CommonHeader"></jsp:include>-->
</head>
<body>
	<jsp:include page="Navbar.html"></jsp:include>
	<div class="container-md my-3">
      <div class="row">
        <div class="col-3"></div>
        <div class="col">
          <div class="input-group input-group-sm mb-3">
						<span class="input-group-text" id="inputGroup-sizing-sm">Mail</span>
						<input type="text" class="form-control"
							aria-label="Sizing example input"
							aria-describedby="inputGroup-sizing-sm" name="mail"
							placeholder="Ingrese correo electronico"
							pattern="^((?!\.)[\w\-_.]*[^.])(@\w+)(\.\w+(\.\w+)?[^.\W])$"
							title="Ingrese un correo valido">
					</div>
					<div class="input-group input-group-sm mb-3">
						<span class="input-group-text" id="inputGroup-sizing-sm">Contraseña</span>
						<input type="password" class="form-control"
							aria-label="Sizing example input"
							aria-describedby="inputGroup-sizing-sm" name="password"
							placeholder="Ingrese su contraseña">
					</div>
					<div>
						<button class="btn btn-primary" type="submit" name="btnIngresar">Ingresar</button>
						<span style="color: red; display: block;">
					</div>
        </div>
        <div class="col-3"></div>
      </div>
    </div>
<!--<jsp:include page="../Templates/CommonFooter"></jsp:include>-->
</body>
</html>