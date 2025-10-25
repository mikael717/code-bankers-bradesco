package br.com.bradesco.codebankers.scam_api.util;

import java.net.IDN;
import java.net.URI;
import java.net.URISyntaxException;

public final class NormalizerUtil {
    private NormalizerUtil(){}

    public static String normalizeEmail(String raw){
        if (raw == null) return null;
        return raw.trim().toLowerCase();
    }

    public static String normalizePhone(String raw){
        if (raw == null) return null;
        String digits = raw.replaceAll("\\D+","");//mantem apenas digitos
        return digits;
    }
    public static String normalizeUrl(String raw){
        if (raw == null) return null;
        String treated = raw.trim();

        if(!treated.matches("(?i)^https?://.*")) treated = "http://" + treated; //caso venha sem esquema, suponho http
        try{
            URI uri = new URI(treated);
            String host = uri.getHost();
            if(host == null) return treated.toLowerCase();

            //punycode + lower + remove porta default
            String asciiHost = IDN.toASCII(host).toLowerCase();
            int port = uri.getPort();
            boolean defaultPort = (port == -1)  || (port == 80 && "http".equalsIgnoreCase(uri.getScheme()))
                                                || (port == 443 && "https".equalsIgnoreCase(uri.getScheme()));
            String normalized = (defaultPort)
                    ? uri.getScheme() + "://" + asciiHost + (uri.getRawPath() == null ? "" : uri.getRawPath())
                    : uri.getScheme() + "://" + asciiHost + ":" + port + (uri.getRawPath() == null ? "" : uri.getRawPath());
            return normalized;

        }catch (URISyntaxException e){
            return raw.toLowerCase(); //deixa como veio
        }
    }

}
