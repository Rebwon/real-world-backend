FROM openjdk:11-jdk

WORKDIR /workspace/app
MAINTAINER msolo021015@gmail.com
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jre

VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-cp","app:app/lib/*","com.rebwon.realworldbackend.RealWorldBackendApplication"]