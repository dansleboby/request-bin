package io.graversen.requestbin.data.cassandra;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("request_by_request_bin")
public class RequestByRequestBinEntity {
    @NonNull
    @PrimaryKeyColumn(name = "bin_id", type = PrimaryKeyType.PARTITIONED)
    private String binId;

    @NonNull
    @PrimaryKeyColumn(name = "bucket", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID bucket = Uuids.timeBased();

    @NonNull
    @PrimaryKeyColumn(name = "created_at", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDateTime createdAt = LocalDateTime.now();

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
    private String requestDuration;
}
