package br.com.bradesco.codebankers.scam_api.domain.report;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    long countByItemTypeAndItemValue(ItemType itemType, String itemValue);
}