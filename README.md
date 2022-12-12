# IO DIGITAL API

___
### Spring Boot Application

---
This project provides to manage Ted Talks information. There is a default data.csv file inside /resources/static/ path that contains Ted Talks data.
The views and likes fields may be corrupted, we set them to 0 by default.


### Prerequisites

---
- Maven
- Docker

### Run & Build
There are 2 ways of run & build the application.
Test user is already inserted, set Authorization header to 1.

1-Maven:

Install maven dependencies with command below

```sh
mvn clean install
```

After that for running the application use the command below

```sh
mvn spring-boot:run
```
Api runs with localhost:8080
http://localhost:8080/swagger-ui/

2-Docker

Run below command within the project path with given sequence
```sh
mvn install
```

```sh
docker build -t api-docker-image .
```

```sh
docker-compose up
```
Api runs with localhost:9091

APi documentation http://localhost:9091/swagger-ui/index.html





### TechStacks

---
- Java 11
- Spring Boot
- Mongo DB
- Lombok
- Restful API
- Maven
- JUnit 5
- Sonarlint (local)
- Spotify (https://open.spotify.com/playlist/7yDx2tF46QYAfMnGxEcY2h?si=8e61d071ec4941b7)


