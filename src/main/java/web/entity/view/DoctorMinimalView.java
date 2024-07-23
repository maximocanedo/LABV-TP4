package web.entity.view;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import web.entity.Doctor;
import web.entity.IDoctor;
import web.entity.Schedule;
import web.entity.Specialty;
import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;

@Entity
@Immutable
@Card(name = "Médico", size = 48)
@Table(name = "doctors")
public class DoctorMinimalView implements IDoctor {
	
	public static DoctorMinimalView from(IDoctor data) {
		if(data == null) return null;
		DoctorMinimalView view = new DoctorMinimalView();
		view.setId(data.getId());
		view.setFile(data.getFile());
		view.setName(data.getName());
		view.setSurname(data.getSurname());
		view.setSpecialty(data.getSpecialty());
		
		return view;
	}
	
	public static DoctorMinimalView from(Doctor data) {
		DoctorMinimalView view = new DoctorMinimalView();
		view.setId(data.getId());
		view.setFile(data.getFile());
		view.setName(data.getName());
		view.setSurname(data.getSurname());
		view.setSpecialty(data.getSpecialty());
		view.setUser(UserView.from(data.getUser()));
		return view;
	}
    
    private int id;
    private int file;
    private String name;
    private String surname;
    private Specialty specialty;
    private UserView user;
    private Set<Schedule> schedules;
    private boolean active = true;
    

	public DoctorMinimalView() {}
	
	/* # Getters */
	
	@Id
	@Override
	@JsonProperty("id")
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
		return id;
	}

	@Override
	@Column(name = "file", unique = true)
	@Format(label="Legajo", prefix="N.º ", order = 1)
	@JsonProperty("file")
	public int getFile() {
		return file;
	}

	@Override
	@Column(name = "name")
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@Override
	@Column(name = "surname")
	@JsonProperty("surname")
	public String getSurname() {
		return surname;
	}

	@Override
	@ManyToOne
	@JsonProperty("specialty")
    @JoinColumn(name = "specialty")
	public Specialty getSpecialty() {
		return specialty;
	}

	@OneToOne(cascade = {CascadeType.ALL})
	@JsonProperty("user")
    @JoinColumn(name ="user")
	@JsonBackReference
    public UserView getUser() {
		return user;
	}

	@Override
	@Transient
	@JsonIgnore
	@Format(omitLabel = true, order = 0)
	public String getFullName() {
		return this.getSurname() + ", " + this.getName();
	}

	/*@Override
	@Transient
	@JsonIgnore
	@Format(omitLabel = true, prefix = "@", order = 9)
	public String getUsername() {
		return getUser().getUsername();
	} */

	@Override
	@Transient
	@JsonIgnore
	@Format(label = "Esp.", order = 2)
	public String getSpecialtyName() {
		return specialty.getName();
	}

	@Override
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "doctor_schedules",
        joinColumns = @JoinColumn(name = "doctor"),
        inverseJoinColumns = @JoinColumn(name = "schedule")
    )
	public Set<Schedule> getSchedules() {
		return this.schedules;
	}

	@Override
	@Column(name = "active")
	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Médico deshabilitado")
	public boolean isActive() {
		return active;
	}

	@Override
	@Transient
	@JsonIgnore
	@Format(label = "Horarios")
	public String listSchedules() {
		String schedules = "";
		if(getSchedules() == null) return "";
		for(Schedule schedule : this.getSchedules()) {
			schedules += "\n · " + schedule;
		}
		return schedules;
	}
	
	/* # Setters */

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setFile(int file) {
		this.file = file;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public void setUser(UserView user) {
		this.user = user;
	}

	@Override
	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/* # Otros métodos */
	
	public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
    }

    public void removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
    }
	
	@Override
    public String toString() {
		return Formatter.of(this).toString();
    }
}
