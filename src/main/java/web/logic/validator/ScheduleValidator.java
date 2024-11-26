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
		if(schedule.getBeginDay().next() == schedule.getFinishDay())
			throw new ValidationException("Invalid day range. ", "El día de finalización debe ser el mismo día o como mucho el día siguiente al día de inicio. Si necesita abarcar varios días, cree varios horarios para cada día. ");
		if(schedule.getStartTimeLT().isAfter(schedule.getEndTimeLT()) && schedule.getBeginDay().next() == schedule.getFinishDay())
			throw new ValidationException("Invalid time range. ", "La hora de salida debe ser posterior a la hora de inicio, salvo cuando el día de salida es posterior al día de inicio. ");
		return schedule;
	}
	
}
