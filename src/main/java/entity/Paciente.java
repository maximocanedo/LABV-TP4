package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import formatter.Card;
import formatter.Format;
import formatter.Formatter;
import formatter.TextBlock.Alignment;

@Entity
@Card(size = 48)
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

	@Format(label = "ID", order = 1)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Format(omitLabel = true, order = 0)
	public String getFullName() {
		return this.getApellido() + ", " + this.getNombre();
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

	@Format(label = "DNI N.º", order = 2)
	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}
	
	@Format(omitLabel = true, order = 3)
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Format(omitLabel = true, align = Alignment.RIGHT, order = 6)
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Format(omitLabel = true, align = Alignment.RIGHT, order = 7)
	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Format(omitLabel = true, align = Alignment.RIGHT, order = 8)
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Format(label = "Nació el", order = 5)
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Format(omitLabel = true, order = 4)
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public String toString() {
		return Formatter.of(this).toString();
	}
}
