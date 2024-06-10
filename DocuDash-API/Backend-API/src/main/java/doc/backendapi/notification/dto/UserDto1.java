package doc.backendapi.notification.dto;

import lombok.Value;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link doc.backendapi.entities.User}
 */
@Value
public class UserDto1 implements Serializable {
    Integer id;
    @Size(max = 255)
    String username;
    @Size(max = 255)
    String password;
    @Size(max = 255)
    String fullName;
    @Size(max = 255)
    String role;
    @Size(max = 255)
    String email;
    @Size(max = 255)
    String phone;
    @Size(max = 255)
    String branch;
}