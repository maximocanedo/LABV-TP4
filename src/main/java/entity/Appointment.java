package entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import formatter.Card;
import formatter.Format;
import formatter.Formatter;
import entity.AppointmentStatus;

@Entity
@Card(size = 64)
@Table(name = "appointments")
public class Appointment {
	
    private int id;
	private Date date;
	private String remarks;
	private AppointmentStatus status;
	private Doctor assignedDoctor;
	private Patient patient;
    private boolean active = true;
	
	@Deprecated
	public Appointment(int id, Date fecha, String observacion, AppointmentStatus estado, Doctor medico,
			Patient paciente) {
		this.id = id;
		this.date = fecha;
		this.remarks = observacion;
		this.status = estado;
		this.assignedDoctor = medico;
		this.patient = paciente;
	}

	public Appointment() {}
	
	/* # Getters */
	
	@Id
	@Column(name = "id")
	@Format(label = "ID N.º", order = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	@Column(name = "date")
	@Format(label = "Fecha y hora", order = 1)
	public Date getDate() {
		return date;
	}

	@Column(name = "notes")
	@Format(label = "Obs.", order = 5)
	public String getRemarks() {
		return remarks;
	}
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	public AppointmentStatus getStatus() {
		return status;
	}

	@ManyToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name="doctor") // Old: id_medico
	@Format(label = "Médico asignado", prefix = "\n", order = 3)
	public Doctor getAssignedDoctor() {
		return assignedDoctor;
	}
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "patient") // Old: id_paciente
	@Format(label = "Paciente", prefix = "\n", order = 4)
	public Patient getPatient() {
		return patient;
	}

	@Column(name = "active")
	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Turno deshabilitado. ")
	public boolean isActive() {
		return this.active;
	}

	@Transient
	@Format(label = "Estado", order = 2)
	public String getStatusDescription() {
		return status == AppointmentStatus.ABSENT ? "AUSENTE" : (status == AppointmentStatus.PRESENT ? "PRESENTE" : (status == AppointmentStatus.PENDING ? "PENDIENTE" : "¿?"));
	}
	
	/* # Setters */

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(Date fecha) {
		this.date = fecha;
	}

	public void setRemarks(String observacion) {
		this.remarks = observacion;
	}

	public void setStatus(AppointmentStatus estado) {
		this.status = estado;
	}

	public void setAssignedDoctor(Doctor medico) {
		this.assignedDoctor = medico;
	}
	
	public void setPatient(Patient paciente) {
		this.patient = paciente;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	/* # Otros métodos */
	
	@Override
	public String toString() {
		return Formatter.of(this).toString();
	}	
	
}
