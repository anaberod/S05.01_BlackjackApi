package cat.itacademy.blackjackapi.domain.util;

/**
 * Utilidades para calcular valores de cartas de Blackjack.
 */
public final class CardUtils {

    private CardUtils() { }

    /**
     * Devuelve los valores posibles de una carta según su rango.
     * - "A" -> [1, 11]
     * - "K", "Q", "J" -> [10]
     * - "2".."10" -> [n]
     */
    public static int[] getValues(String rank) {
        if (rank == null || rank.isBlank()) return new int[]{0};
        return switch (rank) {
            case "A" -> new int[]{1, 11};
            case "K", "Q", "J" -> new int[]{10};
            default -> {
                try {
                    yield new int[]{Integer.parseInt(rank)};
                } catch (NumberFormatException e) {
                    // Si llega algo inesperado, devolvemos 0 para no reventar el flujo.
                    yield new int[]{0};
                }
            }
        };
    }
}
