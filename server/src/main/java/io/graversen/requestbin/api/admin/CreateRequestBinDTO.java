package io.graversen.requestbin.api.admin;

import lombok.Data;

@Data
public class CreateRequestBinDTO {
    private String binId;
    private String source;
    private boolean persistent;
}
