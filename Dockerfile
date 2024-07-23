FROM gradle:8.5-jdk17
WORKDIR /opt/app
COPY build/libs/ServiceLink-BE-0.0.1-SNAPSHOT.jar ./
ENV SPRING_PROFILES_ACTIVE=staging
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar ServiceLink-BE-0.0.1-SNAPSHOT.jar"]