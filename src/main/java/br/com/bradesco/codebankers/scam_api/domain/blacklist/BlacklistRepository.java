package br.com.bradesco.codebankers.scam_api.domain.blacklist;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<BlacklistItem, Long> {

    boolean existsByItemTypeAndItemValue(ItemType itemType, String itemValue);
}
