package io.graversen.requestbin.services;

import io.graversen.requestbin.models.service.CreateBin;
import io.graversen.requestbin.models.service.CreateBinResult;

public interface IBinService
{
    CreateBinResult createNewBin(CreateBin createBin);
}
