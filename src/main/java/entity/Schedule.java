package entity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.*;

import logicImpl.LocalTimeAttributeConverter;

@Entity
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
	
	@Column(name = "beginDay")
	public Day getBeginDay() {
		return beginDay;
	}
	
	@Column(name = "finishDay")
	public Day getFinishDay() {
		return finishDay;
	}
	
	@Column(name = "startTime")
    @Convert(converter = LocalTimeAttributeConverter.class)
	public LocalTime getStartTime() {
		return startTime;
	}

	@Column(name = "endTime")
    @Convert(converter = LocalTimeAttributeConverter.class)
	public LocalTime getEndTime() {
		return endTime;
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
        String startTime = this.getStartTime().format(formatter);
        String endTime = this.getEndTime().format(formatter);
        
		return startDayName + " " + startTime + " -" + endDayName + " " + endTime;
	}
	
	public int hashCode() {
		return this.getId();
	}
	
}
