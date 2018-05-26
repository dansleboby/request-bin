package io.graversen.requestbin.models.service;

import java.util.Map;

public class CreateHttpRequest
{
    private String requestBody;
    private Map<String, String> queryParameters;
    private Map<String, String> httpHeaders;
    private String ipAddress;
    private String httpVerb;
    private Long binId;

    public CreateHttpRequest(String requestBody, Map<String, String> queryParameters, Map<String, String> httpHeaders, String ipAddress, String httpVerb, Long binId)
    {
        this.requestBody = requestBody;
        this.queryParameters = queryParameters;
        this.httpHeaders = httpHeaders;
        this.ipAddress = ipAddress;
        this.httpVerb = httpVerb;
        this.binId = binId;
    }

    public String getRequestBody()
    {
        return requestBody;
    }

    public Map<String, String> getQueryParameters()
    {
        return queryParameters;
    }

    public Map<String, String> getHttpHeaders()
    {
        return httpHeaders;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public Long getBinId()
    {
        return binId;
    }

    public String getHttpVerb()
    {
        return httpVerb;
    }
}
