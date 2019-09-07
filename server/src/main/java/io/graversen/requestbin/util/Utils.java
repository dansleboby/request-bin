package io.graversen.requestbin.util;

import io.graversen.trunk.network.IpAddressUtils;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class Utils {
    private static final IpAddressUtils IP_ADDRESS_UTILS = new IpAddressUtils();
    private static final Duration DEFAULT_BIN_EXPIRY = Duration.ofDays(1);

    public static String binId() {
        return UUID.randomUUID().toString();
    }

    public static LocalDateTime defaultBinExpiry() {
        return LocalDateTime.now().plus(DEFAULT_BIN_EXPIRY);
    }

    public static String generateCreatedByString(String ipAddress, String userAgent) {
        return ipAddress + "/" + userAgent;
    }

    public static String getIpAddress(ServerHttpRequest serverHttpRequest) {
        final var httpHeaders = serverHttpRequest.getHeaders().toSingleValueMap();

        final String clientIpAddress = IP_ADDRESS_UTILS.getClientIpAddress(httpHeaders);

        if ("unknown".equalsIgnoreCase(clientIpAddress)) {
            return "unknown";
        }

        return clientIpAddress;
    }

    public static String getUserAgent(ServerHttpRequest serverHttpRequest) {
        final var headers = serverHttpRequest.getHeaders().get(HttpHeaders.USER_AGENT);

        if (headers == null || headers.isEmpty()) {
            return "unknown";
        } else {
            return headers.get(0);
        }
    }
}
