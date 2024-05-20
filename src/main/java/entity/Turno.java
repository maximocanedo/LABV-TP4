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

import utils.FormattedLine;
import utils.IFormattedLine;
import utils.FormattedLine.Alignment;

@Entity
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public TurnoEstado getEstado() {
		return estado;
	}
	public void setEstado(TurnoEstado estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		final int lineSize = 64;
		IFormattedLine header = new FormattedLine("[Turno]");
		header.setLineSize(lineSize);
		header.setTopHeader(true);
		header.setAlignment(Alignment.RIGHT);
		String cont = "";
		String[] lines = new String[] {
			"Médico: ",
			medico.toString(),
			"Paciente: ",
			paciente.toString(),
			fecha.toString(),
			"Observaciones: " + observacion,
		};
		for(String line: lines) {
			cont += line + "\n";
		}
		IFormattedLine content = new FormattedLine(cont);
		content.setLineSize(lineSize);
		IFormattedLine contact = new FormattedLine(
			this.getEstado().toString()
		);
		contact.setAlignment(Alignment.RIGHT);
		contact.setLineSize(lineSize);
		
		IFormattedLine end = new FormattedLine("···");
		end.setAlignment(FormattedLine.Alignment.CENTER);
		end.setTopHeader(true);
		end.setHeaderMiddleDelimiters('—');
		end.setLineSize(lineSize);
		
		String tot = header.toString() + content.toString() + contact.toString();
		
		return tot + end.toString();
	}
	
	
	
}
