package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;

import java.util.List;

public interface VerificationRule {
    List<String> verify(VerificationRequest request);
}
