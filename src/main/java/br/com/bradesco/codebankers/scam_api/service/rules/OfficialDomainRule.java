package br.com.bradesco.codebankers.scam_api.service.rules;


import br.com.bradesco.codebankers.scam_api.config.CodebankersProperties;
import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Adds reason MATCH_OFFICIAL_DOMAIN when URL host belongs to configured official domains.
 * It does NOT change verdict by itself (policy handled in VerificationService if enabled).
 */

@Component
@Order(10) // executa após o SyntaxValidationRule
public class OfficialDomainRule implements VerificationRule {

    private final CodebankersProperties properties;

    public OfficialDomainRule(CodebankersProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<String> verify(VerificationRequest request) {
        if (request.itemType() != ItemType.URL) return Collections.emptyList();

        final String normalizedUrl = NormalizerUtil.normalizeUrl(request.itemValue());

        try {
            URI uri = new URI(normalizedUrl);
            final String host = uri.getHost();
            if (host == null) return Collections.emptyList();

            final String hostLower = host.toLowerCase();

            for (String baseDomain : properties.getOfficialDomains()) {
                final String baseLower = baseDomain.toLowerCase();
                boolean exact = hostLower.equals(baseLower);
                boolean subdomain = hostLower.endsWith("." + baseLower);
                if(exact || subdomain) {
                    return List.of("MATCH_OFFICIAL_DOMAIN");
                }
            }
            return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList(); //se a url estiver invalida, minha classe de validacao sintaxe cuida (neutro aqui)
        }
    }
}
