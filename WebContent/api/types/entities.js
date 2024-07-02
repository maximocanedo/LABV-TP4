/**
 * @typedef {Object} IUser
 * @property {string} username - Nombre de usuario.
 * @property {string} name - Nombre.
 * @property {boolean} active - Estado lógico del usuario.
 * @property {number} [_lastOfflineSaved] - Última vez actualizado en la base de datos local.
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
 * @property {number[]} startTime - Hora de inicio [hora, minuto].
 * @property {number[]} endTime - Hora de finalización [hora, minuto].
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
 * @typedef {Object} DoctorMinimalView
 * @property {number} id - El ID del doctor.
 * @property {number} file - Número de archivo del doctor.
 * @property {string} name - El nombre del doctor.
 * @property {string} surname - El apellido del doctor.
 * @property {boolean} active - Estado del doctor.
 * @property {Specialty} specialty - Especialidad del doctor.
 * @property {Schedule[]} schedules - Lista de horarios del doctor.
 */
/**
 * @typedef {Object} Doctor
 * @property {number} id - El ID del doctor.
 * @property {number} file - Número de archivo del doctor.
 * @property {string} name - El nombre del doctor.
 * @property {string} surname - El apellido del doctor.
 * @property {'M' | 'F'} sex - El sexo del doctor.
 * @property {Date | string} birth - Fecha de nacimiento del doctor.
 * @property {string} address - Dirección del doctor.
 * @property {string} localty - Localidad del doctor.
 * @property {string} email - Correo electrónico del doctor.
 * @property {string} phone - Número de teléfono del doctor.
 * @property {boolean} active - Estado del doctor.
 * @property {Specialty} specialty - Especialidad del doctor.
 * @property {Schedule[]} schedules - Lista de horarios del doctor.
 */
/**
 * @typedef {Object} CommonException
 * @property {string} message Mensaje del error.
 * @property {string} description Descripción detallada del error.
 * @property {string} path Código client-friendly del error. 
 */




