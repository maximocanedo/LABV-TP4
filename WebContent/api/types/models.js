'use strict';

/**
 * @enum string
 */
export const Permit = Object.freeze({
    // Especialidades
    CREATE_SPECIALTY: 'CREATE_SPECIALTY',
    UPDATE_SPECIALTY: 'UPDATE_SPECIALTY',
    READ_DISABLED_SPECIALTY_RECORDS: 'READ_DISABLED_SPECIALTY_RECORDS',
    DISABLE_SPECIALTY: 'DISABLE_SPECIALTY',
    ENABLE_SPECIALTY: 'ENABLE_SPECIALTY',
    READ_SPECIALTY: 'READ_SPECIALTY',
    SEARCH_SPECIALTY: 'SEARCH_SPECIALTY',

    // Paciente
    CREATE_PATIENT: 'CREATE_PATIENT',
    READ_PATIENT_PERSONAL_DATA: 'READ_PATIENT_PERSONAL_DATA',
    READ_PATIENT_APPOINTMENTS: 'READ_PATIENT_APPOINTMENTS',
    UPDATE_PATIENT: 'UPDATE_PATIENT',
    DISABLE_PATIENT: 'DISABLE_PATIENT',
    ENABLE_PATIENT: 'ENABLE_PATIENT',

    // Doctor
    CREATE_DOCTOR: 'CREATE_DOCTOR',
    READ_DOCTOR: 'READ_DOCTOR',
    READ_DOCTOR_APPOINTMENTS: 'READ_DOCTOR_APPOINTMENTS',
    UPDATE_DOCTOR_PERSONAL_DATA: 'UPDATE_DOCTOR_PERSONAL_DATA',
    UPDATE_DOCTOR_SCHEDULES: 'UPDATE_DOCTOR_SCHEDULES',
    DISABLE_DOCTOR: 'DISABLE_DOCTOR',
    ENABLE_DOCTOR: 'ENABLE_DOCTOR',

    // Appointment
    CREATE_APPOINTMENT: 'CREATE_APPOINTMENT',
    READ_APPOINTMENT: 'READ_APPOINTMENT',
    UPDATE_APPOINTMENT: 'UPDATE_APPOINTMENT',
    DISABLE_APPOINTMENT: 'DISABLE_APPOINTMENT',
    ENABLE_APPOINTMENT: 'ENABLE_APPOINTMENT',

    // UserPermit
    GRANT_PERMISSIONS: 'GRANT_PERMISSIONS',

    // User
    ASSIGN_DOCTOR: 'ASSIGN_DOCTOR',
    RESET_PASSWORD: 'RESET_PASSWORD',
    UPDATE_USER_DATA: 'UPDATE_USER_DATA',
    DELETE_OR_ENABLE_USER: 'DELETE_OR_ENABLE_USER',
    READ_USER_DATA: 'READ_USER_DATA',

    // Ticket
    READ_USER_SESSIONS: 'READ_USER_SESSIONS',
    CLOSE_USER_SESSIONS: 'CLOSE_USER_SESSIONS'
});

/**
 * @enum string
 */
export const PermitTemplate = Object.freeze({
    ROOT: "ROOT",
    DEFAULT: "DEFAULT",
    PATIENT_MANAGER: "PATIENT_MANAGER",
    DOCTOR_MANAGER: "DOCTOR_MANAGER",
    EXTRA_MANAGER: "EXTRA_MANAGER",
    USER_OFFFICE: "USER_OFFICE",
    SECURITY_OFFICE: "SECURITY_OFFICE"
});
