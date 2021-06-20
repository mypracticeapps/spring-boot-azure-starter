package net.mecj.springbootstarter.azure.apiresponse.restresponse;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public abstract class RestResponse {
    private long timestamp = System.currentTimeMillis();
    private int status;

    public abstract ResponseEntity responseEntity();
}