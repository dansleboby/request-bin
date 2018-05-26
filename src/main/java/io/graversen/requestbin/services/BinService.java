package io.graversen.requestbin.services;

import io.graversen.requestbin.models.entities.BinsEntity;
import io.graversen.requestbin.models.etc.CreateBinStatus;
import io.graversen.requestbin.models.service.Bin;
import io.graversen.requestbin.models.service.CreateBin;
import io.graversen.requestbin.models.service.CreateBinResult;
import io.graversen.requestbin.repositories.IBinsRepository;
import io.graversen.requestbin.util.BinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BinService implements IBinService
{
    private final IBinsRepository binRepository;

    @Autowired
    public BinService(IBinsRepository binRepository)
    {
        this.binRepository = binRepository;
    }

    @Override
    public CreateBinResult createNewBin(CreateBin createBin)
    {
        final String identifier = BinUtil.generateBinIdentifier();
        final BinsEntity binEntity = binRepository.save(new BinsEntity(identifier));

        return new CreateBinResult(new Bin(binEntity.getId(), binEntity.getIdentifier(), binEntity.getCreatedAt(), binEntity.getDiscardedAt()), CreateBinStatus.SUCCESS);
    }

    @Override
    public Optional<Bin> getBin(String binIdentifier)
    {
        final Optional<BinsEntity> binsEntityOptional = binRepository.findByIdentifier(binIdentifier);
        return binsEntityOptional.map(b -> new Bin(b.getId(), b.getIdentifier(), b.getCreatedAt(), b.getDiscardedAt()));
    }
}
