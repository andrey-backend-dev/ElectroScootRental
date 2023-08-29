FROM openjdk
COPY target/electroscoot-0.0.1-SNAPSHOT.jar electroscoot-0.0.1-SNAPSHOT.jar
CMD ["sql/initdb.cmd"]
ENTRYPOINT ["java", "-jar", "/electroscoot-0.0.1-SNAPSHOT.jar"]