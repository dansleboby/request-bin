package io.graversen.requestbin.util;

import io.graversen.trunk.network.IpAddressUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class SpringUtil
{
    private static final IpAddressUtils ipAddressUtils = new IpAddressUtils();

    private SpringUtil()
    {

    }

    public static Map<String, String> extractHeaders(HttpServletRequest httpServletRequest)
    {
        return Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, httpServletRequest::getHeader));
    }

    public static String getIpAddress(HttpServletRequest httpServletRequest)
    {
        String clientIpAddress = ipAddressUtils.getClientIpAddress(extractHeaders(httpServletRequest));

        if (clientIpAddress.equalsIgnoreCase("unknown"))
        {
            clientIpAddress = httpServletRequest.getRemoteAddr();
        }

        return clientIpAddress;
    }
}
