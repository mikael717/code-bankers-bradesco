package br.com.bradesco.codebankers.scam_api.dto;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BlacklistAddRequest(
        @NotNull
        ItemType itemType,

        @NotBlank
        String itemValue,

        String source
) {}