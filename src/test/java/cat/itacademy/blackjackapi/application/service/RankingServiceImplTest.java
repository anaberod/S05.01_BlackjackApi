package cat.itacademy.blackjackapi.application.service;

import cat.itacademy.blackjackapi.application.mapper.GameResultMapper;
import cat.itacademy.blackjackapi.application.service.impl.RankingServiceImpl;
import cat.itacademy.blackjackapi.web.dto.RankingItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RankingServiceImplTest {

    private GameResultMapper mapper;
    private RankingServiceImpl service;

    @BeforeEach
    void setup() {
        mapper = mock(GameResultMapper.class);

        // Devuelve un RankingItem válido en cada llamada a fromFields(...)
        when(mapper.fromFields(
                any(UUID.class),
                anyString(),
                anyLong(),
                anyLong(),
                anyLong(),
                any(BigDecimal.class),
                any(BigDecimal.class)
        ))
                // Primera llamada
                .thenReturn(new RankingItem(
                        UUID.randomUUID(), "Alice",
                        10, 5, 2,
                        new BigDecimal("150.00"), new BigDecimal("0.6667")
                ))
                // Segunda llamada
                .thenReturn(new RankingItem(
                        UUID.randomUUID(), "Bob",
                        7, 8, 1,
                        new BigDecimal("-20.00"), new BigDecimal("0.4667")
                ));

        service = new RankingServiceImpl(mapper);
    }

    @Test
    void getRanking_returnsMockedItems() {
        StepVerifier.create(service.getRanking())
                .expectNextCount(2) // el servicio llama al mapper dos veces
                .verifyComplete();
    }
}
