package com.example.backend.payload.exception;

import com.example.backend.payload.i18n.TranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @Value("${spring.application.error-uri}")
    private String errorUri;

    private final TranslateService translateService;

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        return getProblemDetail(
                HttpStatus.NOT_FOUND,
                translateService.getMessage(
                        ex.getMessage(),
                        ex.getArgs()
                )
        );
    }

    private ProblemDetail getProblemDetail(HttpStatus httpStatus, String detail) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                httpStatus,
                detail
        );
        pd.setType(URI.create(errorUri));
        return pd;
    }
}