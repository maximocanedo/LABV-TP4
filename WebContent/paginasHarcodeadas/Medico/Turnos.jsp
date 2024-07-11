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
        <h2>Turnos</h2>
        <table id="example" class="table table-striped" style="width:100%">
          <thead>
              <tr>
                  <th>Id</th>
                  <th>Nombre Paciente</th>
                  <th>Apellido Paciente</th>
                  <th>DNI Paciente</th>
                  <th>Estado</th>
                  <th>Nombre Doctor</th>
                  <th>Apellido Doctor</th>
                  <th>Notas</th>
                  <th></th>
              </tr>
          </thead>
          <tbody>
            <tr>
              <td>1</td>
              <td>Juan</td>
              <td>Pérez</td>
              <td>12345678</td>
              <td>Pendiente</td>
              <td>Pedro</td>
              <td>Rivas</td>
              <td>Llevar estudios correspondientes</td>
              <td><button type="button" class="btn btn-primary">Detalle</button></td>
          </tr>
          <tr>
            <td>2</td>
            <td>María</td>
            <td>González</td>
            <td>87654321</td>
            <td>Ausente</td>
            <td>Laura</td>
            <td>Sánchez</td>
            <td>Revisión completa</td>
            <td><button type="button" class="btn btn-primary">Detalle</button></td>
        </tr>
        <tr>
            <td>3</td>
            <td>Carlos</td>
            <td>Rodríguez</td>
            <td>23456789</td>
            <td>Ausente</td>
            <td>Martín</td>
            <td>Díaz</td>
            <td>Requiere estudio de sangre</td>
            <td><button type="button" class="btn btn-primary">Detalle</button></td>
        </tr>
          </tbody>
          <tfoot>
            <tr>
                <th>Id</th>
                <th>Nombre Paciente</th>
                <th>Apellido Paciente</th>
                <th>DNI Paciente</th>
                <th>Estado</th>
                <th>Nombre Doctor</th>
                <th>Apellido Doctor</th>
                <th>Notas</th>
                <th></th>
            </tr>
          </tfoot>
      </table>
      </div>
	<!--<jsp:include page="../Templates/CommonFooter"></jsp:include>-->
</body>
</html>