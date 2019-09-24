# Ryanair microservice

##### This microservice is built over Spring Boot 2 architecture and Gradle build automation tool.

##### This microservice can be tested through the Swagger User Interface  available at : http://localhost:8080/flights once the service has been deployed.

##### The microservice can be built as a bootable .jar archive that can be found at /build/libs directory

**Restrictions**

                
- The difference between departure and arrival datetimes cannot be greater than 24 h
- The minimum time difference between interconnected flights is 2 h
- Only 2 flights at maximum can be interconnected to reach arrival airport from departure airport