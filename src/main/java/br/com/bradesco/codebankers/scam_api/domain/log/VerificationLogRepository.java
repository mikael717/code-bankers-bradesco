package br.com.bradesco.codebankers.scam_api.domain.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VerificationLogRepository extends JpaRepository<VerificationLog, Long> {

    @Query("""
            select v.verdict as verdict, count(v) as count
            from VerificationLog v
            where v.verificationDate between :from and :to
            group by v.verdict
            """)
    List<CountByVerdict> countByVerdictBetween(@Param("from")LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("""
            select v.itemType as itemType, count(v) as count
            from VerificationLog v
            where v.verificationDate between :from and :to
            group by v.itemType
            """)
    List<CountByItemType> countByItemTypeBetween(@Param("from")LocalDateTime from, @Param("to") LocalDateTime to);
}
