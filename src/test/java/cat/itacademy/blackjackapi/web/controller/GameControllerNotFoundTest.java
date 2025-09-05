package cat.itacademy.blackjackapi.web.controller;

import cat.itacademy.blackjackapi.application.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.UUID;

@WebFluxTest(controllers = GameController.class)
@Import(GameControllerNotFoundTest.MockConfig.class)
class GameControllerNotFoundTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GameService gameService; // mock

    @TestConfiguration
    static class MockConfig {
        @Bean
        GameService gameService() {
            return Mockito.mock(GameService.class);
        }
    }

    @Test
    void getGame_shouldReturn404_whenGameNotFound() {
        UUID gameId = UUID.randomUUID();

        Mockito.when(gameService.getGame(gameId))
                .thenReturn(Mono.error(new NoSuchElementException("Game not found")));

        webTestClient.get()
                .uri("/game/{id}", gameId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("Not Found")
                .jsonPath("$.message").isEqualTo("Game not found");
    }
}
