package br.com.bradesco.codebankers.scam_api.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AbstractEmailResponse(
        @JsonProperty("email_quality") Quality quality,
        @JsonProperty("email_domain") Domain domain,
        @JsonProperty("email_deliverability") Deliverability deliverability
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Quality(
            @JsonProperty("score") String score,
            @JsonProperty("is_disposable") boolean isDisposable
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Domain(
            @JsonProperty("domain_age") Integer domainAgeDays
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Deliverability(
            @JsonProperty("status") String status
    ) {}
}