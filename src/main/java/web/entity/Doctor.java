package web.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import web.formatter.Card;
import web.formatter.Format;
import web.formatter.Formatter;
import web.formatter.TextBlock.Alignment;

import java.util.Date;
import java.util.Set;

@Entity
@Card(name = "Médico", size = 48)
@Table(name = "doctors")
public class Doctor {
    
    private int id;
    private int file;
    private String name;
    private String surname;
    private String sex;
    private Date birth;
    private String address;
    private String localty;
    private String email;
    private String phone;
    private Specialty specialty;
    private User user;
    private Set<Schedule> schedules;
    private boolean active = true;
    

	public Doctor() {}
	
	/* # Getters */
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
	@JsonProperty("id")
    public int getId() {
		return id;
	}
	
	@Column(name = "file", unique = true)
	@Format(label="Legajo", prefix="N.º ", order = 1)
	@JsonProperty("file")
	public int getFile() {
		return file;
	}
	
	@Column(name = "name")
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
	@Column(name = "surname")
	@JsonProperty("surname")
	public String getSurname() {
		return surname;
	}
	
	@Column(name = "sex")
	@Format(label="Sexo", order = 3)
	@JsonProperty("sex")
	public String getSex() {
		return sex;
	}
	
	@Column(name = "birth")
	@Format(label="Nació el", order = 4)
	@JsonProperty("birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirth() {
		return birth;
	}
	
	@Column(name = "address")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 7)
	@JsonProperty("address")
	public String getAddress() {
		return address;
	}
	
	@Column(name = "localty")
	@JsonProperty("localty")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 8)
	public String getLocalty() {
		return localty;
	}

	@Column(name = "email")
	@JsonProperty("email")
	@Format(omitLabel = true, order = 6)
	public String getEmail() {
		return email;
	}
	
	@Column(name = "phone")
	@JsonProperty("phone")
	@Format(omitLabel = true, order = 5)
	public String getPhone() {
		return phone;
	}
	
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
    public User getUser() {
		return user;
	}

	@Transient
	@Format(omitLabel = true, order = 0)
	public String getFullName() {
		return this.getSurname() + ", " + this.getName();
	}

	@Transient
	@Format(omitLabel = true, prefix = "@", order = 9)
	public String getUsername() {
		return getUser().getUsername();
	}

	@Transient
	@Format(label = "Esp.", order = 2)
	public String getSpecialtyName() {
		return specialty.getName();
	}
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "doctor_schedules",
        joinColumns = @JoinColumn(name = "doctor"),
        inverseJoinColumns = @JoinColumn(name = "schedule")
    )
	public Set<Schedule> getSchedules() {
		return this.schedules;
	}
	
	@Column(name = "active")
	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Médico deshabilitado")
	public boolean isActive() {
		return active;
	}
	
	@Transient
	@Format(label = "Horarios")
	public String listSchedules() {
		String schedules = "";
		for(Schedule schedule : this.getSchedules()) {
			schedules += "\n · " + schedule;
		}
		return schedules;
	}
	
	/* # Setters */
	
	public void setId(int id) {
		this.id = id;
	}

	public void setFile(int file) {
		this.file = file;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLocalty(String localty) {
		this.localty = localty;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

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
