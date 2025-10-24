package br.com.bradesco.codebankers.scam_api.service;

import br.com.bradesco.codebankers.scam_api.domain.log.Verdict;
import br.com.bradesco.codebankers.scam_api.domain.log.VerificationLog;
import br.com.bradesco.codebankers.scam_api.domain.log.VerificationLogRepository;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.dto.VerificationResponse;
import br.com.bradesco.codebankers.scam_api.service.rules.VerificationRule;
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

    public VerificationResponse verify(VerificationRequest request){
        //execute as regras e colete os motivos.
        List<String> allReasons = rules.stream()
                .flatMap(rule -> rule.verify(request).stream())
                .collect(Collectors.toList());

        //determina o veredito final com base nos motivos;
        Verdict finalVerdict = determineVerdict(allReasons);

        logRespository.save(new VerificationLog(
                null,
                request.itemType(),
                request.itemValue(),
                finalVerdict,
                String.join(",", allReasons), //salva motivos como "REASON1,REASON2"
                LocalDateTime.now()
        ));

        return new VerificationResponse(finalVerdict, allReasons);
    }

    private Verdict determineVerdict(List<String> reasons) {
        if(reasons.contains("ITEM_FOUND_IN_WHITELIST")) return Verdict.SAFE;

        if(reasons.contains("ITEM_FOUND_IN_BLACKLIST")) return Verdict.BLOCK;

        return Verdict.REVIEW; // se nenhuma regra foi acionada, revisar;
    }
}
