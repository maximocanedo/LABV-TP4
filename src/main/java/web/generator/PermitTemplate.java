package web.generator;

import java.util.HashSet;

import web.entity.Permit;

public class PermitTemplate {
	
	/**
	 * *Usuario Administrador*
	 * Administrador del sistema. Tiene acceso a todas las funcionalidades sin ningún tipo de restricción. 
	 */
	public static PermitTemplate ROOT = new PermitTemplate(new Permit[] {
			Permit.CREATE_SPECIALTY, Permit.UPDATE_SPECIALTY, Permit.READ_DISABLED_SPECIALTY_RECORDS, Permit.DISABLE_SPECIALTY, Permit.ENABLE_SPECIALTY,
			Permit.CREATE_PATIENT, Permit.READ_PATIENT_PERSONAL_DATA, Permit.READ_PATIENT_APPOINTMENTS, Permit.UPDATE_PATIENT, Permit.DISABLE_PATIENT, Permit.ENABLE_PATIENT,
			Permit.CREATE_DOCTOR, Permit.READ_DOCTOR, Permit.READ_DOCTOR_APPOINTMENTS, Permit.UPDATE_DOCTOR_PERSONAL_DATA, Permit.UPDATE_DOCTOR_SCHEDULES, Permit.DISABLE_DOCTOR, Permit.ENABLE_DOCTOR,
			Permit.CREATE_APPOINTMENT, Permit.READ_APPOINTMENT, Permit.UPDATE_APPOINTMENT, Permit.DISABLE_APPOINTMENT, Permit.ENABLE_APPOINTMENT,
			Permit.GRANT_PERMISSIONS,
			Permit.ASSIGN_DOCTOR, Permit.RESET_PASSWORD, Permit.UPDATE_USER_DATA, Permit.DELETE_OR_ENABLE_USER, Permit.READ_USER_DATA,
			Permit.READ_USER_SESSIONS, Permit.CLOSE_USER_SESSIONS	
	});
	
	/**
	 * *Usuario por defecto*
	 * No tiene ningún permiso especial.
	 */
	public static PermitTemplate DEFAULT = new PermitTemplate(new Permit[] {
	});
	
	/**
	 * *Administrador de pacientes*
	 * Puede administrar los datos de los pacientes únicamente.
	 */
	public static PermitTemplate PATIENT_MANAGER = new PermitTemplate(new Permit[] {
			Permit.CREATE_PATIENT, Permit.READ_PATIENT_PERSONAL_DATA, Permit.READ_PATIENT_APPOINTMENTS, Permit.UPDATE_PATIENT, Permit.DISABLE_PATIENT, Permit.ENABLE_PATIENT,
			Permit.READ_DOCTOR, Permit.READ_DOCTOR_APPOINTMENTS, 
			Permit.READ_APPOINTMENT,
	});
	
	/**
	 * *Administrador de médicos*
	 * Puede administrar la tabla de médicos. 
	 */
	public static PermitTemplate DOCTOR_MANAGER = new PermitTemplate(new Permit[] {
			Permit.CREATE_DOCTOR, Permit.READ_DOCTOR, Permit.READ_DOCTOR_APPOINTMENTS, Permit.UPDATE_DOCTOR_PERSONAL_DATA, Permit.UPDATE_DOCTOR_SCHEDULES, Permit.DISABLE_DOCTOR, Permit.ENABLE_DOCTOR,
			Permit.CREATE_APPOINTMENT, Permit.READ_APPOINTMENT, Permit.UPDATE_APPOINTMENT, Permit.DISABLE_APPOINTMENT, Permit.ENABLE_APPOINTMENT,
			Permit.GRANT_PERMISSIONS,
			Permit.ASSIGN_DOCTOR
	});
	
	/** 
	 * *Administrador de datos extra*
	 * Puede crear datos que no están relacionados directamente a los doctores, pacientes, usuarios o turnos. 
	 * Ejemplo: Especialidades, Localidades, Provincias, Códigos Postales.
	 */
	public static PermitTemplate EXTRA_MANAGER = new PermitTemplate(new Permit[] {
			Permit.CREATE_SPECIALTY, Permit.UPDATE_SPECIALTY, Permit.READ_DISABLED_SPECIALTY_RECORDS, Permit.DISABLE_SPECIALTY, Permit.ENABLE_SPECIALTY	
	});

	/**
	 * *Administrador de usuarios*
	 * Administrador de usuarios. Tiene acceso a todas las funcionalidades relacionadas con los usuarios. 
	 */
	public static PermitTemplate USER_OFFICE = new PermitTemplate(new Permit[] {
			Permit.ASSIGN_DOCTOR, Permit.RESET_PASSWORD, Permit.UPDATE_USER_DATA, Permit.DELETE_OR_ENABLE_USER, Permit.READ_USER_DATA,
			Permit.READ_USER_SESSIONS, Permit.CLOSE_USER_SESSIONS	
	});
	
	/**
	 * *Moderador de seguridad*
	 * Se le conceden funciones de seguridad.
	 */
	public static PermitTemplate SECURITY_OFFICE = new PermitTemplate(new Permit[] {
			Permit.GRANT_PERMISSIONS,
			Permit.RESET_PASSWORD, Permit.DELETE_OR_ENABLE_USER, Permit.READ_USER_DATA,
			Permit.READ_USER_SESSIONS, Permit.CLOSE_USER_SESSIONS	
	});
	
	private HashSet<Permit> permits;
	
	public PermitTemplate(HashSet<Permit> permits) {
		this.permits = permits;
	}
	
	public PermitTemplate(Permit[] permits) {
		for(Permit p : permits) {
			this.permits.add(p);
		}
	}
	
	public PermitTemplate() {
		this(new Permit[] {});
	}
	
	public HashSet<Permit> getPermits() {
		return permits;
	}

}
