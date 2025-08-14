# Tomcat-Embedded-OpenApi

## Requirements <a name="Requirements"></a>
### Software: <a name="Software"></a>
- OpenJDK 20 with OpenJ9 -> https://developer.ibm.com/languages/java/semeru-runtimes/downloads/
- Docker 4.28.0 -> https://docs.docker.com/desktop/install/ubuntu/

### Configure JDK VSCode: <a name="ConfigureJDKvscode"></a>

Change /.vscode/launch.json for your Java Home

### Configure JDK Gradle: <a name="ConfigureJDKgradle"></a>

Change gradle.properties for your Java Home

### Environment variables: <a name="EnvironmentVariables"></a>
- DB_URL=localhost:5432/postgres
- DB_USER=postgres
- DB_PASS=mysecretpassword

### Start PostgreSQL <a name="StartPostgreSQL"></a>
```bash
docker run --name tomcat-openapi-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres:16.3-alpine3.18
```


### Start application Gradle <a name="StartApplicationGradle"></a>
```bash
DB_URL="localhost:5432/postgres" DB_USER=postgres DB_PASS=mysecretpassword ./gradlew run
```


### Start application Java <a name="StartApplicationJava"></a>
```bash
DB_URL="localhost:5432/postgres" DB_USER=postgres DB_PASS=mysecretpassword /home/g/DEV/Tools/jdk-23.0.2+7/bin/java -XX:+TieredStopAtLevel=1 -jar tomcat-embed-1.0.0-all.jar
```


## Development <a name="development"></a>
### Visual Studio Code Extensions: <a name="vscode-extensions"></a>
#### Extension Pack for Java

Install "Extension Pack for Java" from Microsoft: https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack

#### Gradle Extension Pack

Install "Gradle Extension Pack" from Richard Willis https://marketplace.visualstudio.com/items?itemName=richardwillis.vscode-gradle-extension-pack


## Endpoints

| Name                 | Endpoint                                                             |
| -------------------- | -------------------------------------------------------------------- |
| Service Info         | http://localhost:8080/                                               |


## Observability <a name="observability"></a>

### Start Docker Grafana OTEL <a name="start-docker-grafana-otel"></a>

```bash
docker run --restart unless-stopped --detach --publish 3000:3000 --publish 4317:4317 --publish 4318:4318 --name grafana_otel grafana/otel-lgtm:0.11.0
```

Ref: https://hub.docker.com/r/grafana/otel-lgtm

### View Grafana <a name="view-grafana"></a>

Log in to http://localhost:3000 with user admin and password admin.