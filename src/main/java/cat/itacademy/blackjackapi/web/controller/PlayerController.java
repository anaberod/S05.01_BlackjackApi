package cat.itacademy.blackjackapi.web.controller;

import cat.itacademy.blackjackapi.application.dto.PlayerRenameRequest;
import cat.itacademy.blackjackapi.application.dto.PlayerView;
import cat.itacademy.blackjackapi.application.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Endpoints para operaciones sobre jugadores.
 */
@Validated
@RestController
@RequestMapping("/player")
@Tag(name = "Player", description = "Player endpoints")
public class PlayerController {

    private final PlayerService playerService;

    // Inyección por constructor (buena práctica)
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Renombra un jugador existente.
     * - PathVariable: playerId (UUID del jugador)
     * - Body: { "name": "NuevoNombre" }
     * - Respuesta: PlayerView actualizado (200 OK)
     */
    @Operation(summary = "Rename a player")
    @PutMapping("/{playerId}")
    public Mono<PlayerView> renamePlayer(@PathVariable UUID playerId,
                                         @Valid @RequestBody PlayerRenameRequest request) {
        // Llamamos al servicio y devolvemos la vista actualizada
        return playerService.renamePlayer(playerId, request.name());
    }
}
