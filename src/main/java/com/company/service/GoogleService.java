package com.company.service;

import com.company.exception.AppException;
import com.company.model.GoogleTimeZone;
import com.company.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.CompletableFuture;

@Service
public class GoogleService {

    @Value("${map.url}")
    private String mapUrl;

    @Value("${map.key}")
    private String mapKey;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(GoogleService.class);

    public CompletableFuture<String> getLocalTime(final Location location) throws Exception {
        final String googleUrl = getGoogleUrl(location);
        return CompletableFuture.supplyAsync(() -> restTemplate.getForEntity(googleUrl, GoogleTimeZone.class))
                .thenApply(timeZone -> timeZone.getBody().getTimeZoneId())
                .exceptionally(e -> {
                    logger.error("Failed to connection to google maps");
                    throw new AppException("101", "Failed to connection to google maps");
                });
    }

    private String getGoogleUrl(final Location location) throws Exception {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Long utcTime = dateFormat.parse(location.getUtcTime()).getTime();
        return MessageFormat.format(mapUrl + mapKey,
                location.getLatitude() + "," + location.getLongitude(), String.valueOf(utcTime / 1000l));
    }
}
