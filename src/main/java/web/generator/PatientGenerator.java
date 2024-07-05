package web.generator;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import web.entity.Patient;
import web.entity.User;
import web.logicImpl.PatientLogicImpl;

@Component
public class PatientGenerator implements IEntityGenerator<Patient> {

	@Autowired
    private PatientLogicImpl patients;
	
    @Autowired
    private Faker faker;
    
    @Autowired
    private Random random;
    
	public PatientGenerator() {}

	@Override
	public Patient generate() {
        Patient paciente = new Patient();
        paciente.setName(faker.name().firstName());
        paciente.setSurname(faker.name().lastName());
        int dni = random.nextInt(90000000) + 10000000 + random.nextInt(500);
        paciente.setDni(dni + "");
        paciente.setPhone(faker.phoneNumber().cellPhone());
        paciente.setAddress(faker.address().streetAddress());
        paciente.setLocalty(faker.address().city());
        paciente.setProvince(faker.address().state());
        paciente.setBirth(faker.date().birthday());
        paciente.setEmail(faker.internet().emailAddress());
        return paciente;
	}

	@Override
	public Patient save(User requiring) {
		Patient p = generate();
		patients.add(p, requiring);
		return p;
	}

}
