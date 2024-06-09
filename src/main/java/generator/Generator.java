package generator;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.Specialty;
import entity.User;

public class Generator {

    public Generator() {}
    
    @Deprecated
    public Patient generateRandomPaciente() {
        PatientGenerator pg = new PatientGenerator();
        return pg.generate();
    }
    
    @Deprecated
    public Appointment generateTurnoForDoctor1234(Patient paciente) {
    	AppointmentGenerator ag = new AppointmentGenerator();
    	return ag.generateForDoctor1234(paciente);
    }
    
    @Deprecated
    public User generateRandomUser() {
        UserGenerator ug = new UserGenerator();
        return ug.generate();
    }
    
    @Deprecated
    public Doctor generateRecord(User user) {
        DoctorGenerator dg = new DoctorGenerator();
        return dg.generate(user);
    }
    
    @Deprecated
    public User generateAndSaveRecord() {
        UserGenerator ug = new UserGenerator();
        return ug.save();
    }
    
    @Deprecated
    public Patient generateAndSaveRandomPaciente() {
        PatientGenerator pg = new PatientGenerator();
        return pg.save();
    }
    
    @Deprecated
    public static boolean EXISTE_LEGAJO_1234 = false;
    
    @Deprecated
    public Doctor generateAndSaveRandomDoctor(User user) {
    	DoctorGenerator dg = new DoctorGenerator();
    	return dg.save(user);
    }
    
    @Deprecated
    public User generateAndSaveRandomUser() {
        UserGenerator ug = new UserGenerator();
        return ug.save();
    }
    
    @Deprecated
    public Appointment generateTurno(Patient p, Doctor m) {
    	AppointmentGenerator ag = new AppointmentGenerator();
    	return ag.save(p, m);
    }
    
    @Deprecated
	public Appointment generateTurnoPunto6(Patient p, Doctor m) {
    	AppointmentGenerator ag = new AppointmentGenerator();
    	return ag.saveforP6(p, m);
    }
    
    @Deprecated
    public Specialty[] generateAndSaveRecords() {
    	SpecialtyGenerator sg = new SpecialtyGenerator();
    	return sg.save();
    }
    
    @Deprecated
    public Specialty[] generateRecords() {
    	SpecialtyGenerator sg = new SpecialtyGenerator();
    	return sg.generate();
    }

}
