package io.graversen.requestbin.services;

import io.graversen.requestbin.models.service.Bin;
import io.graversen.requestbin.models.service.CreateBin;
import io.graversen.requestbin.models.service.CreateBinResult;

import java.util.Optional;

public interface IBinService
{
    CreateBinResult createNewBin(CreateBin createBin);

    Optional<Bin> getBin(String binIdentifier);
}
