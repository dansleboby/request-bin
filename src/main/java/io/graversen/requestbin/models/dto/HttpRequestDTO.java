package io.graversen.requestbin.models.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class HttpRequestDTO
{
    private LocalDateTime createdAt;
    private String requestBody;
    private Map<String, String> queryParameters;
    private Map<String, String> httpHeaders;
    private String ipAddress;

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getRequestBody()
    {
        return requestBody;
    }

    public void setRequestBody(String requestBody)
    {
        this.requestBody = requestBody;
    }

    public Map<String, String> getQueryParameters()
    {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, String> queryParameters)
    {
        this.queryParameters = queryParameters;
    }

    public Map<String, String> getHttpHeaders()
    {
        return httpHeaders;
    }

    public void setHttpHeaders(Map<String, String> httpHeaders)
    {
        this.httpHeaders = httpHeaders;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }
}
