package io.graversen.requestbin.models.service;

import java.util.Map;

public class HttpRequest
{
    private String requestBody;
    private Map<String, String> queryParameters;
    private Map<String, String> httpHeaders;
    private String ipAddress;
    private String httpVerb;

    public HttpRequest(String requestBody, Map<String, String> queryParameters, Map<String, String> httpHeaders, String ipAddress, String httpVerb)
    {
        this.requestBody = requestBody;
        this.queryParameters = queryParameters;
        this.httpHeaders = httpHeaders;
        this.ipAddress = ipAddress;
        this.httpVerb = httpVerb;
    }
}
