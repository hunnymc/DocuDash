package doc.backendapi.hadlers;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request
) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("timestamp", Instant.now());
        errors.put("status", Instant.now());
        errors.put("message", "Validate fields");
        Map<String, String> errorFields = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorFields.put(error.getField(),error.getDefaultMessage());}

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errorFields.put(error.getObjectName(),error.getDefaultMessage());}
        errors.put("fields", errorFields);
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }}

