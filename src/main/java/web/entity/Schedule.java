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
	private Day beginDay;
	private Day finishDay;
	private Time startTime;
	private Time endTime;
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
	@Column(name = "beginDay")
	public Day getBeginDay() {
		return beginDay;
	}


	@Enumerated(EnumType.STRING)
	@Column(name = "finishDay")
	public Day getFinishDay() {
		return finishDay;
	}
	
	
	// Métodos problemáticos

	@JsonIgnore
	@Transient
   // @Convert(converter = LocalTimeAttributeConverter.class)
	public LocalTime getStartTimeLT() {
		return startTime.toLocalTime();
	}

	@JsonIgnore
	@Transient
    //@Convert(converter = LocalTimeAttributeConverter.class)
	public LocalTime getEndTimeLT() {
		return endTime.toLocalTime();
	}

	@JsonIgnore
	//@JsonFormat(pattern = "HH:mm:ss")
	@Column(name = "startTime")
	public Time getStartTime() {
	    return startTime;
	}

	@JsonIgnore
	@Column(name = "endTime")
	public Time getEndTime() {
	    return endTime;
	}

	@Transient
	@JsonProperty("startTime")
	public String getStartTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(startTime);
	}
	
	@Transient
	@JsonProperty("endTime")
	public String getEndTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(endTime);
	}

	@Transient
	public void setEndTimeString(String endTimeString) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            java.util.Date date = sdf.parse(endTimeString);
            Time time = new Time(date.getTime());
            setEndTime(time);
        } catch (ParseException e) {
            System.out.println("Error al parsear la cadena: " + e.getMessage());
        }
	}

	@Transient
	public void setStartTimeString(String startTimeString) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            java.util.Date date = sdf.parse(startTimeString);
            Time time = new Time(date.getTime());
            setStartTime(time);
        } catch (ParseException e) {
            System.out.println("Error al parsear la cadena: " + e.getMessage());
        }
	}
	
	
	// Fin métodos problemáticos
	
	
	@Column(name = "active")
	public boolean isActive() {
		return active;
	}
	
	/* # Setters */
	
	public void setId(int id) {
		this.id = id;
	}

	public void setBeginDay(Day beginDay) {
		this.beginDay = beginDay;
	}

	public void setFinishDay(Day finishDay) {
		this.finishDay = finishDay;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public void setStartTime(LocalTime startTime) {
		this.startTime = Time.valueOf(startTime);
	}
	
	public void setEndTime(Time time) {
		this.endTime = time;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = Time.valueOf(endTime);
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
				return "Sábados";
			default: 
				return "<?>";
		}
	}
	
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		boolean sameDay = this.getBeginDay() == this.getFinishDay();
		String startDayName = this.getWeekDaySpanishName(this.getBeginDay());
		String endDayName = sameDay ? "" : " " + this.getWeekDaySpanishName(this.getFinishDay());
        String startTime = this.getStartTimeLT().format(formatter);
        String endTime = this.getEndTimeLT().format(formatter);
        
		return startDayName + " " + startTime + " -" + endDayName + " " + endTime;
	}
	
	public int hashCode() {
		return this.getId();
	}
	
}
