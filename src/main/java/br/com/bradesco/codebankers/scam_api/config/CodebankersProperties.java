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
}
