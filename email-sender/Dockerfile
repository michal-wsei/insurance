FROM maven:3.8.5-openjdk-11 AS maven_build

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package

FROM openjdk

MAINTAINER michal.garncarz@microsoft.wsei.edu.pl

EXPOSE 8081

COPY --from=maven_build /tmp/target/email-sender-0.0.1-SNAPSHOT.jar /data/email-sender-0.0.1-SNAPSHOT.jar

CMD java -jar /data/email-sender-0.0.1-SNAPSHOT.jar