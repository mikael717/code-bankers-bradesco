package br.com.bradesco.codebankers.scam_api.dto;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerificationRequest(
        @NotNull
        ItemType itemType,

        @NotBlank
        String itemValue) { }
