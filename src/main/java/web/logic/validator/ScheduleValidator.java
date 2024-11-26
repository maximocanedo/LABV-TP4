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
			throw new ValidationException("Invalid day. ", "Ingresá un día de semana en inglés, y en mayúsculas. Por ejemplo: \"MONDAY\" en lugar de \"lunes\". ");
		}
	}
	
	public Schedule check(Schedule schedule) throws ValidationException {
		// Validar que el día de finalización sea el siguiente
		return schedule;
	}
	
}
