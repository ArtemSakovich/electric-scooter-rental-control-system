FROM openjdk

ARG JAR_FILE=*.jar

COPY ../electric-scooter-rental-control-system-web/target/${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]