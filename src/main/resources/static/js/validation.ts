//#region Variables
let nameError: Element | null = document.querySelector("#name-error");
let passwordError: Element | null = document.querySelector("#password-error");
let emailError: Element | null = document.querySelector("#email-error");
let nameInput: HTMLInputElement | null = document.querySelector("#name");
let passwordInput: HTMLInputElement | null = document.querySelector("#password");
let emailInput: HTMLInputElement | null = document.querySelector("#email");
let registerButton: Element | null = document.querySelector('#register-button');
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

    private ValidName(username: string): boolean {
        let regex: RegExp = new RegExp('[A-Za-z0-9]{4,}');

        if(regex.test(username){
            return true;
        }
        else if(username.length < 4)
            this.errorMessage.nameMsg = "Nazwa użytkownika jest za krótka";
        else
            this.errorMessage.nameMsg = "Niepoprawna nazwa użytkonika";

        return false;
    }

    private ValidPassword(passwd: string): boolean{
        let regex: RegExp = new RegExp('[A-Za-z0-9!#$&*:;.,]{4,30}');

        return regex.test(passwd);
    }

    private ValidEmail(email: string): boolean{
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
        } else if(nameInput?.value.length === 0 && nameError != null){
            nameError.innerHTML = "";
        } else{
            disableRegisterButton();
            setErrorMessage(InputType.Name, "Nieporawna nazwa użytkownika");
        }
    });

    emailInput?.addEventListener("input", () => {
        form.SetEmail(emailInput?.value == undefined ? "" : emailInput.value);
        if(form.Valid() && emailInput?.value.length != 0 && emailError != null){
            emailError.innerHTML = "";
            enableRegisterButton();
        }
        else if(emailInput?.value.length == 0 && emailError != null){
           emailError.innerHTML = "";
        }
        else{
            disableRegisterButton();
            setErrorMessage(InputType.Email, "Nieporawny email");
        }
    });

    passwordError?.addEventListener("input", () => {
        form.SetPassword(passwordInput?.value == undefined ? "" : passwordInput.value);
        if(form.Valid() && passwordInput?.value.length != 0 && passwordError != null){
           passwordError.innerHTML = "";
           enableRegisterButton();
        } else if(passwordInput?.value.length == 0 && passwordError != null){
            passwordError.innerHTML = "";
        } else {
            disableRegisterButton();
            setErrorMessage(InputType.Email, "Nieporawny hasło");
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
        registerButton.className = 'register-button-enable';
}

function disableRegisterButton(){
    if(registerButton !== null)
        registerButton.className = 'register-button-disable';
}