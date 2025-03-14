# docker build --no-cache -t tinello/tomcat-api:2024.07.25 .
# docker run --name tomcat-api -e DB_URL="host.docker.internal:5432/postgres" -e DB_USER=postgres -e DB_PASS=mysecretpassword --rm -d -p 8080:8080 tinello/tomcat-api:2024.07.25

FROM ibm-semeru-runtimes:open-23.0.2_7-jdk AS build
COPY ./gradle gradle/
COPY ./src src/
COPY ./build.gradle .
COPY ./gradlew .
COPY ./settings.gradle .
RUN ./gradlew clean shadowJar


FROM ibm-semeru-runtimes:open-23.0.2_7-jre AS run
RUN apt update && apt dist-upgrade -y --no-install-recommends && apt-get clean && apt-get autoclean
WORKDIR /
COPY --from=build /build/libs/*.jar api.jar
EXPOSE 8080

#RUN /usr/sbin/adduser --disabled-password -u 1001 api
#USER api

CMD java -jar api.jar