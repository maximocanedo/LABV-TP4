/**
 * @typedef {Object} SaveableEntity
 * @property {number} [_lastOfflineSaved] - Última vez actualizado en la base de datos local.
 */
/**
 * @typedef {Object} Deletable
 * @property {boolean} active Estado lógico del registro.
 */
/**
 * @typedef {Object} IdentifiableUser
 * @property {string} username Nombre de usuario.
 */
/**
 * @typedef {Object} IUserBasicProperties
 * @property {string} name - Nombre.
 * @property {IDoctor} [doctor] - Doctor asignado.
 * @property {import('./models.js').Permit[]} access Permisos concedidos.
 * @typedef {IdentifiableUser & IUserBasicProperties & SaveableEntity & Deletable} IUser
 */
/**
 * @typedef {Object} IUserActionProperty
 * @property {import('./models.js').Permit[]} access Permisos concedidos.
 * @typedef {IUserActionProperty & IUser} IUserWithActions
 */
/**
 * @typedef {IUser} User
 * @property {Doctor | null} doctor - Doctor asociado al usuario.
 */
/**
 * @typedef {IUser} UserMinimalView
 * @property {DoctorMinimalView | null} doctor - Doctor asociado al usuario.
 */
/**
 * @typedef {Object} ScheduleBasicProperties
 * @property {number} [id] - El ID del horario.
 * @property {string} beginDay - Día de inicio.
 * @property {string} finishDay - Día de finalización.
 * @property {string} startTime - Hora de inicio [hora, minuto].
 * @property {string} endTime - Hora de finalización [hora, minuto].
 * @typedef {ScheduleBasicProperties & Deletable} Schedule
 */
/**
 * @typedef {Object} Identifiable
 * @property {number} id Identificador único
 */
/**
 * @typedef {Object} SpecialtyProps
 * @property {string} name - Nombre de la especialidad.
 * @property {string} description - Descripción de la especialidad.
 * @typedef {SpecialtyProps & Identifiable & Deletable} Specialty
 */
/**
 * @typedef {Object} IdentifiableDoctorProps
 * @property {number} file Número de legajo del doctor. 
 * @typedef {IdentifiableDoctorProps & Identifiable} IdentifiableDoctor
 */
/**
 * @typedef {Object} IDoctorBasicProperties
 * @property {string} name - El nombre del doctor.
 * @property {string} surname - El apellido del doctor.
 * @property {Specialty} specialty - Especialidad del doctor.
 * @property {Schedule[]} schedules - Lista de horarios del doctor.
 * @typedef {IdentifiableDoctor & IDoctorBasicProperties & SaveableEntity & Deletable} IDoctor
 */
/**
 * @typedef {IDoctor} DoctorMinimalView
 */
/**
 * @typedef {'M' | 'F'} Sex Sexo
 */
/**
 * @typedef {Object} DoctorAdditionalProperties
 * @property {Sex} sex - El sexo del doctor.
 * @property {Date | string} birth - Fecha de nacimiento del doctor.
 * @property {string} address - Dirección del doctor.
 * @property {string} localty - Localidad del doctor.
 * @property {string} email - Correo electrónico del doctor.
 * @property {string} phone - Número de teléfono del doctor.
 * @typedef {IDoctor & DoctorAdditionalProperties} Doctor
 */
/**
 * @typedef {Object} DoctorUpdateRequest
 * @property {string} [name] Nombre
 * @property {string} [surname] Apellido
 * @property {Sex} [sex] Sexo
 * @property {Date | string} [birth] Fecha de nacimiento
 * @property {String} [address] Dirección
 * @property {String} [localty] Localidad
 * @property {String} [email] Dirección de correo electrónico
 * @property {String} [phone] Número de teléfono
 * @property {Identifiable} [specialty] Especialidad
 * @property {IdentifiableUser} [user] Usuario asignado
 */
