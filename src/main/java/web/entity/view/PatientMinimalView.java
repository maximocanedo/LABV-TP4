package web.entity.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import web.entity.IPatient;
import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;

@Entity
@Immutable
@Card(size = 48)
@Table(name="patients")
public class PatientMinimalView implements IPatient {
	
	private int id;
	private String name;
	private String surname;
    private boolean active = true;
	
	public PatientMinimalView() {}
	
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
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/* # Otros métodos */
	
	public String toString() {
		return Formatter.of(this).toString();
	}
}
