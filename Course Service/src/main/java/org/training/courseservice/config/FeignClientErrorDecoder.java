package org.training.courseservice.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.training.courseservice.exception.ResourceNotFound;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {


    @Override
    public Exception decode(String s, Response response) {

        switch (response.status()){

            case 404:
                return new ResourceNotFound();
            default:
                return new Exception();
        }
    }
}
