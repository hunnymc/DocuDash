package doc.backendapi.hadlers;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomHttpException extends RuntimeException {
    private final HttpStatus status;

    public CustomHttpException( HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}