# Websocket
Spring boot app which use websocket, MySQL database and Angular
## Run app for Debian/Ubuntu
Run script: run.sh
## Run app for other linux distro 
You need following programs:
- Maven
- Nodejs
- Docker

After instalation run run_linux.sh
## Run app for Windows
1. Run your Java IDE(IntelliJ IDEA or NetBeans) and pack all project to .jar archive
2. Install [Nodejs](https://nodejs.org/en/)
3. Run this command inside angular directory:
    ```
    npm install
    ```
4. Run containers using Docker
    ```ps
    docker-compse up --build
    ```
