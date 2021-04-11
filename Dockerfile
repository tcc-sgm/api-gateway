FROM gradle:6.8.3-jdk8 AS BUILD

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon 

FROM openjdk:8-jre-slim

ARG DATA_TO_BUILD

EXPOSE 8080

COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]


LABEL maintainer="SGM - Sistema de Gestão Integrada Municipal" \
      org.label-schema.name="API-GATWEAY" \
      org.label-schema.vcs-url=https://github.com/tcc-sgm/api-gateway \
      org.label.schema.build-date=$DATA_TO_BUILD