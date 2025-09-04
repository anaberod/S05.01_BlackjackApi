package cat.itacademy.blackjackapi.application.service;

import cat.itacademy.blackjackapi.application.mapper.GameResultMapper;
import cat.itacademy.blackjackapi.application.service.impl.RankingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class RankingServiceImplTest {

    // 👉 Usa los métodos default reales del mapper
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    GameResultMapper mapper;

    @InjectMocks
    RankingServiceImpl service;

    @Test
    void getRanking_returnsMockedItems() {
        StepVerifier.create(service.getRanking())
                .expectNextCount(2) // el servicio llama al mapper dos veces
                .verifyComplete();
    }
}
