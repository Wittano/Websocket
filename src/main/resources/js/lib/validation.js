"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
//#region Variables
var nameError = document.querySelector("#name-error");
var passwordError = document.querySelector("#password-error");
var emailError = document.querySelector("#email-error");
var nameInput = document.querySelector("#name");
var passwordInput = document.querySelector("#password");
var emailInput = document.querySelector("#email");
var registerButton = document.querySelector('#register-button');
//#endregion
var FormValidation = /** @class */ (function () {
    function FormValidation() {
    }
    FormValidation.prototype.ValidName = function (username) {
        var regex = '[A-Za-z0-9]';
        return username.match(regex) !== null;
    };
    FormValidation.prototype.ValidPassword = function (passwd) {
        var regex = '[A-Za-z0-9!#$&*:;.,]{4,30}';
        return passwd.match(regex) !== null;
    };
    FormValidation.prototype.ValidEmail = function (email) {
        var regex = '1/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/';
        return email.toLowerCase().match(regex) !== null;
    };
    FormValidation.prototype.Valid = function () {
        return this.ValidName(this.username) &&
            this.ValidPassword(this.password) &&
            this.ValidEmail(this.email);
    };
    FormValidation.prototype.SetName = function (nick) {
        this.username = nick;
    };
    FormValidation.prototype.SetPassword = function (password) {
        this.password = password;
    };
    FormValidation.prototype.SetEmail = function (email) {
        this.email = email;
    };
    return FormValidation;
}());
var ErrorMessage = /** @class */ (function () {
    function ErrorMessage(nameMsg, passwordMsg, emailMsg) {
        if (nameMsg === void 0) { nameMsg = ""; }
        if (passwordMsg === void 0) { passwordMsg = ""; }
        if (emailMsg === void 0) { emailMsg = ""; }
        this.nameErrorMessage = nameMsg;
        this.passwordErrorMessage = passwordMsg;
        this.emailErrorMessage = emailMsg;
    }
    return ErrorMessage;
}());
var InputType;
(function (InputType) {
    InputType[InputType["Name"] = 0] = "Name";
    InputType[InputType["Password"] = 1] = "Password";
    InputType[InputType["Email"] = 2] = "Email";
})(InputType || (InputType = {}));
var form;
document.addEventListener("DOMContentLoaded", function () {
    form = new FormValidation();
    nameInput === null || nameInput === void 0 ? void 0 : nameInput.addEventListener("input", function (e) {
    });
    emailInput === null || emailInput === void 0 ? void 0 : emailInput.addEventListener("input", function (e) {
    });
    passwordError === null || passwordError === void 0 ? void 0 : passwordError.addEventListener("input", function (e) {
    });
});
function setErrorMessage(type, msg) {
    switch (type) {
        case InputType.Name: {
            if (nameError != null)
                nameError.innerHTML = msg;
        }
        case InputType.Email: {
            if (emailError != null)
                emailError.innerHTML = msg;
        }
        case InputType.Password: {
            if (passwordError != null)
                passwordError.innerHTML = msg;
        }
        default:
            break;
    }
}
function enableRegisterButton(form) {
    if (registerButton !== null)
        registerButton.className = 'register-button-enable';
}
function disableRegisterButton(form) {
    if (registerButton !== null)
        registerButton.className = 'register-button-disable';
}
