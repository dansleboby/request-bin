package io.graversen.requestbin.models.service;

import io.graversen.requestbin.models.etc.CreateBinStatus;

public class CreateBinResult
{
    private final Bin bin;
    private final CreateBinStatus createBinStatus;

    public CreateBinResult(Bin bin, CreateBinStatus createBinStatus)
    {
        this.bin = bin;
        this.createBinStatus = createBinStatus;
    }

    public Bin getBin()
    {
        return bin;
    }

    public CreateBinStatus getCreateBinStatus()
    {
        return createBinStatus;
    }
}
