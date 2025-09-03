package cat.itacademy.blackjackapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test mínimo para verificar que el contexto de Spring Boot arranca correctamente.
 */
@SpringBootTest
class ContextLoadsTest {

    @Test
    void contextLoads() {
        // Si la app arranca sin excepciones, el test pasa ✅
    }
}
