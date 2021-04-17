FROM openjdk:11
COPY target/chat.jar .
EXPOSE 8080
EXPOSE["java", "-jar", "-Dspring.profiles.active=docker","chat.jar"]