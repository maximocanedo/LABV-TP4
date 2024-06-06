package entity;

import javax.persistence.*;

import formatter.Card;
import formatter.Format;
import formatter.Formatter;
import formatter.TextBlock.Alignment;

import java.util.Date;

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
    private boolean active = true;
    

	public Doctor() {}
	
	/* # Getters */
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    public int getId() {
		return id;
	}
	
	@Column(name = "file", unique = true)
	@Format(label="Legajo", prefix="N.º ", order = 1)
	public int getFile() {
		return file;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	@Column(name = "surname")
	public String getSurname() {
		return surname;
	}
	
	@Column(name = "sex")
	@Format(label="Sexo", order = 3)
	public String getSex() {
		return sex;
	}
	
	@Column(name = "birth")
	@Format(label="Nació el", order = 4)
	public Date getBirth() {
		return birth;
	}
	
	@Column(name = "address")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 7)
	public String getAddress() {
		return address;
	}
	
	@Column(name = "localty")
	@Format(omitLabel = true, align = Alignment.RIGHT, order = 8)
	public String getLocalty() {
		return localty;
	}

	@Column(name = "email")
	@Format(omitLabel = true, order = 6)
	public String getEmail() {
		return email;
	}
	
	@Column(name = "phone")
	@Format(omitLabel = true, order = 5)
	public String getPhone() {
		return phone;
	}
	
	@ManyToOne
    @JoinColumn(name = "specialty")
	public Specialty getSpecialty() {
		return specialty;
	}

	@OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name ="user")
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

	@Column(name = "active")
	@Format(omitLabel = true, whenTrue = "", whenFalse = "(!) Médico deshabilitado")
	public boolean isActive() {
		return active;
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

	public void setActive(boolean active) {
		this.active = active;
	}
	
	/* # Otros métodos */
	
	@Override
    public String toString() {
		return Formatter.of(this).toString();
    }
}
