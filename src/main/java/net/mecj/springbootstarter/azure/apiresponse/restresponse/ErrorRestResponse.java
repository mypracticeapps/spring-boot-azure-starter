package net.mecj.springbootstarter.azure.apiresponse.restresponse;

import lombok.Getter;
import lombok.Setter;
import net.mecj.springbootstarter.azure.apiresponse.errordetail.ErrorDetail;
import net.mecj.springbootstarter.azure.apiresponse.errordetail.FormErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Getter
@Setter
public class ErrorRestResponse extends RestResponse {
    private ErrorDetail error;
    private List<ErrorDetail> subErrors = new LinkedList<>();
    private Object cause[];
    private Object stackTrace[];

    public static ErrorRestResponse badRequest() {
        return status(400);
    }

    public static ErrorRestResponse unauthorized() {
        return status(401);
    }

    public static ErrorRestResponse forbidden() {
        return status(403);
    }

    public static ErrorRestResponse notFound() {
        return status(404);
    }

    public static ErrorRestResponse conflict() {
        return status(409);
    }

    public static ErrorRestResponse validationError() {
        return status(422);
    }

    public static ErrorRestResponse unknown() {
        return status(500);
    }

    public static ErrorRestResponse notImplemented() {
        return status(501);
    }

    public static ErrorRestResponse upstreamError() {
        return status(502);
    }

    private static ErrorRestResponse status(int status) {
        ErrorRestResponse restResponse = new ErrorRestResponse();
        restResponse.setStatus(status);
        return restResponse;
    }

    public ErrorRestResponse error(ErrorDetail error) {
        assertThat(error).isNotNull();
        this.error = error;
        return this;
    }

    public ErrorRestResponse subError(ErrorDetail errorDetail) {
        assertThat(errorDetail).isNotNull();
        subErrors.add(errorDetail);
        return this;
    }

    public ErrorRestResponse fieldError(String field, ErrorDetail error) {
        assertThat(field).isNotEmpty();
        assertThat(error).isNotNull();

        this.subError(new FormErrorDetail(field, error));
        return this;
    }

    public ErrorRestResponse cause(Exception exception) {
        assertThat(exception).isNotNull();
        this.cause = Arrays.stream(exception.getStackTrace()).map(trace -> trace.toString()).toArray();
        return this;
    }

    public boolean containsErrors() {
        return error != null || subErrors.size() > 0;
    }

    public void disableStackTraceAndCause() {
        this.stackTrace = null;
        this.cause = null;
    }

    @Override
    public ResponseEntity responseEntity() {
        return new ResponseEntity(this, HttpStatus.valueOf(this.getStatus()));
    }
}
