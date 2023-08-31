FROM maven AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk
COPY --from=build /home/app/target/electroscoot-0.0.1-SNAPSHOT.jar electroscoot-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/electroscoot-0.0.1-SNAPSHOT.jar"]