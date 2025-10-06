package br.com.bradesco.codebankers.scam_api.domain.whitelist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WhitelistRepository extends JpaRepository<WhitelistItem, Long> {

    boolean existsByItemValue(String itemValue);
}
