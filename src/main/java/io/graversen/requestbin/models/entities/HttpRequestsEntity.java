package io.graversen.requestbin.models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "http_requests")
public class HttpRequestsEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "query_parameters")
    private String queryParameters;

    @Column(name = "http_headers")
    private String httpHeaders;

    @Column(name = "ip")
    private String ipAddress;

    public HttpRequestsEntity()
    {
    }

    public Long getId()
    {
        return id;
    }

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

    public String getQueryParameters()
    {
        return queryParameters;
    }

    public void setQueryParameters(String queryParameters)
    {
        this.queryParameters = queryParameters;
    }

    public String getHttpHeaders()
    {
        return httpHeaders;
    }

    public void setHttpHeaders(String httpHeaders)
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
