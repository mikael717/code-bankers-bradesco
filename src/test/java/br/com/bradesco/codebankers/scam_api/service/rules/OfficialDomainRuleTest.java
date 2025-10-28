package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.config.CodebankersProperties;
import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfficialDomainRuleTest {

    private OfficialDomainRule newRule(List<String> domains) {
        var props = new CodebankersProperties();
        props.setOfficialDomains(domains);
        return new OfficialDomainRule(props);
    }

    @Test
    void shouldAddReasonWhenUrlMatchesOfficialBaseDomain() {
        var rule = newRule(List.of("bradesco.com.br"));
        var req = new VerificationRequest(ItemType.URL, "https://bradesco.com.br/minha-conta");
        var reasons = rule.verify(req);
        assertTrue(reasons.contains("MATCH_OFFICIAL_DOMAIN"));
    }

    @Test
    void shouldAddReasonWhenUrlMatchesSubdomainOfOfficialBaseDomain(){
        var rule = newRule(List.of("bradesco.com.br"));
        var req = new VerificationRequest(ItemType.URL, "https://secure.bradesco.com.br/login");
        var reasons = rule.verify(req);
        assertTrue(reasons.contains("MATCH_OFFICIAL_DOMAIN"));
    }

    @Test
    void shouldRemainNeutralWhenUrlDoesNotMatchOfficialDomains(){
        var rule = newRule(List.of("bradesco.com.br"));
        var req = new VerificationRequest(ItemType.URL, "https://bradesco-login.xyz/update");
        var reasons = rule.verify(req);
        assertTrue(reasons.isEmpty());
    }
}
