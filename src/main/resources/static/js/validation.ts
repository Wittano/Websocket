import { strict } from "assert";

//#region Variables
let nameError: Element | null = document.querySelector("#name-error");
let passwordError: Element | null = document.querySelector("#password-error");
let emailError: Element | null = document.querySelector("#email-error");
let nameInput: Element | null = document.querySelector("#name")
let passwordInput: Element | null = document.querySelector("#password")
let emailInput: Element | null = document.querySelector("#email")
let registerButton: Element | null = document.querySelector('#register-button');
//#endregion

class FormValidation {

    private username: string;
    private password: string;
    private email: string;

    private ValidName(username: string): boolean {
        let regex: string = '[A-Za-z0-9]'

        return username.match(regex) !== null;
    }

    private ValidPassword(passwd: string): boolean{
        let regex: string = '[A-Za-z0-9!#$&*:;.,]{4,30}'

        return passwd.match(regex) !== null;
    }

    private ValidEmail(email: string): boolean{
        let regex: string = '1/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/';

        return email.toLowerCase().match(regex) !== null;
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

class ErrorMessage{
    public nameErrorMessage: string;
    public passwordErrorMessage: string;
    public emailErrorMessage: string;

    constructor(nameMsg: string = "", passwordMsg: string = "", emailMsg: string = ""){
        this.nameErrorMessage = nameMsg;
        this.passwordErrorMessage = passwordMsg;
        this.emailErrorMessage = emailMsg;
    }
}

enum InputType {
    Name, Password, Email
}

let form: FormValidation;

document.addEventListener("DOMContentLoaded", () => {
    form = new FormValidation();

    nameInput?.addEventListener("input", (e) => {

    });
    emailInput?.addEventListener("input", (e) => {

    });
    passwordError?.addEventListener("input", (e) => {

    });
});

function setErrorMessage(type: InputType, msg: string){
   switch (type) {
       case InputType.Name:{
            if(nameError != null)
                nameError.innerHTML = msg;
       }
       case InputType.Email: {
            if(emailError != null)
                emailError.innerHTML = msg;
       }
       case InputType.Password: {
            if(passwordError != null)
                passwordError.innerHTML = msg;
       }

       default:
           break;
   } 
}

function enableRegisterButton(form: FormValidation){
    if(registerButton !== null)
        registerButton.className = 'register-button-enable';
}

function disableRegisterButton(form: FormValidation){
    if(registerButton !== null)
        registerButton.className = 'register-button-disable';
}