package org.training.mentorservice.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.ObjectName;
import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${spring.application.bad_request}")
    private String errorCodeBadRequest;

    @Value("${spring.application.conflict}")
    private String errorCodeConflict;

    @Value("${spring.application.not_found}")
    private String errorCodeNotFound;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Set<String> errors = new HashSet<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(new ErrorResponse(errorCodeBadRequest, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<Object> handleResourceConflictException(ResourceConflictException ex){
        return new ResponseEntity<>(new ErrorResponse(errorCodeConflict, Set.of(ex.getLocalizedMessage())), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFound ex) {
        return new ResponseEntity<>(new ErrorResponse(errorCodeNotFound, Set.of(ex.getLocalizedMessage())), HttpStatus.NOT_FOUND);
    }
}
