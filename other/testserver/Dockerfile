FROM openjdk:8-jdk-alpine
ADD testing.jar app.jar
ADD wait-for.sh wait-for.sh
ADD start.sh start.sh
ENTRYPOINT ["/bin/sh", "start.sh"]