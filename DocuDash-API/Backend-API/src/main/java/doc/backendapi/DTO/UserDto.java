package doc.backendapi.DTO;

import doc.backendapi.entities.User;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
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