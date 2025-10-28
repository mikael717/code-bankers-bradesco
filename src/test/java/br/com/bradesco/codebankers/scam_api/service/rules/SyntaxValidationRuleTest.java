package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntaxValidationRuleTest {

    private final SyntaxValidationRule rule = new SyntaxValidationRule();

    @Test
    void emailValido_devePassar() {
        var req = new VerificationRequest(ItemType.EMAIL, "user.name+tag@Bradesco.com.br");
        List<String> reasons = rule.verify(req);
        assertTrue(reasons.isEmpty());
    }

    @Test
    void emailInvalido_deveRetornarMotivo() {
        var req = new VerificationRequest(ItemType.EMAIL, "abc@@");
        List<String> reasons = rule.verify(req);
        assertTrue(reasons.contains("INVALID_EMAIL_SYNTAX"));
    }

    @Test
    void phoneValido_devePassar() {
        var req = new VerificationRequest(ItemType.PHONE, "+55 (11) 98765-4321");
        List<String> reasons = rule.verify(req);
        assertTrue(reasons.isEmpty());
    }

    @Test
    void phoneInvalido_deveRetornarMotivo() {
        var req = new VerificationRequest(ItemType.PHONE, "12.34");
        List<String> reasons = rule.verify(req);
        assertTrue(reasons.contains("INVALID_PHONE_SYNTAX"));
    }

    @Test
    void urlValida_devePassar() {
        var req = new VerificationRequest(ItemType.URL, "https://www.bradesco.com.br/seguranca");
        List<String> reasons = rule.verify(req);
        assertTrue(reasons.isEmpty());
    }

    @Test
    void urlInvalida_deveRetornarMotivo(){
        var req = new VerificationRequest(ItemType.URL, "notaurl");
        List<String> reasons = rule.verify(req);
        assertTrue(reasons.contains("INVALID_URL_SYNTAX"));
    }
}
