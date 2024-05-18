package entity;

import javax.persistence.*;

import utils.FormattedLine;
import utils.IFormattedLine;
import utils.FormattedLine.Alignment;

import java.util.Date;

@Entity
@Table(name = "medicos")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
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

    @OneToOne(mappedBy = "medico")
    private User user;
    
    public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Medico() {
    }

  
    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


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


	public String getSexo() {
		return sexo;
	}


	public void setSexo(String sexo) {
		this.sexo = sexo;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public Especialidad getEspecialidad() {
		return especialidad;
	}


	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}


	@Override
    public String toString() {
		
		IFormattedLine header = new FormattedLine("[Médico]");
		header.setLineSize(48);
		header.setTopHeader(true);
		header.setAlignment(Alignment.RIGHT);
		String cont = "";
		String[] lines = new String[] {
			apellido + ", " + nombre,
			"Especialidad: " + especialidad.getDescripcion(),
			"Legajo: " + legajo,
			"Sexo: " + sexo,
			"Fecha de nacimiento: " + fechaNacimiento,
		};
		for(String line: lines) {
			cont += line + "\n";
		}
		IFormattedLine content = new FormattedLine(cont);
		content.setLineSize(48);
		IFormattedLine contact = new FormattedLine(
			direccion + "\n" +
			localidad + "\n" +
			correo + "\n" +
			telefono  + "\n"
		);
		contact.setAlignment(Alignment.RIGHT);
		contact.setLineSize(48);
		
		IFormattedLine end = new FormattedLine("···");
		end.setAlignment(FormattedLine.Alignment.CENTER);
		end.setTopHeader(true);
		end.setHeaderMiddleDelimiters('—');
		end.setLineSize(48);
		
		String tot = header.toString() + content.toString() + contact.toString();
		
		return tot + end.toString();
    }
}
