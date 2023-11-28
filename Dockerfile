FROM openjdk:17-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/poc-uniovi-avib-data-projection-backend-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app
 
# spring boot profile
ARG ARG_SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=$ARG_SPRING_PROFILES_ACTIVE

# cp target/{jarfileName}.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar
 
# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]