package io.graversen.requestbin.models.service;

import io.graversen.requestbin.models.etc.CreateBinStatus;
import io.graversen.requestbin.models.service.Bin;

public class CreateBinResult
{
    private final Bin bin;
    private final CreateBinStatus createBinStatus;

    public CreateBinResult(Bin bin, CreateBinStatus createBinStatus)
    {
        this.bin = bin;
        this.createBinStatus = createBinStatus;
    }
}
