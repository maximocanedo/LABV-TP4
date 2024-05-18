package entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
//@Table(name = "Turno")
public class Turno {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private LocalDate fecha;
	private LocalTime hora;
	private String observacion;
	private String estado;
	
	

	
	public Turno(int id, LocalDate fecha, LocalTime hora, String observacion, String estado, Medico medico,
			Paciente paciente) {
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.observacion = observacion;
		this.estado = estado;
		this.medico = medico;
		this.paciente = paciente;
	}
	/************************************/
	@ManyToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name="id_medico")
	private Medico medico;
	
	
	@ManyToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name="id_paciente")
	private Paciente paciente;
	
	
	
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	/************************************/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "id:" + id + ", fecha:" + fecha + ", hora:" + hora + ", observacion:" + observacion + ", estado:"
				+ estado + ", medico:" + medico + ", paciente:" + paciente;
	}
	
	
	
}
