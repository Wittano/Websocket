"use strict";
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
    function FormValidation(nick, passwd, email) {
        if (nick === void 0) { nick = ""; }
        if (passwd === void 0) { passwd = ""; }
        if (email === void 0) { email = ""; }
        this.username = nick;
        this.password = passwd;
        this.email = email;
    }
    FormValidation.prototype.ValidName = function (username) {
        var regex = '[A-Za-z0-9]{4,}';
        return username.match(regex) instanceof String;
    };
    FormValidation.prototype.ValidPassword = function (passwd) {
        var regex = '[A-Za-z0-9!#$&*:;.,]{4,30}';
        return passwd.match(regex) instanceof String;
    };
    FormValidation.prototype.ValidEmail = function (email) {
        var regex = '1/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/';
        return email.toLowerCase().match(regex) instanceof String;
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
var InputType;
(function (InputType) {
    InputType[InputType["Name"] = 0] = "Name";
    InputType[InputType["Password"] = 1] = "Password";
    InputType[InputType["Email"] = 2] = "Email";
})(InputType || (InputType = {}));
var form;
document.addEventListener("DOMContentLoaded", function () {
    form = new FormValidation();
    nameInput === null || nameInput === void 0 ? void 0 : nameInput.addEventListener("input", function () {
        form.SetName((nameInput === null || nameInput === void 0 ? void 0 : nameInput.value) == undefined ? "" : nameInput.value);
        if (form.Valid() && (nameInput === null || nameInput === void 0 ? void 0 : nameInput.value.length) != 0 && nameError != null) {
            nameError.innerHTML = "";
            enableRegisterButton();
        }
        else if ((nameInput === null || nameInput === void 0 ? void 0 : nameInput.value.length) === 0 && nameError != null) {
            nameError.innerHTML = "";
        }
        else {
            disableRegisterButton();
            setErrorMessage(InputType.Name, "Nieporawna nazwa użytkownika");
        }
    });
    emailInput === null || emailInput === void 0 ? void 0 : emailInput.addEventListener("input", function () {
        form.SetEmail((emailInput === null || emailInput === void 0 ? void 0 : emailInput.value) == undefined ? "" : emailInput.value);
        if (form.Valid() && (emailInput === null || emailInput === void 0 ? void 0 : emailInput.value.length) != 0 && emailError != null) {
            emailError.innerHTML = "";
            enableRegisterButton();
        }
        else if ((emailInput === null || emailInput === void 0 ? void 0 : emailInput.value.length) == 0 && emailError != null) {
            emailError.innerHTML = "";
        }
        else {
            disableRegisterButton();
            setErrorMessage(InputType.Email, "Nieporawny email");
        }
    });
    passwordError === null || passwordError === void 0 ? void 0 : passwordError.addEventListener("input", function () {
        form.SetPassword((passwordInput === null || passwordInput === void 0 ? void 0 : passwordInput.value) == undefined ? "" : passwordInput.value);
        if (form.Valid() && (passwordInput === null || passwordInput === void 0 ? void 0 : passwordInput.value.length) != 0 && passwordError != null) {
            passwordError.innerHTML = "";
            enableRegisterButton();
        }
        else if ((passwordInput === null || passwordInput === void 0 ? void 0 : passwordInput.value.length) == 0 && passwordError != null) {
            passwordError.innerHTML = "";
        }
        else {
            disableRegisterButton();
            setErrorMessage(InputType.Email, "Nieporawny hasło");
        }
    });
});
function setErrorMessage(type, msg) {
    switch (type) {
        case InputType.Name:
            {
                if (nameError != null)
                    nameError.innerHTML = msg;
            }
            break;
        case InputType.Email:
            {
                if (emailError != null)
                    emailError.innerHTML = msg;
            }
            break;
        case InputType.Password:
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
        registerButton.className = 'register-button-enable';
}
function disableRegisterButton() {
    if (registerButton !== null)
        registerButton.className = 'register-button-disable';
}
