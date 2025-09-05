// src/main/java/cat/itacademy/blackjackapi/web/controller/PingController.java
package cat.itacademy.blackjackapi.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Endpoint simple para verificar que la aplicación está viva.
 */
@RestController
public class PingController {

    @GetMapping("/ping")
    public Mono<Map<String, String>> ping() {
        return Mono.just(Map.of("status", "ok"));
    }
}
