package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.config.CodebankersProperties;
import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.dto.external.AbstractEmailResponse;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class EmailReputationRule implements VerificationRule {

    private final CodebankersProperties properties;
    private final RestTemplate restTemplate;

    public EmailReputationRule(CodebankersProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<String> verify(VerificationRequest request) {
        if (request.itemType() != ItemType.EMAIL) {
            return Collections.emptyList();
        }

        String email = NormalizerUtil.normalizeEmail(request.itemValue());
        List<String> reasons = new ArrayList<>();

        try {
            String url = "https://emailreputation.abstractapi.com/v1/?api_key="
                    + properties.getAbstractApiKey()
                    + "&email=" + email;

            AbstractEmailResponse response = restTemplate.getForObject(url, AbstractEmailResponse.class);

            if (response != null) {
                if (response.quality() != null && response.quality().isDisposable()) {
                    reasons.add("EMAIL_IS_DISPOSABLE");
                }

                if (response.quality() != null && response.quality().score() != null) {
                    try {
                        double score = Double.parseDouble(response.quality().score());
                        if (score < 0.40) {
                            reasons.add("LOW_REPUTATION_SCORE");
                        }
                    } catch (NumberFormatException e) {
                    }
                }

                if (response.domain() != null && response.domain().domainAgeDays() != null) {
                    int days = response.domain().domainAgeDays();
                    if (days > 0 && days < 30) {
                        reasons.add("DOMAIN_CREATED_RECENTLY");
                    }
                }

                if (response.deliverability() != null && "undeliverable".equalsIgnoreCase(response.deliverability().status())) {
                    reasons.add("EMAIL_ADDRESS_NOT_EXIST");
                }
            }

        } catch (Exception e) {
            System.err.println("Erro na API Abstract: " + e.getMessage());
        }

        return reasons;
    }
}