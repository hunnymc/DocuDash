package doc.backendapi.DTO;

import doc.backendapi.entities.User;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Getter
@Setter
public class UserDto  {
    private Integer id;
    @Size(max = 255)
    private String username;
    @Size(max = 255)
    private String fullName;
    @Size(max = 255)
    private String role;
    @Size(max = 255)
    private String email;
    @Size(max = 255)
    private String phone;
    @Size(max = 255)
    private String branch;
}