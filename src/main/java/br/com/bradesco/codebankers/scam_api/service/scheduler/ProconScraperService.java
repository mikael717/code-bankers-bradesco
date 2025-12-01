package br.com.bradesco.codebankers.scam_api.service.scheduler;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import br.com.bradesco.codebankers.scam_api.domain.blacklist.BlacklistItem;
import br.com.bradesco.codebankers.scam_api.domain.blacklist.BlacklistRepository;
import br.com.bradesco.codebankers.scam_api.util.NormalizerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class ProconScraperService {

    @Autowired
    private BlacklistRepository blacklistRepository;

    private static final String FEED_URL = "https://openphish.com/feed.txt";

    @Scheduled(cron = "0 0 4 * * MON")
    public void runScheduledScraping() {
        executeScraping();
    }

    public int executeScraping() {
        int novosItens = 0;
        int duplicados = 0;

        try {
            RestTemplate restTemplate = new RestTemplate();
            String feedContent = restTemplate.getForObject(FEED_URL, String.class);

            if (feedContent == null || feedContent.isEmpty()) {
                System.err.println("❌ [SCRAPER] O feed veio vazio.");
                return 0;
            }

            String[] urls = feedContent.split("\\r?\\n");

            for (String rawUrl : urls) {

                String normalizedUrl = NormalizerUtil.normalizeUrl(rawUrl);

                if (normalizedUrl != null && !normalizedUrl.isBlank()) {

                    if (!blacklistRepository.existsByItemTypeAndItemValue(ItemType.URL, normalizedUrl)) {

                        BlacklistItem item = new BlacklistItem(
                                null,
                                ItemType.URL,
                                normalizedSite(normalizedUrl),
                                "OPENPHISH_FEED"
                        );

                        blacklistRepository.save(item);
                        novosItens++;
                    } else {
                        duplicados++;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("❌ [SCRAPER] Erro ao processar feed: " + e.getMessage());
            return -1;
        }

        return novosItens;
    }

    private String normalizedSite(String url) {
        if (url.length() > 250) {
            return url.substring(0, 250);
        }
        return url;
    }
}