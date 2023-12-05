FROM openjdk:21-ubuntu
ADD /target/demo-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT["java","-jar","backend.jar"]