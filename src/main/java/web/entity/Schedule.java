package web.entity;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Convert;
import web.formatter.Card;
import web.logicImpl.LocalTimeAttributeConverter;

@Entity
@Card(size = 48)
@Table(name = "schedules")
public class Schedule {
	
	private int id;
	private Day beginDay;
	private Day finishDay;
	private LocalTime startTime;
	private LocalTime endTime;
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

	@JsonProperty("startTime")
	@Transient
    @Convert(converter = LocalTimeAttributeConverter.class)
	public LocalTime getStartTimeLT() {
		return startTime;
	}

	@JsonProperty("endTime")
	@Transient
    @Convert(converter = LocalTimeAttributeConverter.class)
	public LocalTime getEndTimeLT() {
		return endTime;
	}

	@JsonIgnore
	@Column(name = "startTime")
	public Time getStartTime() {
	    return startTime != null ? Time.valueOf(startTime) : null;
	}

	@JsonIgnore
	@Column(name = "endTime")
	public Time getEndTime() {
	    return endTime != null ? Time.valueOf(endTime) : null;
	}
	
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

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
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
