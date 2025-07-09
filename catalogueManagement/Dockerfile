#FROM openjdk:17-jdk-slim
#VOLUME /tmp
#COPY target/*.jar app.jar
#EXPOSE 8089
#ENTRYPOINT ["java", "-jar", "/app.jar"]
FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app.jar"]