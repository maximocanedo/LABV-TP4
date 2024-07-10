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
		<table id="example" class="table table-striped" style="width: 100%">
			<thead>
				<tr>
					<th>Id</th>
					<th>Legajo</th>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>Genero</th>
					<th>DNI</th>
					<th>Numero</th>
					<th>Direccion</th>
					<th>Localidad</th>
					<th>Provincia</th>
					<th>Fecha Nacimiento</th>
					<th>Mail</th>
					<th>Especialidad</th>
					<th>Activo</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>1001</td>
					<td>Juan</td>
					<td>P�rez</td>
					<td>Masculino</td>
					<td>12345678</td>
					<td>1</td>
					<td>Calle Falsa 123</td>
					<td>Buenos Aires</td>
					<td>Buenos Aires</td>
					<td>01/01/1975</td>
					<td>juan.perez@hospital.com</td>
					<td>Cardiolog�a</td>
					<td>S�</td>
				</tr>
				<tr>
					<td>2</td>
					<td>1002</td>
					<td>Mar�a</td>
					<td>G�mez</td>
					<td>Femenino</td>
					<td>23456789</td>
					<td>2</td>
					<td>Avenida Siempreviva 456</td>
					<td>C�rdoba</td>
					<td>C�rdoba</td>
					<td>02/02/1980</td>
					<td>maria.gomez@hospital.com</td>
					<td>Pediatr�a</td>
					<td>No</td>
				</tr>
				<tr>
					<td>3</td>
					<td>1003</td>
					<td>Carlos</td>
					<td>Fern�ndez</td>
					<td>Masculino</td>
					<td>34567890</td>
					<td>3</td>
					<td>Ruta 66 km 789</td>
					<td>Rosario</td>
					<td>Santa Fe</td>
					<td>03/03/1978</td>
					<td>carlos.fernandez@hospital.com</td>
					<td>Neurolog�a</td>
					<td>S�</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--<jsp:include page="../Templates/CommonFooter"></jsp:include>-->
</body>
</html>