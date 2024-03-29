package doc.backendapi.DTO.user;

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
public class AllUserDto implements Serializable {
    Integer id;
    String fullName;
    String role;
    String email;
    String phone;
    String branch;
}