package br.com.bd_notifica.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

// @RestControllerAdvice is often better for REST APIs. It combines @ControllerAdvice and @ResponseBody.
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            RecursoNaoEncontradoException ex, WebRequest request) {

        // 1. Create the standardized error response object
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value()); // e.g., 404
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase()); // e.g., "Not Found"
        errorResponse.setMessage(ex.getMessage()); // The specific message from your exception
        errorResponse.setPath(request.getDescription(false).replace("uri=", "")); // The URL path

        // 2. Return a ResponseEntity with the error object and the correct HTTP Status
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // You can add more handlers for other exceptions here
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()); // "Internal Server Error"
        errorResponse.setMessage("An unexpected error occurred: " + ex.getMessage());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));

        // It's very important to log the full exception for debugging
        // logger.error("An unexpected error occurred", ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}