package io.graversen.requestbin.controllers;

import io.graversen.requestbin.models.etc.CreateBinStatus;
import io.graversen.requestbin.models.service.Bin;
import io.graversen.requestbin.models.service.CreateBin;
import io.graversen.requestbin.models.service.CreateBinResult;
import io.graversen.requestbin.models.service.HttpRequest;
import io.graversen.requestbin.services.IBinService;
import io.graversen.requestbin.services.IHttpRequestService;
import io.graversen.requestbin.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
public class BinController
{
    private final IBinService binService;
    private final IHttpRequestService httpRequestService;
    private final Base64.Encoder base64Encoder;

    @Autowired
    public BinController(IBinService binService, IHttpRequestService httpRequestService)
    {
        this.binService = binService;
        this.httpRequestService = httpRequestService;
        this.base64Encoder = Base64.getEncoder();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView newBin(HttpServletRequest httpServletRequest, Model model)
    {
        final String clientIp = SpringUtil.getIpAddress(httpServletRequest);
        final String userAgent = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);

        final CreateBin createBin = new CreateBin(clientIp, userAgent);
        final CreateBinResult newBin = binService.createNewBin(createBin);

        if (newBin.getCreateBinStatus().equals(CreateBinStatus.SUCCESS))
        {
            return new ModelAndView(String.format("redirect:/%s/inspect", newBin.getBin().getIdentifier()));
        }
        else
        {
            return new ModelAndView("", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "{binIdentifier}", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS})
    public ResponseEntity<Void> httpBin(@PathVariable String binIdentifier, @RequestBody(required = false) String requestBody, @RequestParam(required = false) Map<String, String> requestParams, HttpServletRequest httpServletRequest)
    {
        final long requestStart = System.currentTimeMillis();
        final Optional<Bin> binOptional = binService.getBin(binIdentifier);

        if (binOptional.isPresent())
        {
            final Bin bin = binOptional.get();

            if (bin.getDiscardedAt() != null)
            {
                return ResponseEntity.status(HttpStatus.GONE).build();
            }
        }
        else
        {
            return ResponseEntity.notFound().build();
        }

        final Map<String, String> httpHeaders = SpringUtil.extractHeaders(httpServletRequest);
        final String clientIp = SpringUtil.getIpAddress(httpServletRequest);
        final String httpVerb = httpServletRequest.getMethod();
        final LocalDateTime now = LocalDateTime.now();

        final long requestDuration = System.currentTimeMillis() - requestStart;
        final HttpRequest httpRequest = new HttpRequest(base64Encoder.encodeToString(requestBody.getBytes()), requestParams, httpHeaders, clientIp, httpVerb, now, requestDuration);

        httpRequestService.emitHttpRequest(binIdentifier, Collections.singleton(httpRequest));

//        final CreateHttpRequest createHttpRequest = new CreateHttpRequest(requestBody, requestParams, httpHeaders, clientIp, httpVerb, binOptional.get().getId(), now);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "{binIdentifier}/inspect", method = RequestMethod.GET)
    public ModelAndView httpBinInspect(@PathVariable String binIdentifier, Model model)
    {
        model.addAttribute("binIdentifier", binIdentifier);
        return new ModelAndView("inspect", "model", model);
    }
}
