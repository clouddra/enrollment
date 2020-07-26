## Running the project

To run the server as docker container, first build the docker image using the Dockerfile provided. 
```shell script
docker build --target deployable -t yl/enrollment:latest .
```

Run the container by running 
```shell script
docker run -p 8080:8080 yl/enrollment
```

You should be able to access the swagger UI from http://localhost:8080/swagger-ui.html.

### Test

You can run the tests directly using the built image by overriding the entry point
```shell script
docker run -it --entrypoint "./gradlew" yl/enrollment test
```

## Documentation

## APIs
You can see all the APIs available from /swagger-ui.html and are self-explanatory.
Note that classname has to be of the format `{number}<whitespace>{uppercase-alphabet}`. E.g. `2 A`, `4 D`. All student fields are mandatory except `nationality`.

### Persistency
The apis are backed by an in memory h2 database. You can access that admin interface at http://localhost:8080/h2-console. The in-memory database is destroyed each time the server dies. Migrations are maintained using flyway at (/src/main/resources/db/migrations) and are run everytime on application boot. The migrations also include pre-seeded test data.


### Tests
Some integration tests are added using the same pre-seeded data. Unit tests are not added since this project is still small enough to test the system in full.

