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

        switch (response.status()) {
            case 404:
                log.error("Resource not found exception is thrown");
                return new ResourseNotFound("Requested resource not found");
            case 409:
                log.error("Resource conflict exception is thrown");
                return new ResourceConflictExists("Resource conflict exists");
            default:
                log.error("Error occurred when processing the response from the Feign Client");
                return new Exception("Generic exception occurred");
        }
    }

}
