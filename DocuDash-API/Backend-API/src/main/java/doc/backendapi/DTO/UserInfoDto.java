package doc.backendapi.DTO;

import lombok.*;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link doc.backendapi.entities.User}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String username;
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