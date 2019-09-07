package io.graversen.requestbin.models.service;

public class CreateBin
{
    private String ipAddress;
    private String userAgent;

    public CreateBin(String ipAddress, String userAgent)
    {
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public String getUserAgent()
    {
        return userAgent;
    }
}
