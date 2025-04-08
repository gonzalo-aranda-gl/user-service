## user-service
User Service Api to manage users sign-up and login.

## Version: V1.0

## Technologies used
- Java 11
- Gradle(Kotlin) v7.4
- Springboot v2.5.14

## Notes
At project's root level there's a directory called resources which contains:
- Component's diagram
- Sequence diagram
- A Postman collection to test the api

## How to run locally
- First execute gradle build from your ide's gradle window or just run this command on the ide terminal: ```gradlew build```
- Run the class ```UserServiceApplication``` which is the main class, put simply, you will run the project at this step.
- At startup the project will generate an in memory H2 database and created the basic tables needed to execute the service.
- You can explore the in memory db in you browser using: http://localhost:8080/user-service/h2-console
The test credentials are stored in the local profile.
- Once the program has started it will run on port 8080 by default. You can test it via swagger ui or postman.
If you choose postman, just import the provided collection from the resources folder at root project level.
- For swagger ui use this url: http://localhost:8080/user-service/swagger-ui/index.html
