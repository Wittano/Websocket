const username: string | undefined = document.querySelector("#username")?.innerHTML.replace("Witaj ", "");

if (username != null) {
    window.sessionStorage.setItem("username", username);
}
