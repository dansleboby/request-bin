package io.graversen.requestbin.models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
public class ClientsEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "identifier", unique = true, nullable = false, length = 15)
    private String ipAddress;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "blocked")
    private Boolean blocked;

    public ClientsEntity()
    {
    }

    public ClientsEntity(String ipAddress)
    {
        this.ipAddress = ipAddress;
        this.createdAt = LocalDateTime.now();
        this.blocked = false;
    }

    public Long getId()
    {
        return id;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public Boolean getBlocked()
    {
        return blocked;
    }
}
