'use strict';
import * as appointments from "./../../../actions/appointments.js";
import { DoctorSelector } from "./../../.././lib/selectors/DoctorSelector.js";
import { PatientSelector } from "./../../.././lib/selectors/PatientSelector.js";

const doctorSelector = new DoctorSelector();
const patientSelector = new PatientSelector();

document.querySelector(".selectorDoctor").prepend(doctorSelector.getTrigger());
document.querySelector(".selectorPaciente").prepend(patientSelector.getTrigger());
