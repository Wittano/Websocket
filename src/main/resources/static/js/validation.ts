//#region Variables
let nameError: Element | null = document.querySelector("#name-error");
let passwordError: Element | null = document.querySelector("#password-error");
let emailError: Element | null = document.querySelector("#email-error");
let nameInput: HTMLInputElement | null = document.querySelector("#name");
let passwordInput: HTMLInputElement | null = document.querySelector("#password");
let passwordRepeatInput: HTMLInputElement | null = document.querySelector("#repeat-password");
let passwordRepeatError: HTMLSpanElement | null = document.querySelector("#password-repeat-error");
let emailInput: HTMLInputElement | null = document.querySelector("#email");
let emailRepeatInput: HTMLInputElement | null = document.querySelector("#repeat-email");
let emailRepeatError: HTMLSpanElement | null = document.querySelector("#email-repeat-error");
let registerButton: HTMLButtonElement | null = document.querySelector('#register-button');
//#endregion

class ErrorMessage {
    private _nameMsg: string;
    private _passwordMsg: string;
    private _emailMsg: string;


    constructor(nameMsg: string = "", passwordMsg: string = "", emailMsg: string = "") {
        this._nameMsg = nameMsg;
        this._passwordMsg = passwordMsg;
        this._emailMsg = emailMsg;
    }

    get nameMsg(): string {
        return this._nameMsg;
    }

    set nameMsg(value: string) {
        this._nameMsg = value;
    }

    get passwordMsg(): string {
        return this._passwordMsg;
    }

    set passwordMsg(value: string) {
        this._passwordMsg = value;
    }

    get emailMsg(): string {
        return this._emailMsg;
    }

    set emailMsg(value: string) {
        this._emailMsg = value;
    }
}

class FormValidation {
    private username: string;
    private password: string;
    private email: string;

    public errorMessage: ErrorMessage = new ErrorMessage();

    constructor(nick: string = "", passwd: string = "", email: string = ""){
        this.username = nick;
        this.password = passwd;
        this.email = email;
    }

    public ValidName(username: string): boolean {
        let regex: RegExp = new RegExp('[A-Za-z0-9]{4,}');

        if(regex.test(username)){
            return true;
        }
        else if(username.length < 4)
            this.errorMessage.nameMsg = "Nazwa użytkownika jest za krótka";
        else
            this.errorMessage.nameMsg = "Niepoprawna nazwa użytkonika";

        return false;
    }

    public ValidPassword(passwd: string): boolean{
        let regex: RegExp = new RegExp('[A-Za-z0-9!#$&*:;.,]{4,30}');

        return regex.test(passwd);
    }

    public ValidEmail(email: string): boolean{
        let regex: RegExp = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);

        return regex.test(email.toLowerCase());
    }

    public Valid(): boolean {
        return this.ValidName(this.username) && 
            this.ValidPassword(this.password) &&
            this.ValidEmail(this.email);
    }

    public SetName(nick: string){
        this.username = nick;
    }

    public SetPassword(password: string){
        this.password = password;
    }

    public SetEmail(email: string){
        this.email = email;
    }
}

enum InputType {
    Name, Password, Email
}

let form: FormValidation;

document.addEventListener("DOMContentLoaded", () => {
    form = new FormValidation();

    nameInput?.addEventListener("input", () => {
        form.SetName(nameInput?.value == undefined ? "" : nameInput.value);
        if(form.Valid() && nameInput?.value.length != 0 && nameError != null){
            nameError.innerHTML = "";
            enableRegisterButton();
        } else if(nameInput?.value.length === 0 || form.ValidName(String(nameInput?.value))){
            if(nameError != null)
                nameError.innerHTML = "";
        } else{
            disableRegisterButton();
            setErrorMessage(InputType.Name, "Nieporawna nazwa użytkownika");
        }
    });

    passwordInput?.addEventListener("input", () => {
        form.SetPassword(String(passwordInput?.value));
        if(form.Valid() && passwordInput?.value.length != 0 && passwordError != null){
           passwordError.innerHTML = "";
           enableRegisterButton();
        } else if(passwordInput?.value.length == 0 || form.ValidPassword(String(passwordInput?.value))){
            if(passwordError != null)
                passwordError.innerHTML = "";
        } else {
            disableRegisterButton();
            setErrorMessage(InputType.Password, "Niepoprawne hasło");
        }
    });
    passwordRepeatInput?.addEventListener("focusout", (e) => {
        if(passwordRepeatError != null){
            if((passwordInput != null && passwordRepeatInput != null) && !sameValue(passwordInput, passwordRepeatInput)){
                passwordRepeatError.innerHTML = "Hasła nie są takie same!";
            } else {
                passwordRepeatError.innerHTML = "";
            }
        }
    });

    emailInput?.addEventListener("input", () => {
        form.SetEmail(emailInput?.value == undefined ? "" : emailInput.value);
        if(form.Valid() && emailInput?.value.length != 0 && emailError != null){
            emailError.innerHTML = "";
            enableRegisterButton();
        }
        else if(emailInput?.value.length == 0 || form.ValidEmail(String(emailInput?.value))){
            if(emailError != null)
                emailError.innerHTML = "";
        }
        else{
            disableRegisterButton();
            setErrorMessage(InputType.Email, "Nieporawny email");
        }
    });
    emailRepeatInput?.addEventListener("focusout", (e) => {
        if(emailRepeatError != null){
            if((emailInput != null && emailRepeatInput != null) && !sameValue(emailInput, emailRepeatInput)){
                emailRepeatError.innerHTML = "Maile nie są takie same!";
            } else {
                emailRepeatError.innerHTML = "";
            }
        }
    });
});

function setErrorMessage(type: InputType, msg: string){
   switch (type) {
       case InputType.Name:{
            if(nameError != null)
                nameError.innerHTML = msg;
       } break;
       case InputType.Email: {
            if(emailError != null)
                emailError.innerHTML = msg;
       } break;
       case InputType.Password: {
            if(passwordError != null)
                passwordError.innerHTML = msg;
       } break;

       default:
           break;
   } 
}

function enableRegisterButton(){
    if(registerButton !== null)
        registerButton.disabled = true;
}

function disableRegisterButton(){
    if(registerButton !== null)
        registerButton.disabled = false;
}

function sameValue(origin: HTMLInputElement, repeat: HTMLInputElement): boolean {
    return origin.value == repeat.value;
}