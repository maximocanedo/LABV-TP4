package web.entity.view;

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

import org.hibernate.annotations.Immutable;

import web.entity.Appointment;
import web.entity.AppointmentStatus;
import web.entity.IAppointment;
import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;

@Entity
@Card(size = 64)
@Immutable
@Table(name = "appointments")
public class AppointmentCommunicationView implements IAppointment {
	
	public static AppointmentCommunicationView from(IAppointment data) {
		if(data == null) return null;
		AppointmentCommunicationView view = new AppointmentCommunicationView();
		view.setId(data.getId());
		view.setRemarks(data.getRemarks());
		view.setStatus(data.getStatus());
		view.setActive(data.isActive());
		return view;
	}
	
	public static AppointmentCommunicationView from(Appointment data) {
		if(data == null) return null;
		AppointmentCommunicationView view = new AppointmentCommunicationView();
		view.setId(data.getId());
		view.setRemarks(data.getRemarks());
		view.setStatus(data.getStatus());
		view.setActive(data.isActive());
		view.setDate(data.getDate());
		view.setAssignedDoctor(DoctorMinimalView.from(data.getAssignedDoctor()));
		view.setPatient(PatientCommunicationView.from(data.getPatient()));
		return view;
	}
	
    private int id;
	private Date date;
	private String remarks;
	private AppointmentStatus status;
	private DoctorMinimalView assignedDoctor;
	private PatientCommunicationView patient;
    private boolean active = true;

	public AppointmentCommunicationView() {}
	
	/* # Getters */
	
	/* (non-Javadoc)
	 * @see web.entity.IAppointment#getId()
	 */
	@Override
	@Id
	@Column(name = "id")
	@Format(label = "ID N.º", order = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see web.entity.IAppointment#getDate()
	 */
	@Override
	@Column(name = "date")
	@Format(label = "Fecha y hora", order = 1)
	public Date getDate() {
		return date;
	}

	/* (non-Javadoc)
	 * @see web.entity.IAppointment#getRemarks()
	 */
	@Override
	@Column(name = "notes")
	@Format(label = "Obs.", order = 5)
	public String getRemarks() {
		return remarks;
	}
	
	/* (non-Javadoc)
	 * @see web.entity.IAppointment#getStatus()
	 */
	@Override
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	public AppointmentStatus getStatus() {
		return status;
	}

	@ManyToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name="doctor") // Old: id_medico
	@Format(label = "Médico asignado", prefix = "\n", order = 3)
	public DoctorMinimalView getAssignedDoctor() {
		return assignedDoctor;
	}
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "patient") // Old: id_paciente
	@Format(label = "Paciente", prefix = "\n", order = 4)
	public PatientCommunicationView getPatient() {
		return patient;
	}

	/* (non-Javadoc)
	 * @see web.entity.IAppointment#isActive()
	 */
	@Override
	@Column(name = "active")
	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Turno deshabilitado. ")
	public boolean isActive() {
		return this.active;
	}

	/* (non-Javadoc)
	 * @see web.entity.IAppointment#getStatusDescription()
	 */
	@Override
	@Transient
	@Format(label = "Estado", order = 2)
	public String getStatusDescription() {
		return status == AppointmentStatus.ABSENT ? "AUSENTE" : (status == AppointmentStatus.PRESENT ? "PRESENTE" : (status == AppointmentStatus.PENDING ? "PENDIENTE" : (status == AppointmentStatus.CANCELLED ? "CANCELADO":"¿?")));
	}
	
	/* # Setters */

	/* (non-Javadoc)
	 * @see web.entity.IAppointment#setId(int)
	 */
	@Override
	public void setId(int id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see web.entity.IAppointment#setDate(java.util.Date)
	 */
	@Override
	public void setDate(Date fecha) {
		this.date = fecha;
	}

	/* (non-Javadoc)
	 * @see web.entity.IAppointment#setRemarks(java.lang.String)
	 */
	@Override
	public void setRemarks(String observacion) {
		this.remarks = observacion;
	}

	/* (non-Javadoc)
	 * @see web.entity.IAppointment#setStatus(web.entity.AppointmentStatus)
	 */
	@Override
	public void setStatus(AppointmentStatus estado) {
		this.status = estado;
	}

	public void setAssignedDoctor(DoctorMinimalView medico) {
		this.assignedDoctor = medico;
	}
	
	public void setPatient(PatientCommunicationView paciente) {
		this.patient = paciente;
	}

	/* (non-Javadoc)
	 * @see web.entity.IAppointment#setActive(boolean)
	 */
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/* # Otros métodos */
	
	@Override
	public String toString() {
		return Formatter.of(this).toString();
	}	
	
}
