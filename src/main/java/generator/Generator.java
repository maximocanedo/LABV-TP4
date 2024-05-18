package generator;

import java.util.Random;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import entity.*;
import logic.*;
import logicImpl.*;

public class Generator {

    private static IUserLogic users = new UserLogicImpl();
    private static IPacienteLogic pacientes = new PacienteLogicImpl();
    private static IMedicoLogic medicos = new MedicoLogicImpl();
    
    public static Paciente generateRandomPaciente() {
        Faker faker = new Faker();
        Paciente paciente = new Paciente();
        paciente.setNombre(faker.name().firstName());
        paciente.setApellido(faker.name().lastName());
        paciente.setDni(45489657);
        paciente.setTelefono(faker.phoneNumber().cellPhone());
        paciente.setDireccion(faker.address().streetAddress());
        paciente.setLocalidad(faker.address().city());
        paciente.setProvincia(faker.address().state());
        paciente.setFechaNacimiento(faker.date().birthday());
        paciente.setCorreo(faker.internet().emailAddress());
        return paciente;
    }
    
    public static User generateRandomUser() {
        Faker faker = new Faker();
        User user = new User();
        user.setName(faker.name().fullName());
        user.setUsername(faker.name().username());
        user.setPassword("12345678"); // Todos los usuarios generados automáticamente usarán esta contraseña.
        user.setActiveState(true);
        return user;
    }
    
    public static Medico generateRecord(User user) {
        Faker faker = new Faker();
        Especialidad[] especialidades = Generator.generateRecords();
        Random r = new Random();
        Especialidad random = especialidades[r.nextInt(especialidades.length)];
        Medico medico = new Medico();
        Name nn = faker.name();
        Address aa = faker.address();
        medico.setApellido(nn.lastName());
        medico.setNombre(nn.firstName());
        medico.setCorreo(faker.internet().emailAddress());
        medico.setEspecialidad(random);
        medico.setDireccion(aa.fullAddress());
        medico.setFechaNacimiento(faker.date().birthday());
        medico.setLegajo(r.nextInt(10000));
        medico.setLocalidad(faker.address().city());
        medico.setSexo(r.nextInt(10) % 2 == 0 ? "M" : "F");
        medico.setUser(user);
        medico.setTelefono(faker.phoneNumber().phoneNumber());
        return medico;
    }
    
    public static User generateAndSaveRecord() {
        User random = generateRandomUser();
        users.signup(random);
        return random;
    }
    
    public static Paciente generateAndSaveRandomPaciente() {
        Paciente random = generateRandomPaciente();
        pacientes.signupPaciente(random);
        return random;
    }
    
    public static Medico generateAndSaveRandomDoctor(User user) {
        Medico medico = generateRecord(user);
        medicos.signupMedico(medico);
        return medico;
    }
    
    public static Especialidad[] generateAndSaveRecords() {
        IEspecialidadLogic repo = new EspecialidadLogicImpl();
        Especialidad[] especialidades = Generator.generateRecords();
        for (Especialidad especialidad : especialidades) {
            repo.add(especialidad);
        }
        return especialidades;
    }
    
    public static Especialidad[] generateRecords() {
        Especialidad cirugiaGeneral = new Especialidad();
        cirugiaGeneral.setId(1);
        cirugiaGeneral.setNombre("Cirugía General");
        cirugiaGeneral.setDescripcion("Responsable de una amplia gama de condiciones quirúrgicas, como apendicectomías, hernias, entre otras.");

        Especialidad pediatria = new Especialidad();
        pediatria.setId(2);
        pediatria.setNombre("Pediatría");
        pediatria.setDescripcion("Tratan a niños desde recién nacidos hasta la adolescencia, abordando problemas de salud infantil.");

        Especialidad ginecologiaObstetricia = new Especialidad();
        ginecologiaObstetricia.setId(3);
        ginecologiaObstetricia.setNombre("Ginecología y Obstetricia");
        ginecologiaObstetricia.setDescripcion("Se especializan en la salud reproductiva de las mujeres, desde el embarazo hasta la menopausia.");

        Especialidad cardiologia = new Especialidad();
        cardiologia.setId(4);
        cardiologia.setNombre("Cardiología");
        cardiologia.setDescripcion("Diagnostican y tratan enfermedades del corazón y los vasos sanguíneos.");

        Especialidad neurologia = new Especialidad();
        neurologia.setId(5);
        neurologia.setNombre("Neurología");
        neurologia.setDescripcion("Se centran en trastornos del sistema nervioso, como migrañas, epilepsia y enfermedad de Parkinson.");

        Especialidad dermatologia = new Especialidad();
        dermatologia.setId(6);
        dermatologia.setNombre("Dermatología");
        dermatologia.setDescripcion("Tratan enfermedades de la piel, cabello y uñas, como acné, eczema y cáncer de piel.");

        Especialidad psiquiatria = new Especialidad();
        psiquiatria.setId(7);
        psiquiatria.setNombre("Psiquiatría");
        psiquiatria.setDescripcion("Se ocupan de los trastornos mentales, como la depresión, la ansiedad y la esquizofrenia.");

        Especialidad oftalmologia = new Especialidad();
        oftalmologia.setId(8);
        oftalmologia.setNombre("Oftalmología");
        oftalmologia.setDescripcion("Se dedican a la salud ocular, desde exámenes de la vista hasta cirugía de cataratas y corrección de la visión.");

        Especialidad otorrinolaringologia = new Especialidad();
        otorrinolaringologia.setId(9);
        otorrinolaringologia.setNombre("Otorrinolaringología");
        otorrinolaringologia.setDescripcion("Especializados en trastornos del oído, nariz y garganta, como otitis, sinusitis y amigdalitis.");

        Especialidad medicinaInterna = new Especialidad();
        medicinaInterna.setId(10);
        medicinaInterna.setNombre("Medicina Interna");
        medicinaInterna.setDescripcion("Tratan una amplia gama de enfermedades en adultos, desde diabetes hasta enfermedades cardíacas y pulmonares.");
        
        return new Especialidad[] {
            cirugiaGeneral, pediatria, ginecologiaObstetricia, cardiologia, 
            neurologia, dermatologia, psiquiatria, oftalmologia, otorrinolaringologia,
            medicinaInterna
        };
    }

    public static User generateAndSaveRandomUser() {
        User user = generateRandomUser();
        users.signup(user);
        return user;
    }
}
