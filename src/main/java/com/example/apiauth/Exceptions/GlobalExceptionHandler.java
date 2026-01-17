package com.example.apiauth.Exceptions;

import com.example.apiauth.Utils.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return HttpResponse.send(getMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ NoHandlerFoundException.class, NoResourceFoundException.class })
    public ResponseEntity<Object> handleNotFound(Exception ex) {
        log.warn("Route not found: {}", ex.getMessage());
        return HttpResponse.send(getMessage("error.route.not.found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleJsonError(HttpMessageNotReadableException ex) {
        log.error("Malformed JSON: {}", ex.getMessage());
        return HttpResponse.send(getMessage("error.json.malformed"), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDbConflict(DataIntegrityViolationException ex) {
        log.warn("DB Conflict: {}", ex.getMostSpecificCause().getMessage());
        return HttpResponse.send(getMessage("error.db.conflict"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return HttpResponse.send(getMessage("error.validation"), HttpStatus.UNPROCESSABLE_ENTITY, errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return HttpResponse.send(getMessage("error.access.denied"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        log.error("Critical Error: {}", ex.getClass().getName(), ex);
        return HttpResponse.send(getMessage("error.internal"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}