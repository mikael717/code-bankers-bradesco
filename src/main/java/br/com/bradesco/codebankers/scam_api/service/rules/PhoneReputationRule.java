package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.config.CodebankersProperties;
import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.lookups.v2.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class PhoneReputationRule implements VerificationRule {

    private final CodebankersProperties properties;

    public PhoneReputationRule(CodebankersProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (properties.getTwilioAccountSid() != null && properties.getTwilioAuthToken() != null) {
            Twilio.init(properties.getTwilioAccountSid(), properties.getTwilioAuthToken());
        }
    }

    @Override
    public List<String> verify(VerificationRequest request) {
        if (request.itemType() != ItemType.PHONE) {
            return Collections.emptyList();
        }
        List<String> reasons = new ArrayList<>();

        String rawPhone = NormalizerUtil.normalizePhone(request.itemValue());
        String formattedPhone = rawPhone.startsWith("55") ? "+" + rawPhone : "+55" + rawPhone;

        try {
            PhoneNumber number = PhoneNumber.fetcher(formattedPhone).setFields("line_type_intelligence").fetch();

            Map<String, Object> intelligence = number.getLineTypeIntelligence();

            if (intelligence != null) {
                String type = (String) intelligence.get("type");
                String carrierName = (String) intelligence.get("carrier_name");

                if ("voip".equalsIgnoreCase(type) || "nonFixedVoip".equalsIgnoreCase(type)) {
                    reasons.add("PHONE_IS_VOIP");
                }

                if ("landline".equalsIgnoreCase(type)) {
                    reasons.add("PHONE_IS_LANDLINE");
                }

                if (carrierName == null || carrierName.trim().isEmpty()) {
                    reasons.add("UNKNOWN_CARRIER");
                }

                if (carrierName != null && (carrierName.toLowerCase().contains("twilio") || carrierName.toLowerCase().contains("bandwidth") || carrierName.toLowerCase().contains("vonage"))) {
                    reasons.add("PHONE_FROM_API_PLATFORM");
                }
                if ("mobile".equalsIgnoreCase(type) && carrierName != null) {
                    String carrierLower = carrierName.toLowerCase();
                    if (carrierLower.contains("vivo") ||
                            carrierLower.contains("claro") ||
                            carrierLower.contains("tim") ||
                            carrierLower.contains("oi") ||
                            carrierLower.contains("algar")) {

                        reasons.add("PHONE_IS_MOBILE_BR_CARRIER");
                    }
                }
            }


        } catch (
                ApiException e) {
            if (e.getStatusCode() == 404) {
                reasons.add("PHONE_NUMBER_NOT_EXIST");
            } else {
                System.err.println("Erro Twilio API: " + e.getMessage());
            }
        } catch (
                Exception e) {
            System.err.println("Erro Genérico Twilio: " + e.getMessage());
        }

        return reasons;
    }
}