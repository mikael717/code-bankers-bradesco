package br.com.bradesco.codebankers.scam_api.domain.report;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Entity(name = "Report")
@Table(name = "reports")
@EqualsAndHashCode(of = "id")
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @Column(name = "item_value")
    private String itemValue;

    private String reason;

    @Column(name = "reported_at")
    private LocalDateTime reportedAt;

    public Report() {}

    public Report(ItemType itemType, String itemValue, String reason) {
        this.itemType = itemType;
        this.itemValue = itemValue;
        this.reason = reason;
        this.reportedAt = LocalDateTime.now();
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

    public String getReason() {
        return reason;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }
}