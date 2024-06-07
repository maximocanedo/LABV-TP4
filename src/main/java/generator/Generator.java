package generator;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.hibernate.exception.ConstraintViolationException;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import entity.*;
import logic.*;
import logicImpl.*;

public class Generator {

    private static IUserLogic users = new UserLogicImpl();
    private static IPatientLogic pacientes = new PatientLogicImpl();
    private static IDoctorLogic medicos = new DoctorLogicImpl();
    
    
    
    public static Patient generateRandomPaciente() {
        Faker faker = new Faker();
        Patient paciente = new Patient();
        paciente.setName(faker.name().firstName());
        paciente.setSurname(faker.name().lastName());
        Random random = new Random();
        int dni = random.nextInt(90000000) + 10000000 + random.nextInt(500);
        paciente.setDni(dni);
        paciente.setPhone(faker.phoneNumber().cellPhone());
        paciente.setAddress(faker.address().streetAddress());
        paciente.setLocalty(faker.address().city());
        paciente.setProvince(faker.address().state());
        paciente.setBirth(faker.date().birthday());
        paciente.setEmail(faker.internet().emailAddress());
        return paciente;
    }
    
    public static Appointment generateTurnoForDoctor1234(Patient paciente) {
        Faker faker = new Faker();
        IAppointmentLogic turnos = new AppointmentLogicImpl();
        
        // Encontrar el médico con legajo 1234
        Optional<Doctor> optionalMedico = medicos.findByFile(1234);
        int attempts = 0;
        while(optionalMedico.isEmpty() && attempts < 4) {
        	User user = Generator.generateAndSaveRandomUser();
            Doctor medico = generateRecord(user);
            medico.setFile(1234);
            medicos.add(medico);
            optionalMedico = medicos.findByFile(1234);
            attempts++;
        }
        
        Doctor medico = optionalMedico.get();

        Appointment turno = new Appointment();
        turno.setAssignedDoctor(medico);
        turno.setPatient(paciente);
        turno.setRemarks("");
        turno.setStatus(AppointmentStatus.PENDING);


        Calendar start = Calendar.getInstance();
        start.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        Date startDate = start.getTime();

        Calendar end = Calendar.getInstance();
        end.set(2025, Calendar.JANUARY, 2, 23, 59, 59);
        Date endDate = end.getTime();

        Date randomDate = faker.date().between(startDate, endDate);
        turno.setDate(randomDate);
        try {
        	turnos.register(turno);
        } catch(ConstraintViolationException e) {
        	// Expected error:
        	// El DNI es generado al azar, y puede a veces coincidir con un DNI ya existente.
        }

        return turno;
    }
    
    public static User generateRandomUser() {
        Faker faker = new Faker();
        User user = new User();
        user.setName(faker.name().fullName());
        user.setUsername(faker.name().username());
        user.setPassword("12345678"); // Todos los usuarios generados automáticamente usarán esta contraseña.
        user.setActive(true);
        return user;
    }
    
    public static Doctor generateRecord(User user) {
        Faker faker = new Faker();
        Specialty[] especialidades = Generator.generateRecords();
        Random r = new Random();
        Specialty random = especialidades[r.nextInt(especialidades.length)];
        Doctor medico = new Doctor();
        Name nn = faker.name();
        Address aa = faker.address();
        medico.setSurname(nn.lastName());
        medico.setName(nn.firstName());
        medico.setEmail(faker.internet().emailAddress());
        medico.setSpecialty(random);
        medico.setAddress(aa.fullAddress());
        medico.setBirth(faker.date().birthday());
        medico.setFile(r.nextInt(10000));
        medico.setLocalty(faker.address().city());
        medico.setSex(r.nextInt(10) % 2 == 0 ? "M" : "F");
        medico.setUser(user);
        medico.setPhone(faker.phoneNumber().phoneNumber());
        return medico;
    }
    
    public static User generateAndSaveRecord() {
        User random = generateRandomUser();
        users.signup(random);
        return random;
    }
    
