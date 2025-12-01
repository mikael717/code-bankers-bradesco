package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.config.CodebankersProperties;
import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.dto.external.GoogleSafeBrowsingPacket;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UrlReputationRule implements VerificationRule {

    private final CodebankersProperties properties;
    private final RestTemplate restTemplate;

    public UrlReputationRule(CodebankersProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<String> verify(VerificationRequest request) {
        if (request.itemType() != ItemType.URL) {
            return Collections.emptyList();
        }

        List<String> reasons = new ArrayList<>();
        String urlToCheck = NormalizerUtil.normalizeUrl(request.itemValue());

        try {
            String endpoint = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=" + properties.getGoogleApiKey();

            GoogleSafeBrowsingPacket.ThreatInfo threatInfo = new GoogleSafeBrowsingPacket.ThreatInfo(
                    List.of("MALWARE", "SOCIAL_ENGINEERING", "UNWANTED_SOFTWARE"),
                    List.of("ANY_PLATFORM"),
                    List.of("URL"),
                    List.of(new GoogleSafeBrowsingPacket.ThreatEntry(urlToCheck))
            );

            GoogleSafeBrowsingPacket.ClientInfo clientInfo = new GoogleSafeBrowsingPacket.ClientInfo("codebankers-api", "1.0.0");
            GoogleSafeBrowsingPacket.Request googleRequest = new GoogleSafeBrowsingPacket.Request(clientInfo, threatInfo);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<GoogleSafeBrowsingPacket.Request> entity = new HttpEntity<>(googleRequest, headers);

            GoogleSafeBrowsingPacket.Response response = restTemplate.postForObject(
                    endpoint,
                    entity,
                    GoogleSafeBrowsingPacket.Response.class
            );

            if (response != null && response.matches() != null && !response.matches().isEmpty()) {
                for (GoogleSafeBrowsingPacket.Match match : response.matches()) {

                    if ("SOCIAL_ENGINEERING".equals(match.threatType())) {
                        reasons.add("URL_PHISHING_DETECTED");
                    } else if ("MALWARE".equals(match.threatType())) {
                        reasons.add("URL_MALWARE_DETECTED");
                    } else {
                        reasons.add("URL_THREAT_DETECTED");
                    }
                }
            } else {
            }

        } catch (Exception e) {
            System.err.println("Erro Google Safe Browsing: " + e.getMessage());
            e.printStackTrace();
        }

        return reasons;
    }
}