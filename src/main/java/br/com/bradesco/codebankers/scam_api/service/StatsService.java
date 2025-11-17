package br.com.bradesco.codebankers.scam_api.service;

import br.com.bradesco.codebankers.scam_api.domain.log.CountByItemType;
import br.com.bradesco.codebankers.scam_api.domain.log.CountByVerdict;
import br.com.bradesco.codebankers.scam_api.domain.log.VerificationLogRepository;
import br.com.bradesco.codebankers.scam_api.dto.StatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsService {

    @Autowired
    private VerificationLogRepository logRepository;

    public StatsService(VerificationLogRepository logRepository){
        this.logRepository = logRepository;
    }

    public StatsResponse getStats(LocalDateTime fromInclusive, LocalDateTime toExclusive){
        var byVerdict = logRepository.countByVerdictBetween(fromInclusive, toExclusive);
        var byType = logRepository.countByItemTypeBetween(fromInclusive, toExclusive);

        Map<String, Long> verdictCounts = byVerdict.stream()
                .collect(Collectors.toMap(CountByVerdict::getVerdict, CountByVerdict::getCount));

        Map<String, Long> typeCounts = byType.stream()
                .collect(Collectors.toMap(CountByItemType::getItemType, CountByItemType::getCount));

        long totalCount = verdictCounts.values().stream().mapToLong(Long::longValue).sum();

        return new StatsResponse(totalCount, verdictCounts, typeCounts);
    }
}
