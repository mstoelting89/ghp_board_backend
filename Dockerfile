FROM openjdk:11
RUN mkdir -p /upload/images
RUN chmod -R 777 /upload
COPY ./system/build/libs/system-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
