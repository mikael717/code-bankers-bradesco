package br.com.bradesco.codebankers.scam_api.service;

import br.com.bradesco.codebankers.scam_api.config.CodebankersProperties;
import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.domain.log.Verdict;
import br.com.bradesco.codebankers.scam_api.domain.log.VerificationLog;
import br.com.bradesco.codebankers.scam_api.domain.log.VerificationLogRepository;
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

    public VerificationService(List<VerificationRule> rules, VerificationLogRepository logRespository, CodebankersProperties codebankersProperties) {
        this.rules = rules;
        this.logRespository = logRespository;
        this.codebankersProperties = codebankersProperties;
    }

    public VerificationResponse verify(VerificationRequest request){
        //execute as regras e colete os motivos.
        List<String> allReasons = rules.stream()
                .flatMap(rule -> rule.verify(request).stream())
                .collect(Collectors.toList());

        //determina o veredito final com base nos motivos;
        Verdict finalVerdict = determineVerdict(allReasons);

        //1. calcula o valor normalizado apenas para log; mantem o contrato
        final String normalizedValueForLog = switch (request.itemType()){
            case EMAIL -> NormalizerUtil.normalizeEmail(request.itemValue());
            case PHONE -> NormalizerUtil.normalizePhone(request.itemValue());
            case URL -> NormalizerUtil.normalizeUrl(request.itemValue());
        };

        //2.extrai host quando for url
        final String urlHostForLog = (request.itemType() == ItemType.URL)
                ? NormalizerUtil.normalizeUrl(request.itemValue())
                : null;

        //3.cria o log com o construtor atual
        VerificationLog verificationLog = new VerificationLog(
                null,
                request.itemType(),
                request.itemValue(),
                finalVerdict,
                String.join(",", allReasons),
                LocalDateTime.now()
        );

        //4. preenche os novos campos migration v6
        verificationLog.setNormalizedValue(normalizedValueForLog);
        verificationLog.setUrlHost(urlHostForLog);

        logRespository.save(verificationLog);

        return new VerificationResponse(finalVerdict, allReasons);
    }

    private Verdict determineVerdict(List<String> reasons) {
        if(reasons.contains("ITEM_FOUND_IN_WHITELIST")) return Verdict.SAFE;

        if(reasons.contains("ITEM_FOUND_IN_BLACKLIST")) return Verdict.BLOCK;

        if(reasons.contains("MATCH_OFFICIAL_DOMAIN") && codebankersProperties.isOfficialDomainIsSafe()) return Verdict.SAFE;

        return Verdict.REVIEW; // se nenhuma regra foi acionada, revisar;
    }
}
