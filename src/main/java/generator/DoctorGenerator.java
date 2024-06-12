package generator;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import entity.Day;
import entity.Doctor;
import entity.Schedule;
import entity.Specialty;
import entity.User;
import logic.IDoctorLogic;

@Component
public class DoctorGenerator implements IEntityGenerator<Doctor> {

	@Autowired
    private IDoctorLogic medicos;
	
	@Autowired
	private SpecialtyGenerator sg;
	
	@Autowired
	private UserGenerator ug;
	
	@Autowired
	private Random random;
	
	@Autowired
	private Faker faker;
	
	public static boolean EXISTE_LEGAJO_1234 = false;
    
	public DoctorGenerator() {}

	@Override
	public Doctor generate() {
		User user = ug.generate();
		return generate(user);
	}

	@Override
	public Doctor save() {
		boolean exists = EXISTE_LEGAJO_1234 || medicos.findByFile(1234).isPresent();
	    Doctor medico = generate();
	    if(!exists) medico.setFile(1234);
	    else EXISTE_LEGAJO_1234 = exists;
	
	    medico.setSchedules(generateRandomSchedules());
	    medicos.add(medico);
	    return medico;
	}
	
	public Doctor save(User user) {
		boolean exists = EXISTE_LEGAJO_1234 || medicos.findByFile(1234).isPresent();
	    Doctor medico = generate(user);
	    if(!exists) medico.setFile(1234);
	    else EXISTE_LEGAJO_1234 = exists;
	
	    medico.setSchedules(generateRandomSchedules());
	    medicos.add(medico);
	    return medico;
	}
	
	public Doctor generate(User user) {
        Specialty[] especialidades = sg.generate();
        Specialty ss = especialidades[random.nextInt(especialidades.length)];
        Doctor medico = new Doctor();
        Name nn = faker.name();
        Address aa = faker.address();
        medico.setSurname(nn.lastName());
        medico.setName(nn.firstName());
        medico.setEmail(faker.internet().emailAddress());
        medico.setSpecialty(ss);
        medico.setAddress(aa.fullAddress());
        medico.setBirth(faker.date().birthday());
        medico.setFile(random.nextInt(10000));
        medico.setLocalty(faker.address().city());
        medico.setSex(random.nextInt(10) % 2 == 0 ? "M" : "F");
        medico.setUser(user);
        medico.setPhone(faker.phoneNumber().phoneNumber());
        return medico;
	}


    public Set<Schedule> generateRandomSchedules() {
        Set<Schedule> schedules = new HashSet<>();
        
        // Número de horarios a generar
        int numberOfSchedules = random.nextInt(5) + 1; // 1-5 horarios
        
        while (schedules.size() < numberOfSchedules) {
            Day beginDay = Day.values()[random.nextInt(Day.values().length)];
            LocalTime startTime = LocalTime.of(random.nextInt(24), random.nextInt(60));
            int duration = random.nextInt(12) + 1; // Máximo 12 h
            LocalTime endTime = startTime.plusHours(duration);

            Day finishDay = beginDay;
            if (endTime.isBefore(startTime)) {
                finishDay = Day.values()[(beginDay.ordinal() + 1) % Day.values().length];
            }

            Schedule newSchedule = new Schedule();
            newSchedule.setBeginDay(beginDay);
            newSchedule.setFinishDay(finishDay);
            newSchedule.setStartTime(startTime);
            newSchedule.setEndTime(endTime);
            newSchedule.setActive(true); 

            if (isNonOverlapping(newSchedule, schedules)) {
                schedules.add(newSchedule);
            }
        }

        return schedules;
    	
    }
    
    private boolean isNonOverlapping(Schedule newSchedule, Set<Schedule> schedules) {
        for (Schedule existingSchedule : schedules) {
            if (newSchedule.getBeginDay() == existingSchedule.getBeginDay()) {
                // Horarios en el mismo día
                if (newSchedule.getEndTime().isAfter(existingSchedule.getStartTime()) &&
                    newSchedule.getStartTime().isBefore(existingSchedule.getEndTime())) {
                    return false;
                }
            } else if (newSchedule.getFinishDay() == existingSchedule.getBeginDay()) {
                // Horario termina al otro día
                if (newSchedule.getEndTime().isAfter(existingSchedule.getStartTime()) &&
                    newSchedule.getEndTime().isBefore(existingSchedule.getEndTime())) {
                    return false;
                }
            } else if (newSchedule.getBeginDay() == existingSchedule.getFinishDay()) {
                // Horario comienza al final del día anterior
                if (newSchedule.getStartTime().isBefore(existingSchedule.getEndTime()) &&
                    newSchedule.getStartTime().isAfter(existingSchedule.getStartTime())) {
                    return false;
                }
            }
        }
        return true;
    }
    
	
}

