package web.entity;

public enum Permit {
	// Especialidades,
	CREATE_SPECIALTY,
	UPDATE_SPECIALTY,
	READ_DISABLED_SPECIALTY_RECORDS,
	DISABLE_SPECIALTY,
	ENABLE_SPECIALTY,
	READ_SPECIALTY,
	SEARCH_SPECIALTY,
	
	// Paciente
	// TODO: Funcionalidad y permisos para leer registros eliminados lógicamente.
	CREATE_PATIENT,
	READ_PATIENT_PERSONAL_DATA, // Para listados y búsqueda específica.
	READ_PATIENT_APPOINTMENTS, // TODO: Crear funcionalidad de listado de turnos por paciente.
	UPDATE_PATIENT,
	DISABLE_PATIENT,
	ENABLE_PATIENT,
	// Doctor
	CREATE_DOCTOR,
	// Leer datos personales del doctor. No se necesitan permisos para leer un doctor.
	READ_DOCTOR,
	READ_DOCTOR_APPOINTMENTS,
	UPDATE_DOCTOR_PERSONAL_DATA,
	UPDATE_DOCTOR_SCHEDULES, // Reconsiderar
	DISABLE_DOCTOR,
	ENABLE_DOCTOR,
	// Appointment
	CREATE_APPOINTMENT,
	READ_APPOINTMENT, // Puede aparecer información del paciente o médico relacionados.
	UPDATE_APPOINTMENT,
	DISABLE_APPOINTMENT,
	ENABLE_APPOINTMENT,
	// UserPermit
	GRANT_PERMISSIONS,
	// User
	ASSIGN_DOCTOR, // Reconsiderar
	RESET_PASSWORD,
	UPDATE_USER_DATA,
	DELETE_OR_ENABLE_USER,
	READ_USER_DATA,
	// Ticket
	READ_USER_SESSIONS,
	CLOSE_USER_SESSIONS, UPDATE_TICKET, CREATE_TICKET, ENABLE_TICKET
	
}
