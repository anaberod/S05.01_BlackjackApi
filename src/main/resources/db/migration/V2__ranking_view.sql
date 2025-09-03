-- V2__ranking_view.sql

-- Borra la vista si existe (por si re-aplicas)
DROP VIEW IF EXISTS v_ranking;

-- Vista con métricas por jugador
CREATE VIEW v_ranking AS
SELECT
    p.id                                   AS player_id,
    p.name                                 AS name,
    SUM(CASE WHEN gr.result = 'WIN'        THEN 1 ELSE 0 END)        AS wins,
    SUM(CASE WHEN gr.result = 'LOSS'       THEN 1 ELSE 0 END)        AS losses,
    SUM(CASE WHEN gr.result = 'BLACKJACK'  THEN 1 ELSE 0 END)        AS blackjacks,
    COALESCE(SUM(gr.delta), 0)                                     AS total_earnings,
    CASE
        WHEN COUNT(*) = 0 THEN 0
        ELSE ROUND(SUM(CASE WHEN gr.result IN ('WIN','BLACKJACK') THEN 1 ELSE 0 END) / COUNT(*) * 100, 2)
    END                                                            AS win_rate
FROM players p
LEFT JOIN game_results gr ON gr.player_id = p.id
GROUP BY p.id, p.name
ORDER BY total_earnings DESC, win_rate DESC, wins DESC;
