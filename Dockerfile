FROM openjdk:11
COPY target/websocket.jar .
EXPOSE 8080
CMD java -jar websocket.jar