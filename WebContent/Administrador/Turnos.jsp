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
    <table id="example" class="table table-striped" style="width:100%">
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
          <td><button type="button" class="btn btn-primary">Asignar Turno</button></td>
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
          <td><button type="button" class="btn btn-primary">Asignar Turno</button></td>
        </tr>
        <tr>
          <td>3</td>
          <td>Carlos</td>
          <td>Fernández</td>
          <td>34567890</td>
          <td>3</td>
          <td>Ruta 66 km 789</td>
          <td>Rosario</td>
          <td>Santa Fe</td>
          <td>03/03/1978</td>
          <td>carlos.fernandez@mail.com</td>
          <td>Sí</td>
          <td><button type="button" class="btn btn-primary">Asignar Turno</button></td>
        </tr>
        <tr>
          <td>4</td>
          <td>Ana</td>
          <td>Martínez</td>
          <td>45678901</td>
          <td>4</td>
          <td>Camino Real 1011</td>
          <td>La Plata</td>
          <td>Buenos Aires</td>
          <td>04/04/1992</td>
          <td>ana.martinez@mail.com</td>
          <td>No</td>
          <td><button type="button" class="btn btn-primary">Asignar Turno</button></td>
        </tr>
        <tr>
          <td>5</td>
          <td>Lucía</td>
          <td>Lopez</td>
          <td>56789012</td>
          <td>5</td>
          <td>Pasaje Norte 1213</td>
          <td>Mendoza</td>
          <td>Mendoza</td>
          <td>05/05/1980</td>
          <td>lucia.lopez@mail.com</td>
          <td>Sí</td>
          <td><button type="button" class="btn btn-primary">Asignar Turno</button></td>
        </tr>
      </tbody>
      <tfoot>
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
        </tr>
      </tfoot>
    </table>
  </div>
  </div>
	<!--<jsp:include page="../Templates/CommonFooter"></jsp:include>-->
</body>
</html>