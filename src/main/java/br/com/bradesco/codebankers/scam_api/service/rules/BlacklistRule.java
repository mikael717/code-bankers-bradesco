package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.domain.blacklist.BlacklistRepository;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
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

        final String rawValue = request.itemValue();
        final ItemType itemType = request.itemType();

        final String normalizedValue = switch (itemType){
            case EMAIL -> NormalizerUtil.normalizeEmail(rawValue);
            case PHONE -> NormalizerUtil.normalizePhone(rawValue);
            case URL -> NormalizerUtil.normalizeUrl(rawValue);
        };

        boolean isBlacklisted = blacklistRepository.existsByItemTypeAndItemValue(itemType, normalizedValue);

        if(!isBlacklisted){
            isBlacklisted = blacklistRepository.existsByItemTypeAndItemValue(itemType, rawValue);
        }

        if(isBlacklisted) return List.of("ITEM_FOUND_IN_BLACKLIST");


        return Collections.emptyList();
    }
}
