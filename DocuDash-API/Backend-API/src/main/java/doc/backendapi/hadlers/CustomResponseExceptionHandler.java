package doc.backendapi.hadlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomResponseExceptionHandler {

    @ExceptionHandler(CustomHttpException.class)
    public ResponseEntity<Object> handleCustomHttpException(CustomHttpException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", ex.getStatus().value());
        body.put("class", "CustomHttpException");

        // ------- set error by status code -------
        if (ex.getStatus().is4xxClientError()) {
            body.put("error", "Client Error");
        } else if (ex.getStatus().is5xxServerError()) {
            body.put("error", "Server Error");
        } else {
            body.put("error", "Unknown Error");
        }
        // ----------------------------------------

        body.put("message", ex.getMessage());

        // ------- get path from request description --------
        String description = request.getDescription(false);
        int index = description.indexOf("=");
        if (index > -1) {
            String path = description.substring(index + 1);
            body.put("path", path);
        }
        // -------------------------------------------------

        return new ResponseEntity<>(body, ex.getStatus());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", new Date());
//        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        body.put("class", "Exception");
//        body.put("error", "Server Error");
//        body.put("message", ex.getMessage());
//
//        // ------- get path from request description --------
//        String description = request.getDescription(false);
//        int index = description.indexOf("=");
//        if (index > -1) {
//            String path = description.substring(index + 1);
//            body.put("path", path);
//        }
//        // -------------------------------------------------
//
//        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("class", "IllegalArgumentException");
        body.put("error", "Client Error");
        body.put("message", ex.getMessage());

        // ------- get path from request description --------
        String description = request.getDescription(false);
        int index = description.indexOf("=");
        if (index > -1) {
            String path = description.substring(index + 1);
            body.put("path", path);
        }
        // -------------------------------------------------

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", new Date());
            body.put("status", HttpStatus.BAD_REQUEST.value());
            body.put("class", "ConstraintViolationException");
            body.put("error", "Client Error");
            body.put("message", ex.getMessage());

            // ------- get path from request description --------
            String description = request.getDescription(false);
            int index = description.indexOf("=");
            if (index > -1) {
                String path = description.substring(index + 1);
                body.put("path", path);
            }
            // -------------------------------------------------

            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }



}