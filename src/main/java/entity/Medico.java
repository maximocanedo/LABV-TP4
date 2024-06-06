package entity;

import javax.persistence.*;

import formatter.Card;
import formatter.Format;
import formatter.Formatter;
import formatter.TextBlock.Alignment;

import java.util.Date;

@Entity
@Card(name = "Médico", size = 48)
@Table(name = "medicos")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    @Column(unique = true)
    private int legajo;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String sexo;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column
    private String direccion;

    @Column
    private String localidad;

    @Column
    private String correo;

    @Column
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;

    @OneToOne(cascade= {CascadeType.ALL})
    @JoinColumn(name="usuario")
    private User usuario;
    
    @Column
    private boolean active = true;
    

	public Medico() {
    }
	
	public void setActiveStatus(boolean active) {
		this.active = active;
	}

	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Médico deshabilitado")
	public boolean isActive() {
		return active;
	}
	
    
    @Format(omitLabel = true, prefix = "@", order = 9)
    public String getUsername() {
    	return getUser().getUsername();
    }
    
    public User getUser() {
		return usuario;
	}


	public void setUser(User user) {
		this.usuario = user;
	}

	
    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@Format(label="Legajo", prefix="N.º ", order = 1)
	public int getLegajo() {
		return legajo;
	}


	public void setLegajo(int legajo) {
		this.legajo = legajo;
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
	
	@Format(omitLabel = true, order = 0)
	public String getFullName() {
		return this.getApellido() + ", " + this.getNombre();
	}

	@Format(label="Sexo", order = 3)
	public String getSexo() {
		return sexo;
	}


	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Format(label="Nació el", order = 4)
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Format(omitLabel = true, align = Alignment.RIGHT, order = 7)
	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Format(omitLabel = true, align = Alignment.RIGHT, order = 8)
	public String getLocalidad() {
		return localidad;
	}


	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Format(omitLabel = true, order = 6)
	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Format(omitLabel = true, order = 5)
	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Format(label = "Esp.", order = 2)
	public String getSpecialityName() {
		return especialidad.getNombre();
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}


	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}


	@Override
    public String toString() {
		return Formatter.of(this).toString();
    }
}
