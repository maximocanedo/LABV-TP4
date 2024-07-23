'use strict';
import * as page from "./dom.js";
import * as doctors from "./../../../actions/doctors.js";
import { resolveLocalUrl } from "../../../lib/commons.js";
//import libphonenumber from "https://cdnjs.cloudflare.com/ajax/libs/google-libphonenumber/3.2.37/libphonenumber.min.js";



const v = (attr, v = "") => {
    valid[attr] = true;
    attr = attr[0].toUpperCase() + attr.slice(1);
    page["txt" + attr].classList.add("is-valid");
    page["txt" + attr].classList.remove("is-invalid");
    page["txt" + attr + "Valid"].innerText = v;
    page["txt" + attr + "Invalid"].innerText = '';
   // console.log(valid);
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
    //console.log(valid);
};


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
};

const valid = {
    name: false,
    surname: false,
    file: false,
    sex: false,
    phone: false,
    address: false,
    localty: false,
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

const validateFile = async () => {
    const file = page.txtFile.value;
    if(file.length == 0) {
        iv("file", "Campo requerido. ");
    } else if(/\D/.test(file)) {
        iv("file", "El legajo sólo puede contener números. ");
    } else {
        doctors.existsByFile(file).then(ok => {
            if(ok) iv("file", "El legajo ya fue registrado. ");
            else v("file");
        }).catch(err => {
            v("file")
        })
    }
};
const validateBirth = () => {
    const birth = page.txtBirth.valueAsDate;
    birth.setDate(birth.getDate() + 1);
    const d18 = new Date();
    d18.setFullYear(d18.getFullYear() - 18);

    if(birth.getTime() > d18.getTime()) {
        iv("birth", "El médico debe ser mayor de edad. ")
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
const validateSex = () => {
    const sex = page.txtSex.value;
    if(sex != 'M' && sex != 'F') iv("sex", "Ingrese un valor válido. ");
    else v("sex");
};
const validateUser = () => {
    const user = page.userSelector.getSelectedFile();
    if(user == null) iv("user", "Seleccione un usuario. ");
    else if(user.doctor) iv("user", "Este usuario ya tiene un doctor asignado. Intente con otro. ");
    else v("user"); 
};
const validateSpecialty = () => {
    const specialty = page.specialtySelector.getSelectedFile();
    if(specialty == null) iv("specialty", "Seleccione una especialidad. ");
    else v("specialty");
}

page.txtName.addEventListener('change', validateName);
page.txtSurname.addEventListener('change', validateSurname);
page.txtFile.addEventListener('change', validateFile);
page.txtBirth.addEventListener('change', validateBirth);
page.txtEmail.addEventListener('change', validateEmail);
page.txtAddress.addEventListener('change', validateAddress);
page.txtLocalty.addEventListener('change', validateLocalty);
page.txtSex.addEventListener('change', validateSex);
page.txtName.addEventListener('blur', validateName);
page.txtSurname.addEventListener('blur', validateSurname);
page.txtFile.addEventListener('blur', validateFile);
page.txtBirth.addEventListener('blur', validateBirth);
page.txtEmail.addEventListener('blur', validateEmail);
page.txtAddress.addEventListener('blur', validateAddress);
page.txtLocalty.addEventListener('blur', validateLocalty);
page.specialtySelector.getTrigger().addEventListener('change', validateSpecialty);
page.userSelector.getTrigger().addEventListener('change', validateUser);
page.btnEnviar.addEventListener('click', async () => {
    const birth = page.txtBirth.valueAsDate;
    birth.setDate(birth.getDate() + 1);
    const data = {
        name: page.txtName.value,
        surname: page.txtSurname.value,
        file: page.txtFile.value,
        birth,
        phone: phoneValue,
        email: page.txtEmail.value,
        address: page.txtAddress.value,
        localty: page.txtLocalty.value,
        specialty: page.specialtySelector.getSelectedFile(),
        sex: page.txtSex.value,
        user: page.userSelector.getSelectedFile()
    };
    doctors.create(data)
        .then(doctor => {
            const url = resolveLocalUrl(`/medicos/manage?file=${doctor.file}`);
            window.location.href = url;
        }).catch(console.error);
});


(async () => {
    initPhone();
})();