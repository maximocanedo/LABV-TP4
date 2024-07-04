/**
 * @typedef {Object} SaveableEntity
 * @property {number} [_lastOfflineSaved] - Última vez actualizado en la base de datos local.
 */
/**
 * @typedef {Object} IdentifiableUser
 * @property {string} username Nombre de usuario.
 */
/**
 * @typedef {Object} IUserBasicProperties
 * @property {string} name - Nombre.
 * @property {boolean} active - Estado lógico del usuario.
 * @typedef {IdentifiableUser & IUserBasicProperties & SaveableEntity} IUser
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
 * @typedef {Object} Schedule
 * @property {number} [id] - El ID del horario.
 * @property {string} beginDay - Día de inicio.
 * @property {string} finishDay - Día de finalización.
 * @property {[number, number]} startTime - Hora de inicio [hora, minuto].
 * @property {[number, number]} endTime - Hora de finalización [hora, minuto].
 * @property {boolean} [active] - Estado del horario.
 */
/**
 * @typedef {Object} Identifiable
 * @property {number} id Identificador único
 */
/**
 * @typedef {Object} SpecialtyProps
 * @property {string} name - Nombre de la especialidad.
 * @property {boolean} active - Estado de la especialidad.
 * @property {string} description - Descripción de la especialidad.
 * @typedef {SpecialtyProps & Identifiable} Specialty
 */
/**
 * @typedef {Object} IDoctorBasicProperties
 * @property {number} id - El ID del doctor.
 * @property {number} file - Número de legajo del doctor.
 * @property {string} name - El nombre del doctor.
 * @property {string} surname - El apellido del doctor.
 * @property {boolean} active - Estado del doctor.
 * @property {Specialty} specialty - Especialidad del doctor.
 * @property {Schedule[]} schedules - Lista de horarios del doctor.
 * @typedef {IDoctorBasicProperties & SaveableEntity} IDoctor
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
