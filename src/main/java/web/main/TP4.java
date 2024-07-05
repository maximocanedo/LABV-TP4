package web.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.entity.User;
import web.entity.input.DoctorQuery;
import web.entity.input.PatientQuery;
import web.entity.view.DoctorMinimalView;
import web.entity.view.PatientCommunicationView;
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
	private void main(User requiring) {
    	User user = users.getByUsername("abe.bogan", requiring);
    	System.out.println(user);
    	user.setName("Roberto Castañeda");
    	try {
    		users.update(user, requiring);
    		System.out.println("Se cambió correctamente el nombre. ");
    		System.out.println(user);
        	// Borrar un registro
        	users.disable(user, requiring);
        	System.out.println("Se deshabilitó correctamente el usuario. ");
        	users.enable(user, requiring);
    	} catch (Exception e) {
    		System.out.println("Error al intentar cambiar el nombre. ");
    	}
    	
    	// Listar médicos
    	List<DoctorMinimalView> medicos = doctors.search(new DoctorQuery("").paginate(1, 10), requiring);
    	for(DoctorMinimalView medico : medicos) {
    		System.out.println(medico);
    	}
    	
    	List<PatientCommunicationView> pacientes = patients.search(new PatientQuery("").paginate(1, 10), requiring);
    	for(PatientCommunicationView paciente : pacientes) {
    		System.out.println(paciente);
    	}
	}
	
}
