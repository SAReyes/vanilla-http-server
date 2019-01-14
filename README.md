[![Build Status](https://travis-ci.com/SAReyes/vanilla-http-server.svg?branch=master)](https://travis-ci.com/SAReyes/vanilla-http-server)
[![API docs](https://img.shields.io/badge/API%20docs-1.0.0-brightgreen.svg)](https://app.swaggerhub.com/apis-docs/SAReyes/vanilla-store-example/1.0.0)
[![Docker image](https://img.shields.io/badge/Docker%20image-latest-brightgreen.svg?logo=docker)](https://cloud.docker.com/u/sareyes/repository/docker/sareyes/vanilla-store-example)
[![Heroku](https://heroku-badge.herokuapp.com/?app=vanilla-store-example)](https://vanilla-store-example.herokuapp.com)

# Vanilla Java8 Http server
Small example on how to build a vanilla http server using Java8.

## Modules
* `org.example.logger` - A LoggerFactory to make sure the java.util.logging properties are loaded.
* `org.example.server` - The server itself with a wrapper around `HttpClient.java`.
* `org.example.store` - A example store using this server, can be tested using its 
[swagger definition](https://app.swaggerhub.com/apis-docs/SAReyes/vanilla-store-example/1.0.0). 
[Entity diagram](store-example/entities-diagram.png).

## Dependencies
Only one dependency has been used on the implementation process of this server:
* `com.fasterxml.jackson` - The jackson libraries have been used to avoid the hassle to reimplement a json parser,
 library. The focus of this project is the usage of the `HttpClient.java` class.

`JUnit` and `AssertJ` have been used for testing purposes. 

## Build
Maven wrapper provided, only the command below needs to be run:

Windows:
```
$ mvnw.cmd package
```

UNIX:
```
$ ./mvnw package
```

This will generate a jar file on `store/target/store-example-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Run the application
The jar file can be run to launch the example server:
```
$ java -jar store/target/store-example-1.0-SNAPSHOT-jar-with-dependencies.jar
```

This will launch the server on the port 8080. Alternatively, the system variable `$PORT` can be set to change this 
behaviour.

## Container
Docker has been used with a multi-staged build. This provides more flexibility to test and deliver the example. The
commands below will lunch the application locally. `Docker` cli is a requirement for this step:
```
$ docker pull sareyes/vanilla-store-example
$ docker run -p 8080:8080 sareyes/vanilla-store-example
```

## CI
Travis runs the Continuous Integration and Continuous Delivery tasks. More info can be found on 
[travis-ci.com](https://travis-ci.com/SAReyes/vanilla-http-server)

## CD
The example is available on Heroku, which is hooked into the Open API definition available in the
[swagger hub](https://app.swaggerhub.com/apis-docs/SAReyes/vanilla-store-example/1.0.0). Travis has been configured
to deploy all changes from master into https://vanilla-store-example.herokuapp.com
