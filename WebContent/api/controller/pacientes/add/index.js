'use strict';
import * as page from "./dom.js";
import * as patients from "./../../../actions/patients.js";
import { resolveLocalUrl } from "../../../lib/commons.js";
//import libphonenumber from "https://cdnjs.cloudflare.com/ajax/libs/google-libphonenumber/3.2.37/libphonenumber.min.js";
import { control } from "./../../../controller/web.auth.js";
import { PERMIT } from "./../../../actions/users.js";
import { toastAPIErrors } from "./../../../actions/commons.js";

(async () => {
    // @ts-ignore
    window.me = await control(true, [PERMIT.CREATE_PATIENT]);
})();
const calcularDiferencia = (date) => {
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

let phoneValue = page.txtPhone.value;

const initPhone = () => {
    // @ts-ignore
    const { AsYouTypeFormatter } = libphonenumber;
    // @ts-ignore
    console.log(libphonenumber);
    const asYouType = new AsYouTypeFormatter('ar');
    const input = page.txtPhone;
    const {phoneUtil_} = asYouType;

    input.addEventListener('keyup', (event) => {
        asYouType.clear();
        if(!input.value.startsWith("+") && input.value.trim() != "") {
            asYouType.inputDigit("+");
            asYouType.inputDigit("5");
            asYouType.inputDigit("4");
        }
        input.value.split("").map(letter => {
            asYouType.inputDigit(letter);
        });
        const validated = asYouType.currentOutput_;
        phoneValue = validated;
        if(!asYouType.isCompleteNumber_ )
            iv("phone", validated);
        try {
            const phone = phoneUtil_.parse(validated);
            const ev = phoneUtil_.isValidNumber(phone);
            if(ev) v("phone", validated);
            else iv("phone", "Ingrese un número de teléfono válido. ");
        } catch(expected) { } 
    });
}

const validateEmail = () => {
    const email = page.txtEmail.value;
    if(/[^@]+@[^@]+\.[^@]+/.test(email)) v("email");
    else iv("email", "Dirección de correo electrónico inválida");
};

const validateAddress = () => {
    const address = page.txtAddress.value.trim();
    if(address.length == 0) iv("address", "Ingrese una dirección postal válida. ");
    else v("address");
};
const validateLocalty = () => {
    const localty = page.txtLocalty.value.trim();
    if(localty.length == 0) iv("localty", "Campo requerido. ");
    else v("localty");
};
const validateProvince = () => {
    const province = page.txtProvince.value.trim();
    if(province.length == 0) iv("province", "Campo requerido. ");
    else v("province");
};


page.txtName.addEventListener('change', validateName);
page.txtSurname.addEventListener('change', validateSurname);
page.txtDni.addEventListener('change', validateDni);
page.txtBirth.addEventListener('change', validateBirth);
page.txtEmail.addEventListener('change', validateEmail);
page.txtAddress.addEventListener('change', validateAddress);
page.txtLocalty.addEventListener('change', validateLocalty);
page.txtProvince.addEventListener('change', validateProvince);
page.txtName.addEventListener('blur', validateName);
page.txtSurname.addEventListener('blur', validateSurname);
page.txtDni.addEventListener('blur', validateDni);
page.txtBirth.addEventListener('blur', validateBirth);
page.txtEmail.addEventListener('blur', validateEmail);
page.txtAddress.addEventListener('blur', validateAddress);
page.txtLocalty.addEventListener('blur', validateLocalty);
page.txtProvince.addEventListener('blur', validateProvince);
page.btnEnviar.addEventListener('click', async () => {
    const birth = page.txtBirth.valueAsDate;
    birth.setDate(birth.getDate() + 1);
    const data = {
        name: page.txtName.value,
        surname: page.txtSurname.value,
        dni: page.txtDni.value,
        birth,
        phone: phoneValue,
        email: page.txtEmail.value,
        address: page.txtAddress.value,
        localty: page.txtLocalty.value,
        province: page.txtProvince.value
    };
    console.log(data);
    patients.create(data)
        .then(patient => {
            const url = resolveLocalUrl(`/pacientes/manage/?id=${patient.id}`);
            window.location.href = url;
        }).catch(toastAPIErrors);

});



const v = (attr, v = "") => {
    valid[attr] = true;
    attr = attr[0].toUpperCase() + attr.slice(1);
    page["txt" + attr].classList.add("is-valid");
    page["txt" + attr].classList.remove("is-invalid");
    page["txt" + attr + "Valid"].innerText = v;
    page["txt" + attr + "Invalid"].innerText = '';
    page.btnEnviar.disabled = Object.keys(valid).some(key => !valid[key]);
};

const iv = (attr, v = "") => {
    valid[attr] = false;
    attr = attr[0].toUpperCase() + attr.slice(1);
    page["txt" + attr].classList.remove("is-valid");
    page["txt" + attr].classList.add("is-invalid");
    page["txt" + attr + "Valid"].innerText = '';
    page["txt" + attr + "Invalid"].innerText = v;
    page.btnEnviar.disabled = true;
};

(async () => {
    initPhone();
})();