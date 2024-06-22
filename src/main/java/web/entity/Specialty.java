package web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;

@Entity
@Card(size=24)
@Table(name = "specialties")
public class Specialty {
	
    private int id;
    private String name;
    private String descripcion;
    private boolean active = true;

    public Specialty() { }

    /* # Getters */
    
    @Id
    @Column(name = "id")
    @Format(label = "ID", order = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    
    @Column(name = "name")
    @Format(label = "Nombre", order = 1)
    public String getName() {
        return name;
    }
    
    @Column(name = "description")
    @Format(omitLabel=true, order = 2)
    public String getDescription() {
        return descripcion;
    }
    
    @Column(name = "active")
    @Format(omitLabel=true, whenTrue="", whenFalse="(!) Turno eliminado.", order = 3)
    public boolean isActive() {
    	return active;
    }
    
    /* # Setters */
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String nombre) {
        this.name = nombre;
    }
    
    public void setDescription(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setActive(boolean active) {
    	this.active = active;
    }
    
    /* # More methods */
    
    @Override
    public String toString() {
        return Formatter.of(this).toString();
    }
}
