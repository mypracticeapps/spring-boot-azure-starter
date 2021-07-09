package net.mecj.springbootstarter.azure.apiresponse.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.mecj.springbootstarter.azure.apiresponse.errordetail.ErrorDetail;

import static org.assertj.core.api.Assertions.assertThat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PersonValidationErrorCode implements ErrorDetail {

    ID_LENGTH_INVALID("ID_LENGTH_INVALID", "ID field length should be between 5 to 10 chars"),

    ID_SPECIAL_CHAR_NOT_ALLOWED("ID_SPECIAL_CHAR_NOT_ALLOWED", "Special characters are allowed in id");


    private String code, message;

    PersonValidationErrorCode(String code, String message) {
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
