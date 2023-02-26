FROM openjdk:18-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
WORKDIR /home/spring
RUN mkdir app
WORKDIR  /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN chmod 555 app.jar
USER spring:spring
HEALTHCHECK --interval=1m --timeout=20s --start-period=30s  CMD wget --spider http://localhost:8080/actuator/health
CMD java -Dspring.profiles.active=${PROFILE} -jar app.jar