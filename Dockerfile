FROM adoptopenjdk:11.0.11_9-jre-openj9-0.26.0-focal
RUN mkdir /opt/app
COPY build/libs/KevinChamorro-BancoPichincha-0.0.1-RELEASE.jar /opt/app
CMD ["java", "-jar", "/opt/app/KevinChamorro-BancoPichincha-0.0.1-RELEASE.jar"]