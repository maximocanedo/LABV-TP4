package web.generator;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import web.daoImpl.ScheduleDAOImpl;
import web.entity.Day;
import web.entity.Doctor;
import web.entity.Schedule;
import web.entity.Specialty;
import web.entity.User;
import web.logic.IDoctorLogic;

@Component("doctorGenerator")
public class DoctorGenerator implements IEntityGenerator<Doctor> {

	@Autowired
    private IDoctorLogic medicos;
	
	@Autowired
	private SpecialtyGenerator specialtyGenerator;
	
	@Autowired
	private UserGenerator userGenerator;
	
	@Autowired
	private Random random;
	
	@Autowired
	private Faker faker;
	
	@Autowired
	private ScheduleDAOImpl schedulesrepository;
	
	public static boolean EXISTE_LEGAJO_1234 = false;
    
	public DoctorGenerator() {}

	@Override
	public Doctor generate() {
		User user = userGenerator.generate();
		return generate(user);
	}

	@Override
	public Doctor save(User requiring) {
		boolean exists = EXISTE_LEGAJO_1234 || medicos.findByFile(1234, requiring).isPresent();
	    Doctor medico = generate();
	    if(!exists) medico.setFile(1234);
	    else EXISTE_LEGAJO_1234 = exists;
	
	    medico.setSchedules(generateRandomSchedules());
	    medicos.add(medico, requiring);
	    return medico;
	}
	
	public Doctor save(User user, User requiring) {
		boolean exists = EXISTE_LEGAJO_1234 || medicos.findByFile(1234, requiring).isPresent();
	    Doctor medico = generate(user);
	    if(!exists) medico.setFile(1234);
	    else EXISTE_LEGAJO_1234 = exists;
	
	    medico.setSchedules(generateRandomSchedules());
	    medicos.add(medico, requiring);
	    return medico;
	}
	
	public Doctor save(Name n, User user, User requiring) {
		boolean exists = EXISTE_LEGAJO_1234 || medicos.findByFile(1234, requiring).isPresent();
	    Doctor medico = generate(user);
	    if(!exists) medico.setFile(1234);
	    else EXISTE_LEGAJO_1234 = exists;
		Set<Schedule> sss = generateRandomSchedules();
		for(Schedule s : sss) {
			schedulesrepository.save(s);
		}
		medico.setSchedules(sss);
	    medico.setName(n.firstName());
	    medico.setSurname(n.lastName());
	    System.out.println("Número de médico: " + medico.getPhone());
	    medicos.add(medico, requiring);
	    return medico;
	}
	
	public Doctor generate(User user) {
        Specialty[] especialidades = specialtyGenerator.generate();
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
        String pn = "+54 11 " + (5000 + random.nextInt(2999)) + " " + (1000 + random.nextInt(8999));
        medico.setPhone(pn);
        return medico;
	}

	public int randomMinutes() {
		int[] mins = new int[] {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
		return mins[random.nextInt(mins.length)];
	}

    public Set<Schedule> generateRandomSchedules() {
        Set<Schedule> schedules = new HashSet<>();
        
        // Número de horarios a generar
        int numberOfSchedules = random.nextInt(5) + 1; // 1-5 horarios
        
        while (schedules.size() < numberOfSchedules) {
            Day beginDay = Day.values()[random.nextInt(Day.values().length)];
            int duration = random.nextInt(12) + 1; // Máximo 12 h
            int time = (duration);

            Day day = beginDay;
           

            Schedule newSchedule = new Schedule();
            newSchedule.setDay(day);
            newSchedule.setTime(time);
            newSchedule.setActive(true); 

            if (isNonOverlapping(newSchedule, schedules)) {
                schedules.add(newSchedule);
            }
        }

        return schedules;
    	
    }
    
    private boolean isNonOverlapping(Schedule newSchedule, Set<Schedule> schedules) {
        for (Schedule existingSchedule : schedules) {
            if (newSchedule.getDay() == existingSchedule.getDay()) {  
            	return false;
            }
        }
        return true;
    }
    
	
}

