package net.mecj.springbootstarter.azure.config;

import lombok.extern.slf4j.Slf4j;
import net.mecj.springbootstarter.azure.apiresponse.restresponse.ErrorRestResponse;
import net.mecj.springbootstarter.azure.apiresponse.restresponse.RestResponse;
import net.mecj.springbootstarter.azure.exception.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class DefaultRestExceptionHandler {

    @Value("${application.development.response.stackTrace:false}")
    private boolean isResponseDebugEnabled;

    @ExceptionHandler({AppException.class})
    public final ResponseEntity handleRestException(Exception ex, WebRequest request) {
        log.error("rest exception", ex);
        AppException exception = (AppException) ex;
        ErrorRestResponse restResponse = exception.getRestResponse();
        if (isResponseDebugEnabled == false) {
            restResponse.disableStackTraceAndCause();
        }
        return restResponse.responseEntity();
    }

    @ExceptionHandler({Exception.class})
    public final ResponseEntity handleUnknownException(Exception ex, WebRequest request) {
        log.error("unknown exception", ex);
        RestResponse restResponse = ErrorRestResponse.unknown();
        return restResponse.responseEntity();
    }
}