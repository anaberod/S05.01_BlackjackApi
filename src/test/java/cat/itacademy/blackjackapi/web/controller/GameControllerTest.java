package cat.itacademy.blackjackapi.web.controller;

import cat.itacademy.blackjackapi.application.service.GameService;
import cat.itacademy.blackjackapi.web.error.GlobalErrorHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifica que una petición inválida produce 400 y un JSON de error uniforme.
 * Aceptamos "Validation failed" o "Bad Request" en $.error para ser compatibles
 * con diferencias de manejo entre versiones de WebFlux.
 */
@WebFluxTest(controllers = GameController.class)
@Import({ GlobalErrorHandler.class, GameControllerTest.MockConfig.class })
class GameControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GameService gameService; // mock registrado abajo

    @TestConfiguration
    static class MockConfig {
        @Bean
        GameService gameService() {
            return Mockito.mock(GameService.class);
        }
    }

    @Test
    void createGame_shouldReturn400_whenPlayerNameIsBlank() {
        // JSON inválido: playerName vacío -> dispara validación @NotBlank en CreateGameRequest
        String invalidJson = """
                { "playerName": "" }
                """;

        webTestClient.post()
                .uri("/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidJson)
                .exchange()
                // 1) Estado HTTP esperado
                .expectStatus().isBadRequest()
                // 2) Cuerpo JSON con la forma uniforme esperada
                .expectBody()
                .jsonPath("$.timestamp").exists()
                .jsonPath("$.path").isEqualTo("/game/new")
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.message").exists()
                // 3) error puede ser "Validation failed" o "Bad Request" según versión/config
                .jsonPath("$.error").value(val ->
                        assertThat(val.toString()).isIn("Validation failed", "Bad Request")
                );
    }
}
