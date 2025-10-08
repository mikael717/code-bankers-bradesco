package br.com.bradesco.codebankers.scam_api.dto;

import br.com.bradesco.codebankers.scam_api.domain.log.Verdict;

import java.util.List;

public record VerificationResponse(Verdict verdict, List<String> reasons) { }
