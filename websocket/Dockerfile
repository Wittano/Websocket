FROM openjdk:11
COPY target/websocket.jar .
EXPOSE 8080
EXPOSE["java", "-jar", "-Dspring.profiles.active=docker","websocket.jar"]