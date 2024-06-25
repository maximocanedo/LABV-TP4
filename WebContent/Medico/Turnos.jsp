<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Medico</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
 table {
      width: 100%;
    }
    th, td {
      text-align: center;
      padding: 8px;
      border-bottom: 1px solid #ddd;
    }
    

.btn {
  display: inline-block;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
  text-decoration: none;
  outline: none;
  
}

.btn-rojo {
  background-color: #ff4d4d;
  color: white;
}

.btn-verde {
  background-color: #4CAF50;
  color: white;
}

.btn-Azul {
  background-color: #0000FF ;
  color: white;
}

.btn:hover {
  background-color: #e6e6e6;
}
</style>
</head>
<body>
<i class="bi bi-person-circle" style="font-size: 30px"> Juan Martin</i><br><br>
<div>
<label >Ingrese fecha</label>
<input type="text" >
<label >Ingrese hora</label>
<input type="text">
<input type="submit" value="Filtar" class="btn btn-Azul bi bi-check-circle">
<input type="submit" value="Filtar por fecha actual" class="btn btn-Azul bi bi-check-circle">
</div>
<div>
<table class="table table-striped">
  <thead>
    <tr style="border-bottom: 2px solid #fff;" >
      <th>Id Turno</th>
      <th>Fecha y hora</th>
      <th>Nombre y Apellido</th>
      <th>Dni</th>
      <th>Fecha de nacimiento</th>
      <th>Telefono</th>
      <th>Correo Electronico</th>
      <th>Status</th>
      <th>observación</th>
    </tr>
  </thead>
  <tbody>
    <tr style="border:none;border-bottom: 2px solid #fff;"   >
      <td>Dato 1</td>
      <td>Dato 2</td>
      <td>Dato 3</td>
      <td>Dato 4</td>
      <td>Dato 5</td>
      <td>Dato 6</td>
      <td>Dato 7</td>
      <td>
      <input type="submit" value="PRESENTE" class="btn btn-verde bi bi-check-circle">
      <input type="submit" value="AUSENTE"  class="btn btn-rojo bi bi-x-circle">
      </td>
     <td> <input type="submit" value="+ OBSERVACIÒN" class="btn btn-Azul bi bi-check-circle"></td>
    </tr>
    <tr style="border:none;border-bottom: 2px solid #fff;"   >
      <td>Dato 1</td>
      <td>Dato 2</td>
      <td>Dato 3</td>
      <td>Dato 4</td>
      <td>Dato 5</td>
      <td>Dato 6</td>
      <td>Dato 7</td>
      <td>
      <input type="submit" value="PRESENTE" class="btn btn-verde bi bi-check-circle">
      <input type="submit" value="AUSENTE"  class="btn btn-rojo bi bi-x-circle">
      </td>
      <td> <input type="submit" value="+ OBSERVACIÒN" class="btn btn-Azul bi bi-check-circle"></td>
    </tr>
   <tr style="border:none;border-bottom: 2px solid #fff;"   >
      <td>Dato 1</td>
      <td>Dato 2</td>
      <td>Dato 3</td>
      <td>Dato 4</td>
      <td>Dato 5</td>
      <td>Dato 6</td>
      <td>Dato 7</td>
      <td> PRESENTE </td>
      <td> Fiebre con dolor de garganta. se le dio 72hs de reposo</td>
    <tr style="border:none;border-bottom: 2px solid #fff;"   >
      <td>Dato 1</td>
      <td>Dato 2</td>
      <td>Dato 3</td>
      <td>Dato 4</td>
      <td>Dato 5</td>
      <td>Dato 6</td>
      <td>Dato 7</td>
      <td>
      <input type="submit" value="PRESENTE" class="btn btn-verde bi bi-check-circle">
      <input type="submit" value="AUSENTE"  class="btn btn-rojo bi bi-x-circle">
      </td>
      <td> <input type="submit" value="+ OBSERVACIÒN" class="btn btn-Azul bi bi-check-circle"></td>
    </tr>
   <tr style="border:none;border-bottom: 2px solid #fff;"   >
      <td>Dato 1</td>
      <td>Dato 2</td>
      <td>Dato 3</td>
      <td>Dato 4</td>
      <td>Dato 5</td>
      <td>Dato 6</td>
      <td>Dato 7</td>
      <td>
      <input type="submit" value="PRESENTE" class="btn btn-verde bi bi-check-circle">
      <input type="submit" value="AUSENTE"  class="btn btn-rojo bi bi-x-circle">
      </td>
      <td> <input type="submit" value="+ OBSERVACIÒN" class="btn btn-Azul bi bi-check-circle"></td>
    </tr>
    <tr style="border:none;border-bottom: 2px solid #fff;"   >
      <td>Dato 1</td>
      <td>Dato 2</td>
      <td>Dato 3</td>
      <td>Dato 4</td>
      <td>Dato 5</td>
      <td>Dato 6</td>
      <td>Dato 7</td>
      <td>AUSENTE</td>
      <td>AUSENTE</td>
    </tr>
    <tr style="border:none;border-bottom: 2px solid #fff;"   >
      <td>Dato 1</td>
      <td>Dato 2</td>
      <td>Dato 3</td>
      <td>Dato 4</td>
      <td>Dato 5</td>
      <td>Dato 6</td>
      <td>Dato 7</td>
      <td>
      <input type="submit" value="PRESENTE" class="btn btn-verde bi bi-check-circle">
      <input type="submit" value="AUSENTE"  class="btn btn-rojo bi bi-x-circle">
      </td>
      <td> <input type="submit" value="+ OBSERVACIÒN" class="btn btn-Azul bi bi-check-circle"></td>
    </tr>
  </tbody>
</table>
</div>
</body>
</html>