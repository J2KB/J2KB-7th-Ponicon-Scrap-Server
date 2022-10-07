FROM openjdk:11
WORKDIR build/libs
COPY scrap-0.0.1-SNAPSHOT.jar build/libs/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

