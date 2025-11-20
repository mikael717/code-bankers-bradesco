package br.com.bradesco.codebankers.scam_api.controller;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.domain.report.Report;
import br.com.bradesco.codebankers.scam_api.domain.report.ReportRepository;
import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @PostMapping
    public ResponseEntity<Void> report(@RequestBody @Valid VerificationRequest request) {
        String normalizedValue = switch (request.itemType()) {
            case EMAIL -> NormalizerUtil.normalizeEmail(request.itemValue());
            case PHONE -> NormalizerUtil.normalizePhone(request.itemValue());
            case URL -> NormalizerUtil.normalizeUrl(request.itemValue());
        };

        Report report = new Report(request.itemType(), normalizedValue, "USER_REPORT");
        reportRepository.save(report);

        return ResponseEntity.ok().build();
    }
}