    public static Patient generateAndSaveRandomPaciente() {
        Patient random = generateRandomPaciente();
        pacientes.add(random);
        return random;
    }
    public static boolean EXISTE_LEGAJO_1234 = false;
    
    
    public static Set<Schedule> generateRandomSchedules() {
        Random random = new Random();
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
    
    private static boolean isNonOverlapping(Schedule newSchedule, Set<Schedule> schedules) {
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
    
    public static Doctor generateAndSaveRandomDoctor(User user) {
    	boolean exists = Generator.EXISTE_LEGAJO_1234 || medicos.findByFile(1234).isPresent();
        Doctor medico = generateRecord(user);
        if(!exists) medico.setFile(1234);
        else Generator.EXISTE_LEGAJO_1234 = exists;

        medico.setSchedules(generateRandomSchedules());
        medicos.add(medico);
        return medico;
    }
    public static User generateAndSaveRandomUser() {
        User user = generateRandomUser();
        users.signup(user);
        return user;
    }
    
    public static Appointment generateTurno(Patient p, Doctor m) {
    	Faker f = new Faker();
    	Appointment t = new Appointment();
    	t.setAssignedDoctor(m);
    	t.setPatient(p);
    	t.setRemarks("");
    	t.setStatus(AppointmentStatus.PENDING);
    	t.setDate(f.date().future(1280, TimeUnit.DAYS));
    	IAppointmentLogic turnos = new AppointmentLogicImpl();
    	turnos.register(t);
    	return t;
    	
    }
    
    @SuppressWarnings("deprecation")
	public static Appointment generateTurnoPunto6(Patient p, Doctor m) {
    	Faker f = new Faker();
    	Appointment t = new Appointment();
    	t.setAssignedDoctor(m);
    	t.setPatient(p);
    	t.setRemarks("");
    	boolean presente = f.bool().bool();
    	t.setStatus(presente ? AppointmentStatus.PRESENT : AppointmentStatus.ABSENT);
    	Date d = new Date();
    	d.setDate(1);
    	d.setMonth(0);
    	d.setYear(124);
    	Date d2 = new Date();
    	d2.setDate(1);
    	d2.setMonth(2);
    	d2.setYear(124);
    	t.setDate(f.date().between(d, d2));
    	IAppointmentLogic turnos = new AppointmentLogicImpl();
    	turnos.register(t);
    	return t;
    	
    }
    
    
    public static Specialty[] generateAndSaveRecords() {
        ISpecialtyLogic repo = new SpecialtyLogicImpl();
        Specialty[] especialidades = Generator.generateRecords();
        for (Specialty especialidad : especialidades) {
            repo.add(especialidad);
        }
        return especialidades;
    }
    
    public static Specialty[] generateRecords() {
    	// TODO: Reemplazar por beans
        Specialty cirugiaGeneral = new Specialty();
        cirugiaGeneral.setId(1);
        cirugiaGeneral.setName("Cirugía General");
        cirugiaGeneral.setDescription("Responsable de una amplia gama de condiciones quirúrgicas, como apendicectomías, hernias, entre otras.");

        Specialty pediatria = new Specialty();
        pediatria.setId(2);
        pediatria.setName("Pediatría");
        pediatria.setDescription("Tratan a niños desde recién nacidos hasta la adolescencia, abordando problemas de salud infantil.");

        Specialty ginecologiaObstetricia = new Specialty();
        ginecologiaObstetricia.setId(3);
        ginecologiaObstetricia.setName("Ginecología y Obstetricia");
        ginecologiaObstetricia.setDescription("Se especializan en la salud reproductiva de las mujeres, desde el embarazo hasta la menopausia.");

        Specialty cardiologia = new Specialty();
        cardiologia.setId(4);
        cardiologia.setName("Cardiología");
        cardiologia.setDescription("Diagnostican y tratan enfermedades del corazón y los vasos sanguíneos.");

        Specialty neurologia = new Specialty();
        neurologia.setId(5);
        neurologia.setName("Neurología");
        neurologia.setDescription("Se centran en trastornos del sistema nervioso, como migrañas, epilepsia y enfermedad de Parkinson.");

        Specialty dermatologia = new Specialty();
        dermatologia.setId(6);
        dermatologia.setName("Dermatología");
        dermatologia.setDescription("Tratan enfermedades de la piel, cabello y uñas, como acné, eczema y cáncer de piel.");

        Specialty psiquiatria = new Specialty();
        psiquiatria.setId(7);
        psiquiatria.setName("Psiquiatría");
        psiquiatria.setDescription("Se ocupan de los trastornos mentales, como la depresión, la ansiedad y la esquizofrenia.");

        Specialty oftalmologia = new Specialty();
        oftalmologia.setId(8);
        oftalmologia.setName("Oftalmología");
        oftalmologia.setDescription("Se dedican a la salud ocular, desde exámenes de la vista hasta cirugía de cataratas y corrección de la visión.");

        Specialty otorrinolaringologia = new Specialty();
        otorrinolaringologia.setId(9);
        otorrinolaringologia.setName("Otorrinolaringología");
        otorrinolaringologia.setDescription("Especializados en trastornos del oído, nariz y garganta, como otitis, sinusitis y amigdalitis.");

        Specialty medicinaInterna = new Specialty();
        medicinaInterna.setId(10);
        medicinaInterna.setName("Medicina Interna");
        medicinaInterna.setDescription("Tratan una amplia gama de enfermedades en adultos, desde diabetes hasta enfermedades cardíacas y pulmonares.");
        
        return new Specialty[] {
            cirugiaGeneral, pediatria, ginecologiaObstetricia, cardiologia, 
            neurologia, dermatologia, psiquiatria, oftalmologia, otorrinolaringologia,
            medicinaInterna
        };
    }

    public static void main(String[] args) {
    	Set<Schedule> s = Generator.generateRandomSchedules();
    	for(Schedule sc : s) {
    		System.out.println("\n" + sc);
    	}
    }
   
}
