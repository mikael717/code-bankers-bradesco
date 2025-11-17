package br.com.bradesco.codebankers.scam_api.controller;

import br.com.bradesco.codebankers.scam_api.dto.StatsResponse;
import br.com.bradesco.codebankers.scam_api.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
    public StatsResponse getStats(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to
    ) {
        LocalDateTime toExclusive = (to == null) ? LocalDateTime.now() : to;
        LocalDateTime fromInclusive = (from == null) ? toExclusive.minusDays(30) : from;

        if (fromInclusive.isAfter(toExclusive)) {
            var temp = fromInclusive;
            fromInclusive = toExclusive;
            toExclusive = temp;
        }

        return statsService.getStats(fromInclusive, toExclusive);

    }
}
