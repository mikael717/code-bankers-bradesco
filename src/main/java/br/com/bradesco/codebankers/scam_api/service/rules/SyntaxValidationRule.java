package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
@Order(5) //roda antes das outras regras no pipeline
public class SyntaxValidationRule implements VerificationRule {

    private static final Pattern EMAIL_PATTERN = Pattern.
            compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern PHONE_DIGITS_PATTERN = Pattern.compile("^\\d{8,15}$");

    @Override
    public List<String> verify(VerificationRequest request) {
        final ItemType itemType = request.itemType();
        final String rawValue = request.itemValue();

        return switch (itemType) {
            case EMAIL -> validateEmail(rawValue);
            case PHONE -> validatePhone(rawValue);
            case URL -> validateUrl(rawValue);
        };
    }

    private List<String> validateEmail(String rawValue) {
        final String normalizedEmail = NormalizerUtil.normalizeEmail(rawValue);
        if (normalizedEmail == null || normalizedEmail.isBlank() || !EMAIL_PATTERN.matcher(normalizedEmail).matches()) {
            return List.of("INVALID_EMAIL_SYNTAX");
        }
        return Collections.emptyList();
    }

    private List<String> validatePhone(String rawValue) {
        final String digitsOnly = NormalizerUtil.normalizePhone(rawValue); //so digitos
        if (digitsOnly == null || !PHONE_DIGITS_PATTERN.matcher(digitsOnly).matches()) {
            return List.of("INVALID_PHONE_SYNTAX");
        }
        return Collections.emptyList();
    }

    private List<String> validateUrl(String rawValue) {
        final String normalizedUrl = NormalizerUtil.normalizeUrl(rawValue);
        if (normalizedUrl == null || normalizedUrl.isBlank()) {
            return List.of("INVALID_URL_SYNTAX");
        }
        try {
            URI uri = new URI(normalizedUrl);
            final String scheme = uri.getScheme();
            final String host = uri.getHost();
            final boolean schemeOk = "http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme);
            final boolean hostOk = host != null && host.contains(".");
            if (!schemeOk || !hostOk) {
                return List.of("INVALID_URL_SYNTAX");
            }
            return Collections.emptyList();
        } catch (URISyntaxException e) {
            return List.of("INVALID_URL_SYNTAX");
        }
    }
}

