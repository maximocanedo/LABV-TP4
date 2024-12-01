package web.logic.validator;

import org.springframework.stereotype.Component;

import web.entity.Day;
import web.entity.Schedule;
import web.exceptions.ValidationException;

@Component("scheduleValidator")
public class ScheduleValidator {

    public Day day(String day) throws ValidationException {
        try {
            return Day.valueOf(day.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException(
                "Dia no valido",
                "ingrese un dia valido"
            );
        }
    }

    public Schedule check(Schedule schedule) throws ValidationException {
        Day beginDay = schedule.getBeginDay();
        Day finishDay = schedule.getFinishDay();
        int startTime = schedule.getStartTime();
        int endTime = schedule.getEndTime();

        if (finishDay != beginDay && finishDay != beginDay.next()) {
            throw new ValidationException(
                "Rango incorrecto de dia",
                "El dia de finalizacion debe ser el mismo o el siguiente al dia de inicio"
            );
        }

        if (startTime < 0 || startTime >= 24 || endTime < 0 || endTime >= 24) {
            throw new ValidationException(
                "Rango incorrecto de tiempo",
                "Las horas deben estar en el rango de 0 a 23"
            );
        }

        if (finishDay == beginDay) {
            if (startTime >= endTime) {
                throw new ValidationException(
                    "Rango incorrecto de tiempo",
                    "La hora de inicio debe ser anterior a la hora de fin cuando ambos son el mismo día."
                );
            }
        } else if (finishDay == beginDay.next()) {
            if (startTime <= endTime) {
                throw new ValidationException(
                    "Rango incorrecto de tiempo",
                    "Para horarios que cruzan al dia siguiente, la hora de inicio debe ser mayor a la hora de fin"
                );
            }
        }

        return schedule;
    }
}
