package web.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.entity.Doctor;
import web.entity.Patient;
import web.entity.User;
import web.logicImpl.DoctorLogicImpl;
import web.logicImpl.PatientLogicImpl;
import web.logicImpl.UserLogicImpl;

@Deprecated
@Component
public class TP4 {
	
	@Autowired
    private UserLogicImpl users;
	
	@Autowired
	private DoctorLogicImpl doctors;
	
	@Autowired
	private PatientLogicImpl patients;
    
    public TP4() {}
    
	/**
	 * Método main usado en el TP4.
	 */
	@SuppressWarnings("unused")
	private void main() {
    	User user = users.list(1, 1).get(0);
    	System.out.println(user);
    	user.setName("Roberto Castañeda");
    	try {
    		users.update(user);
    		System.out.println("Se cambió correctamente el nombre. ");
    		System.out.println(user);
        	// Borrar un registro
        	users.disable(user);
        	System.out.println("Se deshabilitó correctamente el usuario. ");
    	} catch (Exception e) {
    		System.out.println("Error al intentar cambiar el nombre. ");
    	}
    	
    	// Listar médicos
    	List<Doctor> medicos = doctors.list(1, 10);
    	for(Doctor medico : medicos) {
    		System.out.println(medico);
    	}
    	
    	List<Patient> pacientes = patients.list(1, 10);
    	for(Patient paciente : pacientes) {
    		System.out.println(paciente);
    	}
	}
	
}
