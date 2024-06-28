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
          <h2>Asignar Nuevo Turno:</h2>
          <form>
            <div class="mb-3">
              <label for="doctorToAssign" class="form-label"
                >Seleccione el paciente:</label
              >
              <select class="form-select" aria-label="Default select example">
                <option value="1" selected>
                  Pedro Rivas
                </option>
                <option value="2">Facundo Sanchez</option>
                <option value="3">Pamela Rodrigez</option>
              </select>
            </div>
            <div class="mb-3">
              <label for="appointmentDate" class="form-label"
                >Fecha Turno</label
              >
              <input
                type="datetime-local"
                class="form-control"
                id="appointmentDate"
                aria-describedby="appointmentHelp"
              />
            </div>
            <div class="mb-3">
              <label for="doctorToAssign" class="form-label"
                >Seleccione el doctor:</label
              >
              <select class="form-select" aria-label="Default select example">
                <option value="1" selected>
                  Dr. Pedro Rivas - Cardiología
                </option>
                <option value="2">Dr. Facundo Sanchez - Neumonologia</option>
                <option value="3">Dr. Pamela Rodrigez - Nefrologia</option>
              </select>
            </div>
            <div class="mb-3">
              <label for="exampleFormControlTextarea1" class="form-label"
                >Notas</label
              >
              <textarea
                class="form-control"
                id="exampleFormControlTextarea1"
                rows="3"
              ></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Asignar</button>
          </form>
        </div>
        <div class="col-3"></div>
      </div>
    </div>
	<!--<jsp:include page="../Templates/CommonFooter"></jsp:include>-->
</body>
</html>