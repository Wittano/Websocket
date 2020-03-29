//#region Variables
const nameError: Element | null = document.querySelector("#name-error");
const passwordError: Element | null = document.querySelector("#password-error");
const emailError: Element | null = document.querySelector("#email-error");
const nameInput: HTMLInputElement | null = document.querySelector("#name");
const passwordInput: HTMLInputElement | null = document.querySelector("#password");
const passwordRepeatInput: HTMLInputElement | null = document.querySelector("#repeat-password");
const passwordRepeatError: HTMLSpanElement | null = document.querySelector("#password-repeat-error");
const emailInput: HTMLInputElement | null = document.querySelector("#email");
const emailRepeatInput: HTMLInputElement | null = document.querySelector("#repeat-email");
const emailRepeatError: HTMLSpanElement | null = document.querySelector("#email-repeat-error");
const registerButton: HTMLButtonElement | null = document.querySelector('#register-button');
const radioBox: NodeListOf<HTMLInputElement> = document.querySelectorAll(".radio-button");
const dateInput: HTMLDataElement | null = document.querySelector("#date");
//#endregion

//#region Interfaces
interface IFormObserver {
    radioUpdate(status: boolean): void;
    dateUpdate(status: boolean): void;
    emailUpdate(status: boolean): void;
}
//#endregion

//#region Enums
enum InputType {
    NAME, PASSWORD, EMAIL
}

enum UpdateFormType {
    GENDER, DATE, EMAIL
}
//#endregion

//#region Classes
class ErrorMessage {
    private _nameMsg: string;
    private _passwordMsg: string;
    private _emailMsg: string;

    constructor(nameMsg: string = "", passwordMsg: string = "", emailMsg: string = "") {
        this._nameMsg = nameMsg;
        this._passwordMsg = passwordMsg;
        this._emailMsg = emailMsg;
    }

    //#region Getters and Setters
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
    //#endregion
}

class FormValidation implements IFormObserver{
    private username: string;
    private password: string;
    private email: string;
    private isSelectedGender: boolean;
    private isSelectedDate: boolean;
    private isEmailRepeatCorrect: boolean;
    
    public errorMessage: ErrorMessage = new ErrorMessage();

    constructor(nick: string = "", passwd: string = "", email: string = ""){
        this.username = nick;
        this.password = passwd;
        this.email = email;
        this.isSelectedGender = false;
        this.isSelectedDate = false;
        this.isEmailRepeatCorrect = false;
    }

    private Update(obj: Object, type: UpdateFormType){
        switch (type) {
            case UpdateFormType.DATE:{
               this.isSelectedDate = Boolean(obj);
            }break;
            case UpdateFormType.GENDER:{
                this.isSelectedGender = Boolean(obj);
            }break;
            case UpdateFormType.EMAIL:{
                this.isEmailRepeatCorrect = Boolean(obj);
            }break;
            default:
                break;
        }
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
            this.ValidEmail(this.email) &&
            this.isSelectedGender && this.isSelectedDate && this.isEmailRepeatCorrect;
    }

    //#region Setters
    public SetName(nick: string){
        this.username = nick;
    }

    public SetPassword(password: string){
        this.password = password;
    }

    public SetEmail(email: string){
        this.email = email;
    }
    //#endregion

    public radioUpdate(status: boolean = true) {
        this.Update(status, UpdateFormType.GENDER);
    }

    public dateUpdate(status: boolean = true) {
        this.Update(status, UpdateFormType.DATE);
    }

    emailUpdate(status: boolean = true): void {
        this.Update(status, UpdateFormType.EMAIL);
    }
}
//#endregion

let form: FormValidation;

document.addEventListener("DOMContentLoaded", () => {
    form = new FormValidation();

    disableRegisterButton();

    nameInput?.addEventListener("focusout", () => {
        form.SetName(nameInput?.value == undefined ? "" : nameInput.value);
        if(form.Valid() && nameInput?.value.length != 0 && nameError != null){
            nameError.innerHTML = "";
            enableRegisterButton();
        } else if(nameInput?.value.length === 0 || form.ValidName(String(nameInput?.value))){
            if(nameError != null)
                nameError.innerHTML = "";
        } else{
            disableRegisterButton();
            setErrorMessage(InputType.NAME, "Nieporawna nazwa użytkownika");
        }
    });

    passwordInput?.addEventListener("focusout", () => {
        form.SetPassword(String(passwordInput?.value));
        if(form.Valid() && passwordInput?.value.length != 0 && passwordError != null){
           passwordError.innerHTML = "";
           enableRegisterButton();
        } else if(passwordInput?.value.length == 0 ||
            form.ValidPassword(String(passwordInput?.value))){
            if(passwordError != null)
                passwordError.innerHTML = "";
        } else {
            disableRegisterButton();
            setErrorMessage(InputType.PASSWORD, "Niepoprawne hasło");
        }
    });
    passwordRepeatInput?.addEventListener("focusout", () => {
        if(passwordRepeatError != null){
            if((passwordInput != null && passwordRepeatInput != null) &&
                !sameValue(passwordInput, passwordRepeatInput)){
                passwordRepeatError.innerHTML = "Hasła nie są takie same!";
            } else {
                passwordRepeatError.innerHTML = "";
            }
        }
    });

    emailInput?.addEventListener("focusout", () => {
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
            setErrorMessage(InputType.EMAIL, "Nieporawny email");
        }
    });
    emailRepeatInput?.addEventListener("focusout", () => {
        if(emailRepeatError != null){
            if((emailInput != null && emailRepeatInput != null) &&
                !sameValue(emailInput, emailRepeatInput)){
                emailRepeatError.innerHTML = "Maile nie są takie same!";
                form.emailUpdate(false);
                disableRegisterButton();
            } else {
                emailRepeatError.innerHTML = "";
                form.emailUpdate();
            }
        }
    });


    radioBox.forEach(element => {
        element?.addEventListener("click", () => {
            form.radioUpdate();
            if(form.Valid()){
                enableRegisterButton();
            }
        });
    });

    dateInput?.addEventListener("input", () => {
       form.dateUpdate();
       if(form.Valid()){
           enableRegisterButton();
       }
    });
});

//#region Functions
function setErrorMessage(type: InputType, msg: string){
   switch (type) {
       case InputType.NAME:{
            if(nameError != null)
                nameError.innerHTML = msg;
       } break;
       case InputType.EMAIL: {
            if(emailError != null)
                emailError.innerHTML = msg;
       } break;
       case InputType.PASSWORD: {
            if(passwordError != null)
                passwordError.innerHTML = msg;
       } break;

       default:
           break;
   } 
}

function enableRegisterButton(){
    if(registerButton !== null)
        registerButton.disabled = false;
}

function disableRegisterButton(){
    if(registerButton !== null)
        registerButton.disabled = true;
}

function sameValue(origin: HTMLInputElement, repeat: HTMLInputElement): boolean {
    return origin.value == repeat.value;
}
//#endregion
