package io.graversen.requestbin.data.mysql;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "request_bin")
public class RequestBinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "bin_id", nullable = false, unique = true)
    private String binId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @NonNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @NonNull
    @Column(name = "source", nullable = false)
    private String source;

    @NonNull
    @Column(name = "open", nullable = false)
    private boolean open = true;
}
