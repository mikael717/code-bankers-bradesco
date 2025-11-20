package br.com.bradesco.codebankers.scam_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds tunable settings for Codebankers.
 * officialDomains: list of base domains that are considered official.
 * officialDomainIsSafe: whether a match should be treated as SAFE (default false).
 */

@Component
@ConfigurationProperties(prefix = "codebankers")
public class CodebankersProperties {

    private List<String> officialDomains = new ArrayList<>();
    private boolean officialDomainIsSafe = false;
    private String abstractApiKey;
    private String twilioAccountSid;
    private String twilioAuthToken;
    private String googleApiKey;

    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public void setGoogleApiKey(String googleApiKey) {
        this.googleApiKey = googleApiKey;
    }

    public List<String> getOfficialDomains() {
        return officialDomains;
    }

    public void setOfficialDomains(List<String> officialDomains) {
        this.officialDomains = officialDomains;
    }

    public boolean isOfficialDomainIsSafe() {
        return officialDomainIsSafe;
    }

    public void setOfficialDomainIsSafe(boolean officialDomainIsSafe) {
        this.officialDomainIsSafe = officialDomainIsSafe;
    }

    public String getAbstractApiKey() {
        return abstractApiKey;
    }

    public void setAbstractApiKey(String abstractApiKey) {
        this.abstractApiKey = abstractApiKey;
    }

    public String getTwilioAccountSid() {
        return twilioAccountSid;
    }

    public void setTwilioAccountSid(String twilioAccountSid) {
        this.twilioAccountSid = twilioAccountSid;
    }

    public String getTwilioAuthToken() {
        return twilioAuthToken;
    }

    public void setTwilioAuthToken(String twilioAuthToken) {
        this.twilioAuthToken = twilioAuthToken;
    }
}