package br.com.bradesco.codebankers.scam_api.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GoogleSafeBrowsingPacket {

    public record Request(
            ClientInfo client,
            ThreatInfo threatInfo
    ) {
    }

    public record ClientInfo(
            String clientId,
            String clientVersion
    ) {
    }

    public record ThreatInfo(
            List<String> threatTypes,
            List<String> platformTypes,
            List<String> threatEntryTypes,
            List<ThreatEntry> threatEntries
    ) {
    }

    public record ThreatEntry(
            String url
    ) {
    }

    public record Response(
            List<Match> matches
    ) {
    }

    public record Match(
            String threatType,
            String platformType,
            @JsonProperty("threatEntry") ThreatEntry entry
    ) {
    }
}