package cat.itacademy.blackjackapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = BlackjackapiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                // Evita tocar BDs y repos reactivos (R2DBC/Mongo) y Flyway:
                "spring.autoconfigure.exclude=" +
                        "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration," +
                        "org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration",
                // No intentes levantar Swagger/OpenAPI en el test:
                "springdoc.api-docs.enabled=false",
                "springdoc.swagger-ui.enabled=false",
                // Crea beans bajo demanda:
                "spring.main.lazy-initialization=true",
                // Aclara que es app reactiva:
                "spring.main.web-application-type=reactive"
        }
)
class SmokeTest {

    @Test
    void contextLoads() {
        // Pasa si el contexto arranca.
    }
}
