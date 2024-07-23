'use strict';
import * as page from "./dom.js";
import * as patients from "./../../../actions/patients.js";
//import libphonenumber from "https://cdnjs.cloudflare.com/ajax/libs/google-libphonenumber/3.2.37/libphonenumber.min.js";

function calcularDiferencia(date) {
    const today = new Date();
    const actualYear = today.getFullYear();
    const actualMonth = today.getMonth();
    const actualDate = today.getDate();
  
    const referencedYear = date.getFullYear();
    const referencedMonth = date.getMonth();
    const referencedDate = date.getDate();
  
    let yearsApart = actualYear - referencedYear;
    let monthsApart = actualMonth - referencedMonth;
    let daysApart = actualDate - referencedDate;
  
    if (monthsApart < 0) {
      yearsApart--;
      monthsApart += 12;
    }
    if (daysApart < 0) {
      monthsApart--;
      const lastDayOfPrevMonth = new Date(actualYear, actualMonth, 0).getDate();
      daysApart += lastDayOfPrevMonth;
    }
  
    return { years: yearsApart, months: monthsApart, days: daysApart };
  }

const labelBirth = (date) => {
    const x = calcularDiferencia(date);
    const z = new Date();
    const isBirthDay = z.getDate() == date.getDate() && z.getMonth() == date.getMonth();
    let y = "";
    if(x.years) {
        y = `${x.years} año${x.years > 1 ? "s" : ""}`;
        if(isBirthDay) return `Hoy cumple ${y}`;
        else return y;
    }
    if(x.months) return `${x.months} mes${x.months > 1 ? "es" : ""}`;
    if(x.days > 1) return `${x.days} día${x.days > 1 ? "s" : ""}`;
    if(x.days == 0) return `El paciente nació hoy. `;
    if(x.days == 1) return `El paciente nació ayer. `;
};

const valid = {
    name: false,
    surname: false,
    dni: false,
    phone: false,
    address: false,
    localty: false,
    province: false,
    birth: false,
    email: false
};

const validateName = () => {
    const name = page.txtName.value;
    if(name.length == 0) {
        iv("name", "Campo requerido. ");
    } else {
        v("name");
    }
};
const validateSurname = () => {
    const surname = page.txtSurname.value;
    if(surname.length == 0) {
        iv("surname", "Campo requerido. ");
    } else v("surname");
};

const validateDni = async () => {
    const dni = page.txtDni.value;
    if(dni.length == 0) {
        iv("dni", "Campo requerido. ");
    } else if(/\D/.test(dni)) {
        iv("dni", "El D.N.I. sólo puede contener números. ");
    } else {
        patients.existsByDNI(dni).then(ok => {
            if(ok) iv("dni", "El D.N.I. ya fue registrado. ");
            else v("dni");
        }).catch(err => {
            v("dni")
        })
    }
};
const validateBirth = () => {
    const birth = page.txtBirth.valueAsDate;
    birth.setDate(birth.getDate() + 1);
    if(birth.getTime() > new Date().getTime()) {
        iv("birth", "El paciente aún no nació. ")
    } else v("birth", labelBirth(birth));
};
const validatePhone = () => {
    

};

const initPhone = () => {
    // @ts-ignore
    const { AsYouTypeFormatter } = libphonenumber;
    const asYouType = new AsYouTypeFormatter('ar');
    const input = page.txtPhone;

    input.addEventListener('keyup', (event) => {
        asYouType.clear();
        if(!input.value.startsWith("+54")) asYouType.inputDigit("+54");
        input.value.split("").map(letter => {
            asYouType.inputDigit(letter);
        });
        const validated = asYouType.currentOutput_;
        (asYouType.isCompleteNumber_ ? v : iv)("phone", validated);
    });
    console.log(asYouType);
}


page.txtName.addEventListener('change', validateName);
page.txtSurname.addEventListener('change', validateSurname);
page.txtDni.addEventListener('change', validateDni);
page.txtBirth.addEventListener('change', validateBirth);
initPhone();
page.txtPhone.addEventListener('change', validatePhone);

const v = (attr, v = "") => {
    valid[attr] = true;
    attr = attr[0].toUpperCase() + attr.slice(1);
    page["txt" + attr].classList.add("is-valid");
    page["txt" + attr].classList.remove("is-invalid");
    page["txt" + attr + "Valid"].innerText = v;
    page["txt" + attr + "Invalid"].innerText = '';
    //console.log(valid);
};

const iv = (attr, v = "") => {
    valid[attr] = false;
    attr = attr[0].toUpperCase() + attr.slice(1);
    page["txt" + attr].classList.remove("is-valid");
    page["txt" + attr].classList.add("is-invalid");
    page["txt" + attr + "Valid"].innerText = '';
    page["txt" + attr + "Invalid"].innerText = v;
    //console.log(valid);
};

(async () => {
    //console.log("OK");
})();