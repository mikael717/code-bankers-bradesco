package br.com.bradesco.codebankers.scam_api.controller;

import br.com.bradesco.codebankers.scam_api.domain.blacklist.BlacklistItem;
import br.com.bradesco.codebankers.scam_api.domain.blacklist.BlacklistRepository;
import br.com.bradesco.codebankers.scam_api.dto.BlacklistAddRequest;
import br.com.bradesco.codebankers.scam_api.service.scheduler.ProconScraperService;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private ProconScraperService scraperService;

    @Autowired
    private BlacklistRepository blacklistRepository;

    @GetMapping("/sync-procon")
    public ResponseEntity<String> forceProconSync() {
        int count = scraperService.executeScraping();
        if (count >= 0) {
            return ResponseEntity.ok("Sincronização realizada com sucesso! Novos sites: " + count);
        } else {
            return ResponseEntity.internalServerError().body("Erro ao conectar no feed.");
        }
    }

    @PostMapping("/blacklist")
    public ResponseEntity<String> addToBlacklist(@RequestBody @Valid BlacklistAddRequest request) {
        String normalizedValue = switch (request.itemType()) {
            case EMAIL -> NormalizerUtil.normalizeEmail(request.itemValue());
            case PHONE -> NormalizerUtil.normalizePhone(request.itemValue());
            case URL -> NormalizerUtil.normalizeUrl(request.itemValue());
        };

        if (normalizedValue == null || normalizedValue.isBlank()) {
            return ResponseEntity.badRequest().body("Valor inválido para o tipo selecionado.");
        }

        if (blacklistRepository.existsByItemTypeAndItemValue(request.itemType(), normalizedValue)) {
            return ResponseEntity.status(409).body("Este item já está na Blacklist.");
        }

        BlacklistItem item = new BlacklistItem(
                null,
                request.itemType(),
                normalizedValue,
                request.source() != null ? request.source() : "MANUAL_ADMIN"
        );

        blacklistRepository.save(item);

        return ResponseEntity.ok("Item adicionado à Blacklist com sucesso: " + normalizedValue);
    }
}