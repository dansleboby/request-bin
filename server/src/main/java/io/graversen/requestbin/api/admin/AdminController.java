package io.graversen.requestbin.api.admin;

import io.graversen.requestbin.configuration.RequestBinProperties;
import io.graversen.requestbin.service.requestbin.CreateRequestBin;
import io.graversen.requestbin.service.requestbin.RequestBin;
import io.graversen.requestbin.service.requestbin.RequestBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
    private static final String X_SECRET_HEADER = "X-Secret";
    private final RequestBinProperties requestBinProperties;
    private final RequestBinService requestBinService;

    @GetMapping("bins")
    public ResponseEntity<List<RequestBinDTO>> getRequestBins(@RequestHeader(X_SECRET_HEADER) String secret) {
        if (secretInvalid(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        final var requestBins = requestBinService.getOpenBins().stream()
                .map(mapRequestBin())
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok(requestBins);
    }

    @DeleteMapping("bins/{binId}")
    public ResponseEntity<List<RequestBinDTO>> closeRequestBin(@RequestHeader(X_SECRET_HEADER) String secret, @PathVariable String binId) {
        if (secretInvalid(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        requestBinService.closeBin(binId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("bins")
    public ResponseEntity<Void> createRequestBin(@RequestHeader(X_SECRET_HEADER) String secret, @RequestBody CreateRequestBinDTO createRequestBin) {
        if (secretInvalid(secret)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        requestBinService.createNew(
                new CreateRequestBin("admin", "admin", createRequestBin.getSource(), createRequestBin.getBinId(), createRequestBin.isPersistent())
        );

        return ResponseEntity.ok().build();
    }

    Function<RequestBin, RequestBinDTO> mapRequestBin() {
        return requestBin -> new RequestBinDTO(
                requestBin.getBinId(),
                requestBin.getCreatedAt(),
                requestBin.getExpiresAt(),
                requestBin.getCreatedBy()
        );
    }

    boolean secretInvalid(String secret) {
        return !requestBinProperties.getAdminSecret().equals(secret);
    }
}
