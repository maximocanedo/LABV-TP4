package web.entity;

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
    private int startTime;
    private int endTime;
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

    @Column(name = "startTime")
    @JsonIgnore
    public int getStartTime() {
        return startTime;
    }

    @Column(name = "endTime")
    @JsonIgnore
    public int getEndTime() {
        return endTime;
    }

    @Transient
    @JsonProperty("startTime")
    public String getStartTimeString() {
        return String.format("%02d:00", startTime);
    }

    @Transient
    @JsonProperty("endTime")
    public String getEndTimeString() {
        return String.format("%02d:00", endTime);
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

    public void setStartTime(int startTime) {
        validateHour(startTime);
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        validateHour(endTime);
        this.endTime = endTime;
    }

    @Transient
    public void setStartTimeString(String startTimeString) {
        this.startTime = parseHour(startTimeString);
    }

    @Transient
    public void setEndTimeString(String endTimeString) {
        this.endTime = parseHour(endTimeString);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /* # Métodos auxiliares */

    private void validateHour(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("La hora debe estar entre 0 y 23.");
        }
    }

    private int parseHour(String hourString) {
        try {
            return Integer.parseInt(hourString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de hora no válido: " + hourString, e);
        }
    }

    @Transient
    public String getWeekDaySpanishName(Day day) {
        switch (day) {
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
                return "?";
        }
    }

    @Override
    public String toString() {
        boolean sameDay = this.getBeginDay() == this.getFinishDay();
        String startDayName = this.getWeekDaySpanishName(this.getBeginDay());
        String endDayName = sameDay ? "" : " " + this.getWeekDaySpanishName(this.getFinishDay());
        String startTime = this.getStartTimeString();
        String endTime = this.getEndTimeString();

        return startDayName + " " + startTime + " -" + endDayName + " " + endTime;
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}