/**
 * @typedef {Object} DoctorCreateRequestAdditionalProperties
 * @property {number} file Legajo.
 * @property {Schedule[]} schedules Horarios.
 * @typedef {DoctorCreateRequestAdditionalProperties & DoctorUpdateRequest} DoctorRegistrationRequest
 */
/**
 * @typedef {Object} CommonException
 * @property {string} message Mensaje del error.
 * @property {string} description Descripción detallada del error.
 * @property {string} path Código client-friendly del error. 
 */
/**
 * @typedef {Object} SignUpRequest
 * @property {string} username Nombre de usuario.
 * @property {string} name Nombre.
 * @property {string} password Contraseña.
 * @property {IDoctor | null} doctor Doctor asociado.
 */
/**
 * @typedef {Object} UserPermit
 * @property {import('./models.js').Permit} action Acción concreta.
 * @property {IUser} [user] Usuario dueño del permiso.
 * @property {boolean} allowed Concedida o denegada.
 */

// PACIENTES
/**
 * @typedef {Object} IdentifiablePatientProps
 * @property {string} dni D.N.I. del paciente.
 * @typedef {IdentifiablePatientProps & Identifiable} IdentifiablePatient
 */
/**
 * @typedef {Object} PatientBasicProperties
 * @property {string} name Nombre del paciente.
 * @property {string} surname Apellido del paciente.
 * @typedef {IdentifiablePatient & SaveableEntity & PatientBasicProperties & Deletable} IPatient
 */
/**
 * @typedef {PatientBasicProperties} PatientMinimalView
 */
/**
 * @typedef {Object} PatientCommunicationFields
 * @property {string} phone Número de teléfono del paciente.
 * @property {string} email Dirección de correo electrónico del paciente.
 * @typedef {IPatient & PatientCommunicationFields} PatientCommunicationView
 */
/**
 * @typedef {Object} PatientPrivateInformationFields
 * @property {string} address Dirección.
 * @property {string} localty Localidad.
 * @property {string} province Provincia.
 * @property {Date | string} birth Fecha de nacimiento.
 * @typedef {IPatient & PatientCommunicationFields & PatientPrivateInformationFields} Patient
 */
/**
 * @typedef {PatientBasicProperties & PatientCommunicationFields & PatientPrivateInformationFields} PatientSignRequest
 */
/**
 * @typedef {PatientBasicProperties & PatientCommunicationFields & PatientPrivateInformationFields} PatientUpdateRequest
 */
// Appointments
/**
 * @typedef {"PENDING" | "ABSENT" | "PRESENT"} AppointmentStatus
 */
/**
 * @typedef {Object} AppointmentRegistrationRequest
 * @property {Date | string} date Fecha y hora del turno.
 * @property {IDoctor} doctor Médico asignado.
 * @property {IPatient} patient Paciente.
 */
/**
 * @typedef {Object} AppointmentBasicProperties
 * @property {string} remarks Observaciones.
 * @property {AppointmentStatus} status Estado del turno.
 * @typedef {AppointmentRegistrationRequest & AppointmentBasicProperties & Deletable & Identifiable & SaveableEntity} IAppointment
 */
/**
 * @typedef {Object} AppointmentMinimalViewProps
 * @property {DoctorMinimalView} doctor Médico asignado
 * @property {PatientMinimalView} patient Paciente.
 * @typedef {IAppointment & AppointmentMinimalViewProps} AppointmentMinimalView
 */
/**
 * @typedef {Object} AppointmentCommunicationViewProps
 * @property {DoctorMinimalView} doctor Médico asignado
 * @property {PatientCommunicationView} patient Paciente.
 * @typedef {IAppointment & AppointmentCommunicationViewProps} AppointmentCommunicationView
 */
/**
 * @typedef {Object} AppointmentProps
 * @property {Doctor} doctor Médico asignado.
 * @property {Patient} paciente Paciente.
 * @typedef {IAppointment & AppointmentProps} Appointment
 */