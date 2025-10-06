package br.com.bradesco.codebankers.scam_api.domain.blacklist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<BlacklistItem, Long> {

    boolean existsByItemValue(String itemValue);
}
