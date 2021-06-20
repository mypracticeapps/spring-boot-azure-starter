package net.mecj.springbootstarter.azure.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.mecj.springbootstarter.azure.apiresponse.errordetail.ErrorDetail;

import static org.assertj.core.api.Assertions.assertThat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UpstreamErrorCode implements ErrorDetail {
    UPSTREAM_ERROR("UPSTREAM_ERROR", "an upstream service currently unavailable. therefore we are not able to full fill your request at this time");

    private String code, message;

    private UpstreamErrorCode(String code, String message) {
        assertThat(code).isNotEmpty();
        assertThat(message).isNotEmpty();

        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
