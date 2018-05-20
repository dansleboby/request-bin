package io.graversen.requestbin.controllers;

import io.graversen.requestbin.models.etc.CreateBinStatus;
import io.graversen.requestbin.models.service.CreateBin;
import io.graversen.requestbin.models.service.CreateBinResult;
import io.graversen.requestbin.services.IBinService;
import io.graversen.trunk.network.IpAddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BinController
{
    private final IBinService binService;
    private final IpAddressUtils ipAddressUtils;

    @Autowired
    public BinController(IBinService binService, IpAddressUtils ipAddressUtils)
    {
        this.binService = binService;
        this.ipAddressUtils = ipAddressUtils;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String newBin(HttpServletRequest httpServletRequest, Model model)
    {
        final Map<String, String> httpHeaders = Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, httpServletRequest::getHeader));

        final String clientIp = ipAddressUtils.getClientIpAddress(httpHeaders);
        final String userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);

        final CreateBin createBin = new CreateBin(clientIp, userAgent);
        final CreateBinResult newBin = binService.createNewBin(createBin);

        if (newBin.getCreateBinStatus().equals(CreateBinStatus.SUCCESS))
        {
            return newBin.getBin().getIdentifier();
        }
        else
        {
            // Return forbidden or something
        }

        return "";
    }

    @RequestMapping(value = "{binIdentifier}", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
    public ResponseEntity<Void> httpBin(@PathVariable String binIdentifier, @RequestBody(required = false) String requestBody, @RequestParam(required = false) Map<String, String> requestParams, HttpServletRequest httpServletRequest)
    {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "{binIdentifier}/inspect", method = RequestMethod.GET)
    public String httpBinInspect(@PathVariable String binIdentifier, Model model)
    {
        return "";
    }
}
