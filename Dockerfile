FROM openjdk:11

COPY ./system/build/libs/system-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]