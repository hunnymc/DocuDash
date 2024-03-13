package doc.backendapi.hadlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value());
        httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
    }
    //    error handle for @valid -> sprint boot starter validation -> in entities
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
//      error default
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("timestamp",new Date());
        details.put("status",status.value());

//        error handle -> get all fields
        Map<String , String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String Name = ((FieldError) error).getField();
            String errorMes = error.getDefaultMessage();
            errors.put(Name , errorMes);
        });

        details.put("errors" , errors);
        return new ResponseEntity<>(details,headers,status);
    }

}