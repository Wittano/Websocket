FROM openjdk:11
ADD target/websocket-0.2.jar .
EXPOSE 8080
CMD java -jar websocket-0.2.jar