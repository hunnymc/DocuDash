package doc.backendapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Response {
    private HttpStatus httpStatus;
    private String message;

    public static ResponseEntity<Object> response(HttpStatus httpStatus, String message){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("timestamp", new Date());
        map.put("status", httpStatus.value());
        map.put("error", httpStatus.toString().replaceAll("\\d","").trim());
        map.put("message", message);
        return new ResponseEntity<Object>(map,httpStatus);
    }
}