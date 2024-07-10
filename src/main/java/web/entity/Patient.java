package web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;
import web.formatter.TextBlock.Alignment;

@Entity
@Card(size = 48)
@Table(name="patients")
public class Patient implements IPatient {
	
	private int id;
	private String name;
	private String surname;
	private String dni; 
	private String phone;
	private String address;
	private String localty;
	private String province;
	private Date birth;
	private String email;
    private boolean active = true;
	
	public Patient() {}
	
	@Id
	@Override
	@JsonProperty("id")
	@Format(label = "ID", order = 1)
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	@Column(name = "name")
	@JsonProperty("name")
	@Override
	public String getName() {
		return name;
	}

	@Column(name = "surname")
	@JsonProperty("surname")
	@Override
	public String getSurname() {
		return surname;
	}

	@Column(name = "dni", unique = true)
	@Format(label = "DNI N.º", order = 2)
	@JsonProperty("dni")
	public String getDni() {
		return dni;
	}

	@Column(name = "phone")
	@Format(omitLabel = true, order = 3)
	@JsonProperty("phone")
	public String getPhone() {
		return phone;
	}

	@Column(name = "address")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 6)
	@JsonProperty("address")
	public String getAddress() {
		return address;
	}

	@Column(name = "localty")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 7)
	@JsonProperty("localty")
	public String getLocalty() {
		return localty;
	}

	@Column(name = "province")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 8)
	@JsonProperty("province")
	public String getProvince() {
		return province;
	}

	@Column(name = "birth")
	@Format(label = "Nació el", order = 5)
	@JsonProperty("birth")
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getBirth() {
		return birth;
	}

	@Column(name = "email")
	@Format(omitLabel = true, order = 4)
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@Column(name = "active")
	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Paciente deshabilitado. ")
	@JsonProperty("active")
	public boolean isActive() {
		return active;
	}

	@Transient
	@Format(omitLabel = true, order = 0)
	@JsonIgnore
	public String getFullName() {
		return this.getSurname() + ", " + this.getName();
	}
	
	/* # Setters */

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setName(String nombre) {
		this.name = nombre;
	}

	@Override
	public void setSurname(String apellido) {
		this.surname = apellido;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public void setPhone(String telefono) {
		this.phone = telefono;
	}

	public void setAddress(String direccion) {
		this.address = direccion;
	}

	public void setLocalty(String localidad) {
		this.localty = localidad;
	}

	public void setProvince(String provincia) {
		this.province = provincia;
	}

	public void setBirth(Date fechaNacimiento) {
		this.birth = fechaNacimiento;
	}

	public void setEmail(String correo) {
		this.email = correo;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/* # Otros métodos */
	
	public String toString() {
		return Formatter.of(this).toString();
	}
}
