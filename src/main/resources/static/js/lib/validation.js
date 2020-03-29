"use strict";
//#region Variables
var nameError = document.querySelector("#name-error");
var passwordError = document.querySelector("#password-error");
var emailError = document.querySelector("#email-error");
var nameInput = document.querySelector("#name");
var passwordInput = document.querySelector("#password");
var passwordRepeatInput = document.querySelector("#repeat-password");
var passwordRepeatError = document.querySelector("#password-repeat-error");
var emailInput = document.querySelector("#email");
var emailRepeatInput = document.querySelector("#repeat-email");
var emailRepeatError = document.querySelector("#email-repeat-error");
var registerButton = document.querySelector('#register-button');
var radioBox = document.querySelectorAll(".radio-button");
var dateInput = document.querySelector("#date");
//#endregion
//#region Enums
var InputType;
(function (InputType) {
    InputType[InputType["NAME"] = 0] = "NAME";
    InputType[InputType["PASSWORD"] = 1] = "PASSWORD";
    InputType[InputType["EMAIL"] = 2] = "EMAIL";
})(InputType || (InputType = {}));
var UpdateFormType;
(function (UpdateFormType) {
    UpdateFormType[UpdateFormType["GENDER"] = 0] = "GENDER";
    UpdateFormType[UpdateFormType["DATE"] = 1] = "DATE";
    UpdateFormType[UpdateFormType["EMAIL"] = 2] = "EMAIL";
})(UpdateFormType || (UpdateFormType = {}));
//#endregion
//#region Classes
var ErrorMessage = /** @class */ (function () {
    function ErrorMessage(nameMsg, passwordMsg, emailMsg) {
        if (nameMsg === void 0) { nameMsg = ""; }
        if (passwordMsg === void 0) { passwordMsg = ""; }
        if (emailMsg === void 0) { emailMsg = ""; }
        this._nameMsg = nameMsg;
        this._passwordMsg = passwordMsg;
        this._emailMsg = emailMsg;
    }
    Object.defineProperty(ErrorMessage.prototype, "nameMsg", {
        //#region Getters and Setters
        get: function () {
            return this._nameMsg;
        },
        set: function (value) {
            this._nameMsg = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ErrorMessage.prototype, "passwordMsg", {
        get: function () {
            return this._passwordMsg;
        },
        set: function (value) {
            this._passwordMsg = value;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(ErrorMessage.prototype, "emailMsg", {
        get: function () {
            return this._emailMsg;
        },
        set: function (value) {
            this._emailMsg = value;
        },
        enumerable: true,
        configurable: true
    });
    return ErrorMessage;
}());
var FormValidation = /** @class */ (function () {
    function FormValidation(nick, passwd, email) {
        if (nick === void 0) { nick = ""; }
        if (passwd === void 0) { passwd = ""; }
        if (email === void 0) { email = ""; }
        this.errorMessage = new ErrorMessage();
        this.username = nick;
        this.password = passwd;
        this.email = email;
        this.isSelectedGender = false;
        this.isSelectedDate = false;
        this.isEmailRepeatCorrect = false;
    }
    FormValidation.prototype.Update = function (obj, type) {
        switch (type) {
            case UpdateFormType.DATE:
                {
                    this.isSelectedDate = Boolean(obj);
                }
                break;
            case UpdateFormType.GENDER:
                {
                    this.isSelectedGender = Boolean(obj);
                }
                break;
            case UpdateFormType.EMAIL:
                {
                    this.isEmailRepeatCorrect = Boolean(obj);
                }
                break;
            default:
                break;
        }
    };
    FormValidation.prototype.ValidName = function (username) {
        var regex = new RegExp('[A-Za-z0-9]{4,}');
        if (regex.test(username)) {
            return true;
        }
        else if (username.length < 4)
            this.errorMessage.nameMsg = "Nazwa użytkownika jest za krótka";
        else
            this.errorMessage.nameMsg = "Niepoprawna nazwa użytkonika";
        return false;
    };
    FormValidation.prototype.ValidPassword = function (passwd) {
        var regex = new RegExp('[A-Za-z0-9!#$&*:;.,]{4,30}');
        return regex.test(passwd);
    };
    FormValidation.prototype.ValidEmail = function (email) {
        var regex = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
        return regex.test(email.toLowerCase());
    };
    FormValidation.prototype.Valid = function () {
        return this.ValidName(this.username) &&
            this.ValidPassword(this.password) &&
            this.ValidEmail(this.email) &&
            this.isSelectedGender && this.isSelectedDate && this.isEmailRepeatCorrect;
    };
    //#region Setters
    FormValidation.prototype.SetName = function (nick) {
        this.username = nick;
    };
    FormValidation.prototype.SetPassword = function (password) {
        this.password = password;
    };
    FormValidation.prototype.SetEmail = function (email) {
        this.email = email;
    };
    //#endregion
    FormValidation.prototype.radioUpdate = function (status) {
        if (status === void 0) { status = true; }
        this.Update(status, UpdateFormType.GENDER);
    };
    FormValidation.prototype.dateUpdate = function (status) {
        if (status === void 0) { status = true; }
        this.Update(status, UpdateFormType.DATE);
    };
    FormValidation.prototype.emailUpdate = function (status) {
        if (status === void 0) { status = true; }
        this.Update(status, UpdateFormType.EMAIL);
    };
    return FormValidation;
}());
//#endregion
var form;
document.addEventListener("DOMContentLoaded", function () {
    form = new FormValidation();
    disableRegisterButton();
    nameInput === null || nameInput === void 0 ? void 0 : nameInput.addEventListener("focusout", function () {
        form.SetName((nameInput === null || nameInput === void 0 ? void 0 : nameInput.value) == undefined ? "" : nameInput.value);
        if (form.Valid() && (nameInput === null || nameInput === void 0 ? void 0 : nameInput.value.length) != 0 && nameError != null) {
            nameError.innerHTML = "";
            enableRegisterButton();
        }
        else if ((nameInput === null || nameInput === void 0 ? void 0 : nameInput.value.length) === 0 || form.ValidName(String(nameInput === null || nameInput === void 0 ? void 0 : nameInput.value))) {
            if (nameError != null)
                nameError.innerHTML = "";
        }
        else {
            disableRegisterButton();
            setErrorMessage(InputType.NAME, "Nieporawna nazwa użytkownika");
        }
    });
    passwordInput === null || passwordInput === void 0 ? void 0 : passwordInput.addEventListener("focusout", function () {
        form.SetPassword(String(passwordInput === null || passwordInput === void 0 ? void 0 : passwordInput.value));
        if (form.Valid() && (passwordInput === null || passwordInput === void 0 ? void 0 : passwordInput.value.length) != 0 && passwordError != null) {
            passwordError.innerHTML = "";
            enableRegisterButton();
        }
        else if ((passwordInput === null || passwordInput === void 0 ? void 0 : passwordInput.value.length) == 0 ||
            form.ValidPassword(String(passwordInput === null || passwordInput === void 0 ? void 0 : passwordInput.value))) {
            if (passwordError != null)
                passwordError.innerHTML = "";
        }
        else {
            disableRegisterButton();
            setErrorMessage(InputType.PASSWORD, "Niepoprawne hasło");
        }
    });
    passwordRepeatInput === null || passwordRepeatInput === void 0 ? void 0 : passwordRepeatInput.addEventListener("focusout", function () {
        if (passwordRepeatError != null) {
            if ((passwordInput != null && passwordRepeatInput != null) &&
                !sameValue(passwordInput, passwordRepeatInput)) {
                passwordRepeatError.innerHTML = "Hasła nie są takie same!";
            }
            else {
                passwordRepeatError.innerHTML = "";
            }
        }
    });
    emailInput === null || emailInput === void 0 ? void 0 : emailInput.addEventListener("focusout", function () {
        form.SetEmail((emailInput === null || emailInput === void 0 ? void 0 : emailInput.value) == undefined ? "" : emailInput.value);
        if (form.Valid() && (emailInput === null || emailInput === void 0 ? void 0 : emailInput.value.length) != 0 && emailError != null) {
            emailError.innerHTML = "";
            enableRegisterButton();
        }
        else if ((emailInput === null || emailInput === void 0 ? void 0 : emailInput.value.length) == 0 || form.ValidEmail(String(emailInput === null || emailInput === void 0 ? void 0 : emailInput.value))) {
            if (emailError != null)
                emailError.innerHTML = "";
        }
        else {
            disableRegisterButton();
            setErrorMessage(InputType.EMAIL, "Nieporawny email");
        }
    });
    emailRepeatInput === null || emailRepeatInput === void 0 ? void 0 : emailRepeatInput.addEventListener("focusout", function () {
        if (emailRepeatError != null) {
            if ((emailInput != null && emailRepeatInput != null) &&
                !sameValue(emailInput, emailRepeatInput)) {
                emailRepeatError.innerHTML = "Maile nie są takie same!";
                form.emailUpdate(false);
                disableRegisterButton();
            }
            else {
                emailRepeatError.innerHTML = "";
                form.emailUpdate();
            }
        }
    });
    radioBox.forEach(function (element) {
        element === null || element === void 0 ? void 0 : element.addEventListener("click", function () {
            form.radioUpdate();
            if (form.Valid()) {
                enableRegisterButton();
            }
        });
    });
    dateInput === null || dateInput === void 0 ? void 0 : dateInput.addEventListener("input", function () {
        form.dateUpdate();
        if (form.Valid()) {
            enableRegisterButton();
        }
    });
});
//#region Functions
function setErrorMessage(type, msg) {
    switch (type) {
        case InputType.NAME:
            {
                if (nameError != null)
                    nameError.innerHTML = msg;
            }
            break;
        case InputType.EMAIL:
            {
                if (emailError != null)
                    emailError.innerHTML = msg;
            }
            break;
        case InputType.PASSWORD:
            {
                if (passwordError != null)
                    passwordError.innerHTML = msg;
            }
            break;
        default:
            break;
    }
}
function enableRegisterButton() {
    if (registerButton !== null)
        registerButton.disabled = false;
}
function disableRegisterButton() {
    if (registerButton !== null)
        registerButton.disabled = true;
}
function sameValue(origin, repeat) {
    return origin.value == repeat.value;
}
//#endregion
