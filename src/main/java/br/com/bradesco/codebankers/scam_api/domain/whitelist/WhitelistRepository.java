package br.com.bradesco.codebankers.scam_api.domain.whitelist;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhitelistRepository extends JpaRepository<WhitelistItem, Long> {

    boolean existsByItemTypeAndItemValue(ItemType itemType, String itemValue);
}
