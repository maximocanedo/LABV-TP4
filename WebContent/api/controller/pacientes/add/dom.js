import { div, input, button } from "../../../actions/commons.js";
import * as patients from "../../../actions/patients.js";
/**
 * 
 * @param {string} id 
 * @returns { [HTMLInputElement, HTMLDivElement, HTMLDivElement] }
 */
const vinput = (id) => [
    input("#txt" + id),
    div("#txt" + id + "Valid"),
    div("#txt" + id + "Invalid"),
];

export const [ txtName, txtNameValid, txtNameInvalid ] = vinput("Name");
export const [ txtSurname, txtSurnameValid, txtSurnameInvalid ] = vinput("Surname");
export const [ txtDni, txtDniValid, txtDniInvalid ] = vinput("DNI");
export const [ txtPhone, txtPhoneValid, txtPhoneInvalid ] = vinput("Phone");
export const [ txtAddress, txtAddressValid, txtAddressInvalid ] = vinput("Address");
export const [ txtLocalty, txtLocaltyValid, txtLocaltyInvalid ] = vinput("Localty");
export const [ txtProvince, txtProvinceValid, txtProvinceInvalid ] = vinput("Province");
export const [ txtBirth, txtBirthValid, txtBirthInvalid ] = vinput("Birth");
export const [ txtEmail, txtEmailValid, txtEmailInvalid ] = vinput("Email");
export const btnEnviar = button("#btnEnviar");