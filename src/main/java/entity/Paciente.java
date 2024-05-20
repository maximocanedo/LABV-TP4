package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import utils.FormattedLine;
import utils.IFormattedLine;
import utils.FormattedLine.Alignment;

@Entity
@Table(name="pacientes")
public class Paciente {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private String nombre;
	@Column
	private String apellido;
	@Column
	private int dni;
	@Column
	private String telefono;
	@Column
	private String direccion;
	@Column
	private String localidad;
	@Column
	private String provincia;
	@Column
	private Date fechaNacimiento;
	@Column
	private String correo;
	
	public Paciente() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public String toString() {		
		IFormattedLine header = new FormattedLine("[Paciente]");
		header.setLineSize(48);
		header.setTopHeader(true);
		header.setAlignment(Alignment.RIGHT);
		
		String cont = "";
		String[] lines = new String[] {
			"ID: " + String.valueOf(id),
			"Nombre: " + nombre + " " + apellido, 
			"DNI N.º: " + String.valueOf(dni),
			"Fecha de nacimiento: " + String.valueOf(fechaNacimiento),
			direccion, 
			localidad + "," + provincia,
			correo
		};
		for(String line: lines) {
			cont += line + "\n";
		}
		IFormattedLine content = new FormattedLine(cont);
		content.setLineSize(48);
				
		IFormattedLine end = new FormattedLine("···");
		end.setAlignment(FormattedLine.Alignment.CENTER);
		end.setTopHeader(true);
		end.setHeaderMiddleDelimiters('—');
		end.setLineSize(48);
		
		String tot = header.toString() + content.toString();
		
		return tot + end.toString();
	}
}
