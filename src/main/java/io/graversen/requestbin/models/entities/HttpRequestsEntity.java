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

    @Column(name = "http_verb")
    private String httpVerb;

    @Column(name = "bin_id")
    private Long binId;

    @Column(name = "request_duration")
    private Long requestDuration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bin_id", insertable = false, updatable = false)
    private BinsEntity bin;

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

    public String getHttpVerb()
    {
        return httpVerb;
    }

    public void setHttpVerb(String httpVerb)
    {
        this.httpVerb = httpVerb;
    }

    public Long getBinId()
    {
        return binId;
    }

    public void setBinId(Long binId)
    {
        this.binId = binId;
    }

    public BinsEntity getBin()
    {
        return bin;
    }

    public void setBin(BinsEntity bin)
    {
        this.bin = bin;
    }

    public Long getRequestDuration()
    {
        return requestDuration;
    }

    public void setRequestDuration(Long requestDuration)
    {
        this.requestDuration = requestDuration;
    }
}
