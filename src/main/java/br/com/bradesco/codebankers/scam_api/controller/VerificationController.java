package br.com.bradesco.codebankers.scam_api.controller;

import br.com.bradesco.codebankers.scam_api.dto.VerificationRequest;
import br.com.bradesco.codebankers.scam_api.dto.VerificationResponse;
import br.com.bradesco.codebankers.scam_api.service.VerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("verify")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @PostMapping
    public ResponseEntity<VerificationResponse> verify(@RequestBody @Valid VerificationRequest request){
        var response = verificationService.verify(request);
        return ResponseEntity.ok(response);
    }
}
