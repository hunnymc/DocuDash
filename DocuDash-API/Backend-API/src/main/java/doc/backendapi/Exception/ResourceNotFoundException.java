package doc.backendapi.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String document, String message, Integer id) {
        super(message);
    }
}