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

import formatter.Card;
import formatter.Format;
import formatter.Formatter;
import entity.TurnoEstado;

@Entity
@Card
@Table(name = "turnos")
public class Turno {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private int id;
	
	@Column(name="fecha")
	private Date fecha;
	
	@Column(name="observacion")
	private String observacion;
	
	@Column(name="estado")
	@Enumerated(EnumType.STRING)
	private TurnoEstado estado;
	
	

	
	public Turno(int id, Date fecha, String observacion, TurnoEstado estado, Medico medico,
			Paciente paciente) {
		this.id = id;
		this.fecha = fecha;
		this.observacion = observacion;
		this.estado = estado;
		this.medico = medico;
		this.paciente = paciente;
	}

	public Turno() {
		
	}
	
	
	
	
	@ManyToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name="id_medico")
	private Medico medico;
	
	
	@ManyToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name="id_paciente")
	private Paciente paciente;
	
	
	@Format(label = "Médico asignado: ", prefix = "\n", order = 3)
	public Medico getMedico() {
		return medico;
	}
	
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	
	@Format(label = "Paciente: ", prefix = "\n", order = 4)
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Format(label = "ID N.º", order = 0)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Format(label = "Fecha y hora: ", order = 1)
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Format(label = "Obs.", order = 5)
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	@Format(label = "Estado", order = 2)
	public String getEstadoDescription() {
		return estado == TurnoEstado.AUSENTE ? "AUSENTE" : (estado == TurnoEstado.PRESENTE ? "PRESENTE" : (estado == TurnoEstado.PENDIENTE ? "PENDIENTE" : "¿?"));
	}
	
	public TurnoEstado getEstado() {
		return estado;
	}
	public void setEstado(TurnoEstado estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return Formatter.of(this).toString();
	}
	
	
	
}
