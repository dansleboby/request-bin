package io.graversen.requestbin.api;

import io.graversen.requestbin.data.dto.RequestBinCreated;
import io.graversen.requestbin.data.mysql.RequestBinEntity;
import io.graversen.requestbin.data.service.CreateRequestBin;
import io.graversen.requestbin.service.RequestBinService;
import io.graversen.requestbin.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestBinController {
    private final RequestBinService requestBinService;

    @GetMapping
    public ResponseEntity<RequestBinCreated> createBin(ServerHttpRequest serverHttpRequest) {
        final String clientIp = Utils.getIpAddress(serverHttpRequest);
        final String userAgent = Utils.getUserAgent(serverHttpRequest);

        final var createRequestBin = new CreateRequestBin(clientIp, userAgent);
        final RequestBinEntity requestBinEntity = requestBinService.createNew(createRequestBin);

        final var requestBinCreated = new RequestBinCreated(requestBinEntity.getBinId());

        return ResponseEntity.ok(requestBinCreated);
    }
}
