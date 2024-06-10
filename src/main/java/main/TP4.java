package main;

import java.util.List;

import entity.Doctor;
import entity.Patient;
import entity.User;
import logic.IDoctorLogic;
import logicImpl.DoctorLogicImpl;
import logicImpl.PatientLogicImpl;
import logicImpl.UserLogicImpl;

@Deprecated
public class TP4 {
	
    private UserLogicImpl users;
    
    public TP4() {
		users = new UserLogicImpl();
	}
    
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
    	IDoctorLogic medicos_repo = new DoctorLogicImpl();
    	List<Doctor> medicos = medicos_repo.list(1, 10);
    	for(Doctor medico : medicos) {
    		System.out.println(medico);
    	}
    	
    	PatientLogicImpl paciente_repo = new PatientLogicImpl();
    	List<Patient> pacientes = paciente_repo.list(1, 10);
    	for(Patient paciente : pacientes) {
    		System.out.println(paciente);
    	}
	}
	
}
