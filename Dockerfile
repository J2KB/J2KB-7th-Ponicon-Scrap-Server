FROM openjdk:11
ARG JAR_FILE=./build/libs/scrap-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-DSpring.profiles.active=dev","-jar","/app.jar"]

