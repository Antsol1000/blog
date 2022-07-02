# Blog application
gRPC server that allows creating users and posting notes.

### blog-api
Protobuf definitions of data format.

### blog-server
gRPC server build with Spring Boot.

### blog-client
Simple client to talk to server.

### blog-e2e
E2e tests with:
- local instance of server, you need to run the server locally:
```
mvn spring-boot:run -pl blog-server
```
- test containers, you need to create docker image of server
```
mvn spring-boot:build-image -pl blog-server
```
