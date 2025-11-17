package br.com.bradesco.codebankers.scam_api.dto;

import java.util.Map;

public record StatsResponse(
        long totalCount,
        Map<String, Long> verdictCounts,
        Map<String, Long> typeCounts
) {
}
