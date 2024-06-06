package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import formatter.Card;
import formatter.Format;
import formatter.Formatter;

@Card(size=24)
@Entity
@Table(name = "especialidades")
public class Especialidad {
	
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nombre;

    @Column
    private String descripcion;
    
    @Column
    private boolean active;

    public Especialidad() {
    }

    @Format(omitLabel=true, whenTrue="", whenFalse="Turno eliminado", order = 3)
    public boolean isActive() {
    	return active;
    }
    
    public void setActiveStatus(boolean active) {
    	this.active = active;
    }
    
    @Format(label="ID", order = 0)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Format(label="Nombre", order = 1)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Format(omitLabel=true, order = 2)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return Formatter.of(this).toString();
    }
}
