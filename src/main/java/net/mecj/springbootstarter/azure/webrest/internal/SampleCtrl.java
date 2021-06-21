package net.mecj.springbootstarter.azure.webrest.internal;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import net.mecj.springbootstarter.azure.apiresponse.restresponse.ErrorRestResponse;
import net.mecj.springbootstarter.azure.apiresponse.restresponse.OkRestResponse;
import net.mecj.springbootstarter.azure.constants.CommonResourceErrorCode;
import net.mecj.springbootstarter.azure.constants.PersonValidationErrorCode;
import net.mecj.springbootstarter.azure.constants.UpstreamErrorCode;
import net.mecj.springbootstarter.azure.exception.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@ConditionalOnExpression("${application.development.endpoints.samplectrl:false}")
public class SampleCtrl {

    @Value("${key-vault-key}")
    private String secret;

    @GetMapping(path = "/sample/keyvault")
    public ResponseEntity keyvault() {
        Map map = new HashMap();
        map.put("secret", secret);
        return OkRestResponse.ok(map).responseEntity();
    }

    @GetMapping(path = "/sample/ok/single")
    public ResponseEntity okSingle() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();

        return OkRestResponse.ok(person).responseEntity();
    }

    @GetMapping(path = "/sample/ok/multiple")
    public ResponseEntity okMultiple() {
        Fairy fairy = Fairy.create();
        List<Person> persons = new LinkedList<>();
        for (int ii = 0; ii < 10; ii++) {
            persons.add(fairy.person());
        }

        return OkRestResponse.ok(persons).responseEntity();
    }

    @GetMapping(path = "/sample/error/notfound")
    public ResponseEntity errorResponse() {
        ErrorRestResponse errorRestResponse = ErrorRestResponse
                .notFound()
                .error(CommonResourceErrorCode.RESOURCE_NOT_FOUND);
        throw new AppException(errorRestResponse);
    }

    @GetMapping(path = "/sample/error/upstream")
    public ResponseEntity errorUpstreamResponse() {
        RuntimeException runtimeException = new RuntimeException();

        ErrorRestResponse errorRestResponse = ErrorRestResponse
                .upstreamError()
                .error(UpstreamErrorCode.UPSTREAM_ERROR)
                .cause(runtimeException);
        throw new AppException(errorRestResponse);
    }

    @GetMapping(path = "/sample/error/form")
    public ResponseEntity errorFormResponse() {
        ErrorRestResponse formErrorRestResponse = ErrorRestResponse
                .validationError()
                .error(CommonResourceErrorCode.INVALID_FORM)
                .fieldError("id", PersonValidationErrorCode.ID_LENGTH_INVALID)
                .fieldError("id", PersonValidationErrorCode.ID_SPECIAL_CHAR_NOT_ALLOWED);
        throw new AppException(formErrorRestResponse);
    }
}