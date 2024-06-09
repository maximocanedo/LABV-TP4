package generator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import entity.*;
import logic.*;
import logicImpl.*;

@Component
public class Generator {

    private static final IUserLogic users = new UserLogicImpl();
    private static final IPatientLogic pacientes = new PatientLogicImpl();
    private static final IDoctorLogic medicos = new DoctorLogicImpl();
    private static final IAppointmentLogic turnos = new AppointmentLogicImpl();
    private static final ISpecialtyLogic repo = new SpecialtyLogicImpl();

    private static final Faker faker = new Faker();

    public static void main(String[] args) {
        Set<Schedule> schedules = generateRandomSchedules();
        schedules.forEach(System.out::println);
    }

    public static Patient generateRandomPaciente() {
        Patient paciente = new Patient();
        paciente.setName(faker.name().firstName());
        paciente.setSurname(faker.name().lastName());
        paciente.setDni(generateUniqueDni());
        paciente.setPhone(faker.phoneNumber().cellPhone());
        paciente.setAddress(faker.address().streetAddress());
        paciente.setLocalty(faker.address().city());
        paciente.setProvince(faker.address().state());
        paciente.setBirth(faker.date().birthday());
        paciente.setEmail(faker.internet().emailAddress());
        return paciente;
    }

    private static int generateUniqueDni() {
        Random random = new Random();
        return random.nextInt(90000000) + 10000000 + random.nextInt(500);
    }

    public static Appointment generateTurnoForDoctor1234(Patient paciente) {
        Doctor medico = findOrCreateDoctorByFile(1234);
        return createAndRegisterAppointment(paciente, medico);
    }

    private static Doctor findOrCreateDoctorByFile(int file) {
        entity.Optional<Doctor> optionalMedico = medicos.findByFile(file);
        int attempts = 0;
        User user = null;
        while (optionalMedico.isEmpty() && attempts < 4) {
        	user = Generator.generateAndSaveRandomUser();
            Doctor medico = generateAndSaveRandomDoctor(user);
            medico.setFile(file);
            medicos.add(medico);
            optionalMedico = medicos.findByFile(file);
            attempts++;
        }

        return optionalMedico.orElseThrow(() -> new RuntimeException("Doctor with file " + file + " could not be created"));
    }


    
    private static Appointment createAndRegisterAppointment(Patient paciente, Doctor medico) throws ConstraintViolationException {
        Appointment turno = new Appointment();
        turno.setAssignedDoctor(medico);
        turno.setPatient(paciente);
        turno.setRemarks("");
        turno.setStatus(AppointmentStatus.PENDING);
        turno.setDate(generateRandomDate());

        turnos.register(turno);

        return turno;
    }


