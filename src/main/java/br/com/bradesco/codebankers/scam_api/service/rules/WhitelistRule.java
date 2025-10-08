package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.domain.whitelist.WhitelistRepository;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class WhitelistRule implements VerificationRule{

    @Autowired
    private WhitelistRepository whitelistRepository;
    @Override
    public List<String> verify(VerificationRequest request) {
        if(whitelistRepository.existsByItemValue(request.itemValue())){
            return List.of("ITEM_FOUND_IN_WHITELIST");
        }
        return Collections.emptyList();
    }
}
