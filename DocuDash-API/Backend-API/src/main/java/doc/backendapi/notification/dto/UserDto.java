package doc.backendapi.notification.dto;

import lombok.Value;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link doc.backendapi.entities.User}
 */
@Value
public class UserDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String fullName;
}