package br.com.bradesco.codebankers.scam_api.domain.log;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationLogRepository extends JpaRepository<VerificationLog, Long> {
}
