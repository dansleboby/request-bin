package io.graversen.requestbin.models.service;

import java.time.LocalDateTime;

public class Bin
{
    private Long id;
    private String identifier;
    private LocalDateTime createdAt;
    private LocalDateTime discardedAt;

    public Bin(Long id, String identifier, LocalDateTime createdAt, LocalDateTime discardedAt)
    {
        this.id = id;
        this.identifier = identifier;
        this.createdAt = createdAt;
        this.discardedAt = discardedAt;
    }

    public Long getId()
    {
        return id;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public LocalDateTime getDiscardedAt()
    {
        return discardedAt;
    }
}
