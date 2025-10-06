package br.com.bradesco.codebankers.scam_api.domain.log;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "VerificationLog")
@Table(name = "verification_logs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    private String reasons; //motivos concatenados, e:"blacklisted, contains_keyword"

    @Column(name = "verification_date")
    private LocalDateTime verificationDate;
}
