package br.com.bradesco.codebankers.scam_api.domain.log;

public enum Verdict {
    SAFE,           // Seguro (0 pontos)
    LOW_RISK,       // Leve Possibilidade (1-20 pontos)
    MEDIUM_RISK,    // Possibilidade Média (21-60 pontos)
    HIGH_RISK,      // Alta Chance (61-99 pontos)
    CONFIRMED_SCAM  // Golpe / Blacklist (100+ pontos)
}