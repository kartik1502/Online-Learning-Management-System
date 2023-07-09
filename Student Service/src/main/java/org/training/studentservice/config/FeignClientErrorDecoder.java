package org.training.studentservice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.training.studentservice.exception.ErrorResponse;
import org.training.studentservice.exception.ResourceConflictExists;
import org.training.studentservice.exception.ResourseNotFound;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        ErrorResponse errorResponse = extractGlobalException(response);

        switch (response.status()) {
            case 404:
                log.error("Resource not found exception is thrown");
                return new ResourseNotFound(getErrorMessageOrDefault(errorResponse, "Requested resource not found"));
            case 409:
                log.error("Resource conflict exception is thrown");
                return new ResourceConflictExists(getErrorMessageOrDefault(errorResponse, "Resource conflict exists"));
            default:
                log.error("Error occurred when processing the response from the Feign Client");
                return new Exception("Generic exception occurred");
        }
    }

    private String getErrorMessageOrDefault(ErrorResponse errorResponse, String defaultValue) {
        if (errorResponse != null && errorResponse.getErrorMessage() != null) {
            return errorResponse.getErrorMessage().toString();
        }
        return defaultValue;
    }

    private ErrorResponse extractGlobalException(Response response) {
        ErrorResponse errorResponse = null;
        System.out.println(response);
        try (Reader reader = response.body().asReader(StandardCharsets.UTF_8)) {
            String result = IOUtils.toString(reader);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            errorResponse = mapper.readValue(result, ErrorResponse.class);
            System.out.println(mapper);
            System.out.println(errorResponse);
        } catch (IOException e) {
            log.error("IO exception occurred while reading the exception from the Feign client", e);
            throw new RuntimeException("Failed to parse the error response", e);
        }

        return errorResponse;
    }

}
