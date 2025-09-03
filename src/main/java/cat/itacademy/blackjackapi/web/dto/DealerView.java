package cat.itacademy.blackjackapi.web.dto;

import java.util.List;

/**
 * Vista del dealer. Si holeHidden=true, la carta oculta no debe mostrarse.
 */
public record DealerView(
        List<CardView> cards,
        int total,
        boolean holeHidden
) {}
