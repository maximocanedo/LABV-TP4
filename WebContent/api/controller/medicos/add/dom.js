import { SpecialtySelector } from "../../../lib/selectors/SpecialtySelector.js";
import { div, input, button, select } from "../../../actions/commons.js";
import { UserSelector } from "../../../lib/selectors/UserSelector.js";
import * as users from "../../../actions/users.js";
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
export const [ txtFile, txtFileValid, txtFileInvalid ] = vinput("File");
export const [ txtPhone, txtPhoneValid, txtPhoneInvalid ] = vinput("Phone");
export const [ txtAddress, txtAddressValid, txtAddressInvalid ] = vinput("Address");
export const [ txtLocalty, txtLocaltyValid, txtLocaltyInvalid ] = vinput("Localty");
export const [ txtBirth, txtBirthValid, txtBirthInvalid ] = vinput("Birth");
export const [ txtEmail, txtEmailValid, txtEmailInvalid ] = vinput("Email");
export const [ userSelector, txtUserValid, txtUserInvalid ] = 
    [new UserSelector(), div("#txtUserValid"), div("#txtUserInvalid")];
export const txtSpecialtyWrapper = div(".specialtySelectorWrapper");
export const txtUserWrapper = div(".txtUserWrapper");
export const txtSex = select("#txtSex");
export const txtSexValid = select("#txtSexValid");
export const txtSexInvalid = select("#txtSexInvalid");
export const [ specialtySelector, txtSpecialtyValid, txtSpecialtyInvalid ] = [
    new SpecialtySelector(), div("#specialtySelectorValid"), div("#specialtySelectorInvalid")
];
export const btnEnviar = button("#btnEnviar");
export const txtSpecialty = specialtySelector.getTrigger();
export const txtUser = userSelector.getTrigger().querySelector(".form-control");
userSelector.setInitialQuery(new users.Query().filterByUnassigned(true));
txtSpecialtyWrapper.append(txtSpecialty);
txtUserWrapper.prepend(userSelector.getTrigger());