package io.graversen.requestbin.util;

import io.graversen.trunk.network.IpAddressUtils;
import io.graversen.trunk.random.RandomUtils;
import io.graversen.trunk.random.RandomUtilsFactory;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@UtilityClass
public class Utils {
    public static final DateTimeFormatter HUMAN_READABLE_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private static final RandomUtils RANDOM_UTILS = RandomUtilsFactory.defaultRandomUtils();
    private static final IpAddressUtils IP_ADDRESS_UTILS = new IpAddressUtils();

    public static String clientId() {
        return RANDOM_UTILS.randomString(16, true, true, false);
    }

    public static String binId() {
        return UUID.randomUUID().toString();
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

        final String[] clientIpAddresses = clientIpAddress.split(",");
        if (clientIpAddresses.length > 1) {
            return clientIpAddresses[0];
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
