package web.entity;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import web.formatter.Card;

@Entity
@Card(size = 48)
@Table(name = "schedules")
public class Schedule {
	
	private int id;
	private Day day;
	private int time;
	private boolean active;
	
	public Schedule() {}
	
	/* # Getters */
	
	@Id
	@Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "day")
	public Day getDay() {
		return day;
	}

	@JsonIgnore
	@Column(name = "time")
	public int getTime() {
	    return time;
	}

	@Transient
	@JsonProperty("time")
	public String getTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(time);
	}


	@Column(name = "active")
	public boolean isActive() {
		return active;
	}
	
	/* # Setters */
	
	public void setId(int id) {
		this.id = id;
	}

	public void setDay(Day day) {
		this.day = day;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/* # Otros métodos */
	
	@Transient
	public String getWeekDaySpanishName(Day day) {
		switch(day) {
			case SUNDAY: 
				return "Domingo";
			case MONDAY: 
				return "Lunes";
			case TUESDAY: 
				return "Martes";
			case WEDNESDAY: 
				return "Miércoles";
			case THURSDAY: 
				return "Jueves";
			case FRIDAY: 
				return "Viernes";
			case SATURDAY: 
				return "Sábado";
			default: 
				return "<?>";	
		}
	}
	
	public String toString() {
        String dayName = this.getWeekDaySpanishName(this.getDay());
        int formattedTime = this.getTime();
		return dayName + " " + formattedTime;
	}
	
	public int hashCode() {
		return this.getId();
	}
}
