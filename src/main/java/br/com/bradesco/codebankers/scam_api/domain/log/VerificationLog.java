package br.com.bradesco.codebankers.scam_api.domain.log;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity(name = "VerificationLog")
@Table(name = "verification_logs")
@EqualsAndHashCode(of = "id")
public class VerificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @Column(name = "item_value")
    private String itemValue;

    @Enumerated(EnumType.STRING)
    private Verdict verdict;

    private String reasons;

    @Column(name = "verification_date")
    private LocalDateTime verificationDate;

    @Column(name = "normalized_value")
    private String normalizedValue;

    @Column(name = "url_host")
    private String urlHost;

    @Column(name = "risk_score")
    private int riskScore;

    public VerificationLog() {
    }

    public VerificationLog(Long id, ItemType itemType, String itemValue, Verdict verdict, String reasons, LocalDateTime verificationDate, int riskScore) {
        this.id = id;
        this.itemType = itemType;
        this.itemValue = itemValue;
        this.verdict = verdict;
        this.reasons = reasons;
        this.verificationDate = verificationDate;
        this.riskScore = riskScore;
    }
    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public Long getId() {
        return id;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getItemValue() {
        return itemValue;
    }

    public Verdict getVerdict() {
        return verdict;
    }

    public String getReasons() {
        return reasons;
    }

    public LocalDateTime getVerificationDate() {
        return verificationDate;
    }

    public String getNormalizedValue() {
        return normalizedValue;
    }

    public void setNormalizedValue(String normalizedValue) {
        this.normalizedValue = normalizedValue;
    }

    public String getUrlHost() {
        return urlHost;
    }

    public void setUrlHost(String urlHost) {
        this.urlHost = urlHost;
    }
}