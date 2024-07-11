<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pacientes</title>
<!--<jsp:include page="../Templates/CommonHeader"></jsp:include>-->
</head>
<body>
	<jsp:include page="Navbar.html"></jsp:include>

	<div class="container-md my-3">
		<table id="example" class="table table-striped" style="width: 100%">
			<thead>
				<tr>
					<th>Id</th>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>DNI</th>
					<th>Numero</th>
					<th>Direccion</th>
					<th>Localidad</th>
					<th>Provincia</th>
					<th>Fecha Nacimiento</th>
					<th>Mail</th>
					<th>Activo</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>Juan</td>
					<td>Pérez</td>
					<td>12345678</td>
					<td>1</td>
					<td>Calle Falsa 123</td>
					<td>Buenos Aires</td>
					<td>Buenos Aires</td>
					<td>01/01/1990</td>
					<td>juan.perez@mail.com</td>
					<td>Sí</td>
					<td><button type="button" class="btn btn-primary">Asignar
							Turno</button></td>
				</tr>
				<tr>
					<td>2</td>
					<td>María</td>
					<td>Gómez</td>
					<td>23456789</td>
					<td>2</td>
					<td>Avenida Siempreviva 456</td>
					<td>Córdoba</td>
					<td>Córdoba</td>
					<td>02/02/1985</td>
					<td>maria.gomez@mail.com</td>
					<td>No</td>
					<td><button type="button" class="btn btn-primary">Asignar
							Turno</button></td>
				</tr>
			</tbody>
		</table>
	</div>

	<!--<jsp:include page="../Templates/CommonFooter"></jsp:include>-->
</body>
</html>