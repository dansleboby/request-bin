package io.graversen.requestbin.data.cassandra;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("hashes_by_algorithm")
public class RequestByRequestBinEntity {
    @NonNull
    @PrimaryKeyColumn(name = "bin_id", type = PrimaryKeyType.PARTITIONED)
    private String binId;

    @NonNull
    @PrimaryKeyColumn(name = "created_at")
    private LocalDateTime createdAt;

    @NonNull
    @Column("request_body")
    private String requestBody;

    @NonNull
    @Column("query_parameters")
    private String queryParameters;

    @NonNull
    @Column("http_headers")
    private String httpHeaders;

    @NonNull
    @Column("ip_address")
    private String ipAddress;

    @NonNull
    @Column("http_verb")
    private String httpVerb;

    @NonNull
    @Column("request_duration")
    private Long requestDuration;
}
