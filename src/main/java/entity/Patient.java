package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import formatter.Card;
import formatter.Format;
import formatter.Formatter;
import formatter.TextBlock.Alignment;

@Entity
@Card(size = 48)
@Table(name="patients")
public class Patient {
	
	private int id;
	private String name;
	private String surname;
	private int dni; // TODO: Comprobar si se puede cambiar a String.
	private String phone;
	private String address;
	private String localty;
	private String province;
	private Date birth;
	private String email;
    private boolean active = true;
	
	public Patient() {}
	
	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Format(label = "ID", order = 1)
	public int getId() {
		return id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "surname")
	public String getSurname() {
		return surname;
	}

	@Column(name = "dni", unique = true)
	@Format(label = "DNI N.º", order = 2)
	public int getDni() {
		return dni;
	}

	@Column(name = "phone")
	@Format(omitLabel = true, order = 3)
	public String getPhone() {
		return phone;
	}

	@Column(name = "address")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 6)
	public String getAddress() {
		return address;
	}

	@Column(name = "localty")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 7)
	public String getLocalty() {
		return localty;
	}

	@Column(name = "province")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 8)
	public String getProvince() {
		return province;
	}

	@Column(name = "birth")
	@Format(label = "Nació el", order = 5)
	public Date getBirth() {
		return birth;
	}

	@Column(name = "email")
	@Format(omitLabel = true, order = 4)
	public String getEmail() {
		return email;
	}

	@Column(name = "active")
	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Paciente deshabilitado. ")
	public boolean isActive() {
		return active;
	}

	@Transient
	@Format(omitLabel = true, order = 0)
	public String getFullName() {
		return this.getSurname() + ", " + this.getName();
	}
	
	/* # Setters */
	
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String nombre) {
		this.name = nombre;
	}

	public void setSurname(String apellido) {
		this.surname = apellido;
	}

	public void setDni(int dni) {
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
	public void iniPatient() {
		System.out.println("Se inicializa bean");
	} 
	public void destroyPatient(){
		System.out.println("Se destruye bean");
	}
}
