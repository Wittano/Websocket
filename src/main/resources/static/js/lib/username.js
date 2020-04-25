"use strict";
var _a;
var username = (_a = document.querySelector("#username")) === null || _a === void 0 ? void 0 : _a.innerHTML.replace("Witaj ", "");
if (username != null) {
    window.sessionStorage.setItem("username", username);
}
