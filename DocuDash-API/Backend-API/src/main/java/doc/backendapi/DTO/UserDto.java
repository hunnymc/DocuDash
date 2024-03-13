package doc.backendapi.DTO;

import doc.backendapi.Enum.Role;
import doc.backendapi.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO for {@link User}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;

    private String username;

    private String fullName;

    private Role role;

    private String email;

    private String phone;

    private String branch;

}