package br.com.bradesco.codebankers.scam_api.service;

import br.com.bradesco.codebankers.scam_api.config.CodebankersProperties;
import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.domain.log.Verdict;
import br.com.bradesco.codebankers.scam_api.domain.log.VerificationLog;
import br.com.bradesco.codebankers.scam_api.domain.log.VerificationLogRepository;
import br.com.bradesco.codebankers.scam_api.domain.report.ReportRepository; // <--- IMPORTANTE
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.dto.VerificationResponse;
import br.com.bradesco.codebankers.scam_api.service.rules.VerificationRule;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VerificationService {

    @Autowired
    private List<VerificationRule> rules;
    @Autowired
    private VerificationLogRepository logRespository;
    @Autowired
    private CodebankersProperties codebankersProperties;
    @Autowired
    private ReportRepository reportRepository; // <--- ADICIONE ISSO AQUI PARA RESOLVER O PRIMEIRO ERRO

    // Se você usa construtor, atualize ele também (ou deixe o @Autowired cuidar disso se preferir)
    public VerificationService(List<VerificationRule> rules,
                               VerificationLogRepository logRespository,
                               CodebankersProperties codebankersProperties,
                               ReportRepository reportRepository) {
        this.rules = rules;
        this.logRespository = logRespository;
        this.codebankersProperties = codebankersProperties;
        this.reportRepository = reportRepository;
    }

    public VerificationResponse verify(VerificationRequest request) {
        // 1. Executa regras
        List<String> allReasons = rules.stream()
                .flatMap(rule -> rule.verify(request).stream())
                .collect(Collectors.toList());

        // 2. Calcula Score (AQUI ESTAVA O ERRO DE ARGUMENTOS)
        // Agora passamos a lista, o tipo e o valor para ele consultar o banco de report
        int score = calculateRiskScore(allReasons, request.itemType(), request.itemValue());

        Verdict finalVerdict = determineVerdictByScore(score, allReasons);

        // Whitelist zera o score
        if (allReasons.contains("ITEM_FOUND_IN_WHITELIST") ||
                (allReasons.contains("MATCH_OFFICIAL_DOMAIN") && codebankersProperties.isOfficialDomainIsSafe())) {
            score = 0;
            finalVerdict = Verdict.SAFE;
        }

        // 3. Normalização para logs
        final String normalizedValueForLog = switch (request.itemType()) {
            case EMAIL -> NormalizerUtil.normalizeEmail(request.itemValue());
            case PHONE -> NormalizerUtil.normalizePhone(request.itemValue());
            case URL -> NormalizerUtil.normalizeUrl(request.itemValue());
        };

        final String urlHostForLog = (request.itemType() == ItemType.URL)
                ? NormalizerUtil.normalizeUrl(request.itemValue())
                : null;

        // 4. Salva log
        VerificationLog verificationLog = new VerificationLog(
                null,
                request.itemType(),
                request.itemValue(),
                finalVerdict,
                String.join(",", allReasons),
                LocalDateTime.now(),
                score
        );
        verificationLog.setNormalizedValue(normalizedValueForLog);
        verificationLog.setUrlHost(urlHostForLog);
        logRespository.save(verificationLog);

        return new VerificationResponse(
                finalVerdict,
                allReasons,
                request.itemType(),
                request.itemValue(),
                score
        );
    }

    private int calculateRiskScore(List<String> reasons, ItemType type, String value) {
        int score = 0;
        if (reasons.contains("ITEM_FOUND_IN_BLACKLIST")) return 100;

        // REGRAS DE URL
        if (reasons.contains("URL_PHISHING_DETECTED") ||
                reasons.contains("URL_MALWARE_DETECTED")) return 100;

        if (reasons.contains("PHONE_IS_VOIP")) score += 60;

        // REGRAS DE TELEFONE
        if (reasons.contains("PHONE_IS_VOIP")) score += 60;
        if (reasons.contains("PHONE_NUMBER_NOT_EXIST")) score += 20;
        if (reasons.contains("UNKNOWN_CARRIER")) score += 10;
        if (reasons.contains("PHONE_IS_LANDLINE")) score += 15;

        // REGRAS DE EMAIL
        if (reasons.contains("EMAIL_IS_DISPOSABLE")) score += 35;
        if (reasons.contains("EMAIL_ADDRESS_NOT_EXIST")) score += 20;
        if (reasons.contains("DOMAIN_CREATED_RECENTLY")) score += 25;
        if (reasons.contains("LOW_REPUTATION_SCORE")) score += 15;
        if (reasons.contains("INVALID_EMAIL_SYNTAX") ||
                reasons.contains("INVALID_PHONE_SYNTAX") ||
                reasons.contains("INVALID_URL_SYNTAX")) score += 5;

        // Crowdsourcing: Consulta quantas denúncias existem
        String normalized = switch (type) {
            case EMAIL -> NormalizerUtil.normalizeEmail(value);
            case PHONE -> NormalizerUtil.normalizePhone(value);
            case URL -> NormalizerUtil.normalizeUrl(value);
        };

        // Conta denúncias e soma 3 pontos cada
        long reportCount = reportRepository.countByItemTypeAndItemValue(type, normalized);
        score += (int) (reportCount * 3);

        return Math.min(score, 99);
    }

    private Verdict determineVerdictByScore(int score, List<String> reasons) {
        if (score == 0) return Verdict.SAFE;
        if (score <= 20) return Verdict.LOW_RISK;
        if (score <= 60) return Verdict.MEDIUM_RISK;
        if (score < 100) return Verdict.HIGH_RISK;
        return Verdict.CONFIRMED_SCAM;
    }
}