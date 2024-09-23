package web.generator;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import web.entity.Patient;
import web.entity.User;
import web.logicImpl.PatientLogicImpl;

@Component("patientGenerator")
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
        Name fker = faker.name();
        Address fa = faker.address();
        paciente.setName(fker.firstName());
        paciente.setSurname(fker.lastName());
        int dni = random.nextInt(90000000) + 10000000 + random.nextInt(500);
        String pn = "+54 11 " + (1000 + random.nextInt(3999)) + " " + (1000 + random.nextInt(8999));
        paciente.setDni(dni + "");
        paciente.setPhone(pn);
        paciente.setAddress(fa.streetAddress());
        paciente.setLocalty(fa.city());
        paciente.setProvince(fa.state());
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
