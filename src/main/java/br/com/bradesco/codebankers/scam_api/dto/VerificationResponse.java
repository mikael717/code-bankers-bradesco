package br.com.bradesco.codebankers.scam_api.dto;

import br.com.bradesco.codebankers.scam_api.domain.ItemType; // <--- Não esqueça do import!
import br.com.bradesco.codebankers.scam_api.domain.log.Verdict;

import java.util.List;

public record VerificationResponse(
        Verdict verdict,
        List<String> reasons,
        ItemType itemType,
        String itemValue,
        int riskScore
) {
}