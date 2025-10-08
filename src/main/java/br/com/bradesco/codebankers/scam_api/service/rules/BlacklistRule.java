package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.domain.blacklist.BlacklistRepository;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BlacklistRule implements VerificationRule{

    @Autowired
    private BlacklistRepository blacklistRepository;

    @Override
    public List<String> verify(VerificationRequest request) {
        if(blacklistRepository.existsByItemValue(request.itemValue())){
            return List.of("ITEM_FOUND_IN_BLACKLIST");
        }
        return Collections.emptyList();
    }
}
