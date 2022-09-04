package com.kevinchamorro.bancopichincha.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerExceptions(
            Exception e
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(e.getMessage())
                        .build(),
                status
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(
            Exception e
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(e.getMessage())
                        .build(),
                status
        );
    }
}