    private static Date generateRandomDate() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 2);

        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();

        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);

        return Date.from(LocalDate.ofEpochDay(randomDay).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static User generateRandomUser() {
        User user = new User();
        user.setName(faker.name().fullName());
        user.setUsername(faker.name().username());
        user.setPassword("12345678");
        user.setActive(true);
        return user;
    }

    public static Doctor generateRecord(User user) {
        Specialty randomSpecialty = getRandomSpecialty();
        Doctor medico = new Doctor();
        medico.setSurname(faker.name().lastName());
        medico.setName(faker.name().firstName());
        medico.setEmail(faker.internet().emailAddress());
        medico.setSpecialty(randomSpecialty);
        medico.setAddress(faker.address().fullAddress());
        medico.setBirth(faker.date().birthday());
        medico.setFile(ThreadLocalRandom.current().nextInt(10000));
        medico.setLocalty(faker.address().city());
        medico.setSex(ThreadLocalRandom.current().nextBoolean() ? "M" : "F");
        medico.setUser(user);
        medico.setPhone(faker.phoneNumber().phoneNumber());
        return medico;
    }

    private static Specialty getRandomSpecialty() {
        Specialty[] specialties = generateRecords();
        return specialties[ThreadLocalRandom.current().nextInt(specialties.length)];
    }

    public static User generateAndSaveRandomUser() {
        User user = generateRandomUser();
        users.signup(user);
        return user;
    }

    public static Doctor generateAndSaveRandomDoctor(User user) {
        Doctor medico = generateRecord(user);
        medico.setSchedules(generateRandomSchedules());
        medicos.add(medico);
        return medico;
    }


    public static Appointment generateTurnoPunto6(Patient paciente, Doctor medico) {
        Appointment turno = new Appointment();
        turno.setAssignedDoctor(medico);
        turno.setPatient(paciente);
        turno.setRemarks("");
        turno.setStatus(AppointmentStatus.PENDING);
        turno.setDate(generateRandomDate());
        
        turnos.register(turno);
        
        return turno;
    }
    public static Patient generateAndSaveRandomPaciente() {
        Patient paciente = generateRandomPaciente();
        pacientes.add(paciente);
        return paciente;
    }

    public static Set<Schedule> generateRandomSchedules() {
        Set<Schedule> schedules = new HashSet<>();
        int numberOfSchedules = ThreadLocalRandom.current().nextInt(1, 6);

        while (schedules.size() < numberOfSchedules) {
            Schedule newSchedule = createRandomSchedule();
            if (isNonOverlapping(newSchedule, schedules)) {
                schedules.add(newSchedule);
            }
        }

        return schedules;
    }

    private static Schedule createRandomSchedule() {
        Day beginDay = Day.values()[ThreadLocalRandom.current().nextInt(Day.values().length)];
        LocalTime startTime = LocalTime.of(ThreadLocalRandom.current().nextInt(24), ThreadLocalRandom.current().nextInt(60));
        int duration = ThreadLocalRandom.current().nextInt(1, 13);
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

        return newSchedule;
    }

    private static boolean isNonOverlapping(Schedule newSchedule, Set<Schedule> schedules) {
        return schedules.stream().noneMatch(existingSchedule -> isOverlapping(newSchedule, existingSchedule));
    }

    private static boolean isOverlapping(Schedule newSchedule, Schedule existingSchedule) {
        if (newSchedule.getBeginDay() == existingSchedule.getBeginDay()) {
            return newSchedule.getEndTime().isAfter(existingSchedule.getStartTime()) &&
                   newSchedule.getStartTime().isBefore(existingSchedule.getEndTime());
        } else if (newSchedule.getFinishDay() == existingSchedule.getBeginDay()) {
            return newSchedule.getEndTime().isAfter(existingSchedule.getStartTime()) &&
                   newSchedule.getEndTime().isBefore(existingSchedule.getEndTime());
        } else if (newSchedule.getBeginDay() == existingSchedule.getFinishDay()) {
            return newSchedule.getStartTime().isBefore(existingSchedule.getEndTime()) &&
                   newSchedule.getStartTime().isAfter(existingSchedule.getStartTime());
        }
        return false;
    }

    public static Specialty[] generateRecords() {
        return new Specialty[]{
            createSpecialty(1, "Cirugía General", "Responsable de una amplia gama de condiciones quirúrgicas, como apendicectomías, hernias, entre otras."),
            createSpecialty(2, "Pediatría", "Tratan a niños desde recién nacidos hasta la adolescencia, abordando problemas de salud infantil."),
            createSpecialty(3, "Ginecología y Obstetricia", "Se especializan en la salud reproductiva de las mujeres, desde el embarazo hasta la menopausia."),
            createSpecialty(4, "Cardiología", "Diagnostican y tratan enfermedades del corazón y los vasos sanguíneos."),
            createSpecialty(5, "Neurología", "Se centran en trastornos del sistema nervioso, como migrañas, epilepsia y enfermedad de Parkinson."),
            createSpecialty(6, "Dermatología", "Tratan enfermedades de la piel, cabello y uñas, como acné, eczema y cáncer de piel."),
            createSpecialty(7, "Psiquiatría", "Se ocupan de los trastornos mentales, como la depresión, la ansiedad y la esquizofrenia."),
            createSpecialty(8, "Oftalmología", "Se dedican a la salud ocular, desde exámenes de la vista hasta cirugía de cataratas y corrección de la visión."),
            createSpecialty(9, "Otorrinolaringología", "Especializados en trastornos del oído, nariz y garganta, como otitis, sinusitis y amigdalitis."),
            createSpecialty(10, "Medicina Interna", "Tratan una amplia gama de enfermedades en adultos, desde diabetes hasta enfermedades cardíacas y pulmonares.")
        };
    }

    private static Specialty createSpecialty(int id, String name, String description) {
        Specialty specialty = new Specialty();
        specialty.setId(id);
        specialty.setName(name);
        specialty.setDescription(description);
        return specialty;
    }

    public static Specialty[] generateAndSaveRecords() {
        Specialty[] specialties = generateRecords();
        Arrays.stream(specialties).forEach(repo::add);
        return specialties;
    }
}
