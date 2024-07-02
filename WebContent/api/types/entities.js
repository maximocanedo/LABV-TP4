/**
 * @typedef {Object} SaveableEntity
 * @property {number} [_lastOfflineSaved] - Última vez actualizado en la base de datos local.
 */
/**
 * @typedef {Object} IUserBasicProperties
 * @property {string} username - Nombre de usuario.
 * @property {string} name - Nombre.
 * @property {boolean} active - Estado lógico del usuario.
 * @typedef {IUserBasicProperties & SaveableEntity} IUser
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
 * @property {number} id - El ID del horario.
 * @property {string} beginDay - Día de inicio.
 * @property {string} finishDay - Día de finalización.
 * @property {[number, number]} startTime - Hora de inicio [hora, minuto].
 * @property {[number, number]} endTime - Hora de finalización [hora, minuto].
 * @property {boolean} active - Estado del horario.
 */
/**
 * @typedef {Object} Specialty
 * @property {number} id - El ID de la especialidad.
 * @property {string} name - Nombre de la especialidad.
 * @property {boolean} active - Estado de la especialidad.
 * @property {string} description - Descripción de la especialidad.
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
 * @typedef {Object} DoctorAdditionalProperties
 * @property {'M' | 'F'} sex - El sexo del doctor.
 * @property {Date | string} birth - Fecha de nacimiento del doctor.
 * @property {string} address - Dirección del doctor.
 * @property {string} localty - Localidad del doctor.
 * @property {string} email - Correo electrónico del doctor.
 * @property {string} phone - Número de teléfono del doctor.
 * @typedef {IDoctor & DoctorAdditionalProperties} Doctor
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
