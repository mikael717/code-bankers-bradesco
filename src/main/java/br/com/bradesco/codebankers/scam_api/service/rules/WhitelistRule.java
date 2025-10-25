package br.com.bradesco.codebankers.scam_api.service.rules;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.domain.whitelist.WhitelistRepository;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
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

        //1.normaliza conforme o tipo
        final String rawValue = request.itemValue();
        final ItemType itemType = request.itemType();

        final String normalizedValue = switch (itemType) {
            case EMAIL -> NormalizerUtil.normalizeEmail(rawValue);
            case PHONE -> NormalizerUtil.normalizePhone(rawValue);
            case URL ->  NormalizerUtil.normalizeUrl(rawValue);
        };

        //2.valida primeiro o valor normalizado
        boolean isWhitelisted = whitelistRepository.existsByItemTypeAndItemValue(itemType, normalizedValue);

        //3.tbm valida o valor bruto(dados legados)
        if(!isWhitelisted){
            isWhitelisted = whitelistRepository.existsByItemTypeAndItemValue(itemType, rawValue);
        }

        if(isWhitelisted) return List.of("ITEM_FOUND_IN_WHITELIST");

        return Collections.emptyList();
    }
}
