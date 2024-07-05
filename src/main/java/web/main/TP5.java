package web.main;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import web.entity.Doctor;
import web.entity.User;
import web.logic.IAppointmentLogic;
import web.logic.IDoctorLogic;
import web.logicImpl.AppointmentLogicImpl;
import web.logicImpl.DoctorLogicImpl;

@Deprecated
public class TP5 {
	
	public TP5() {
		
	}

	 // PENDIENTE BEANS
    @SuppressWarnings("unused")
	private void punto1(User requiring) {
    	System.out.println("\n\n-> INICIO PUNTO 1\n\n");
    	DoctorLogicImpl doctorsRepo = new DoctorLogicImpl();
    	List<Doctor> doctors = doctorsRepo.listOrderByFileDescending(1, 10, requiring);
    	for(Doctor m : doctors) {
    		System.out.println(m);
    	}
    }
    
    @SuppressWarnings("unused")
	private void punto2(User requiring) {
    	System.out.println("\n\n-> INICIO PUNTO 2\n\n");
		IDoctorLogic medicos_repo = new DoctorLogicImpl();
    	List<Object[]> lista_medicos_P2 = medicos_repo.listOnlyFileNumbersAndNames(requiring);
    	for(Object[] medico : lista_medicos_P2) {
    		System.out.println("Legajo: " + medico[0] + " Nombre: "+ medico[1] + " Apellido: "+ medico[2]);
    	}
	}
	
    @SuppressWarnings("unused")
	private void punto3(User requiring) {
    	System.out.println("\n\n-> INICIO PUNTO 3\n\n");
		IDoctorLogic medicos_repo = new DoctorLogicImpl();

		LocalDate fecha = LocalDate.of(2024, 12, 31);
		LocalDate fecha2 = LocalDate.of(2025, 1, 2);

		List<Object[]> turnosEnRango = medicos_repo.getAppointmentsByDoctorAndDateRange(1234, fecha, fecha2, requiring);
        for (Object[] turno : turnosEnRango) {
            System.out.println("Legajo: " + turno[0] + ", Fecha de Alta: " + turno[1] + ", Estado: " + turno[2]);
        }
	}
    
    @SuppressWarnings("unused")
	private void punto4(User requiring) {
    	System.out.println("\n\n-> INICIO PUNTO 4\n\n");
    	IDoctorLogic Medicos = new DoctorLogicImpl();
    	List<Integer> lista_medicos_P4 = Medicos.listOnlyFileNumbers(requiring);
    	for(Integer LMedico : lista_medicos_P4) {
    		System.out.println(LMedico);
    	}
    }
    
    @SuppressWarnings("unused")
	private void punto5(User requiring) {
    	System.out.println("\n\n-> INICIO PUNTO 5\n\n");
    	DoctorLogicImpl logic = new DoctorLogicImpl();
    	Doctor m = logic.findDoctorWithHighestFileNumber(requiring);
    	System.out.println(m);
	}
	
	@SuppressWarnings("unused")
	private void punto6(User requiring) {
    	System.out.println("\n\n-> INICIO PUNTO 6\n\n");
		Date d = new Date();
    	d.setDate(1);
    	d.setMonth(0);
    	d.setYear(124);
    	Date d2 = new Date();
    	d2.setDate(1);
    	d2.setMonth(2);
    	d2.setYear(124);
    	IAppointmentLogic turnos = new AppointmentLogicImpl();
    	int presentes = turnos.countPresencesBetween(d, d2, requiring);
    	int ausentes = turnos.countAbsencesBetween(d, d2, requiring);
    	double total = presentes + ausentes;
    	double p_p = Math.round((presentes / total) * 10000.0) / 100.0;
    	double a_p = Math.round((ausentes / total) * 10000.0) / 100.0;
    	
    	System.out.println("Presentes: " + presentes + " (" + p_p + "% del total)\nAusentes: " + ausentes + " (" + a_p + "% del total)\nTotal: " + total);
    	
	}
	
}
