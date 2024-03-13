package doc.backendapi.DTO;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link doc.backendapi.entities.User}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto implements Serializable {
    Integer id;

//    @NotBlank(message = "Username must not be blank")
//    @Size(max = 100, message = "Username size must not be more than 100")
    String username;

    @Size(max = 255)
    String password;

    @NotBlank(message = "Fullname must not be blank")
    @Size(max = 100, message = "Username size must not be more than 100")
    String fullName;

    @NotBlank(message = "Role must not be blank")
    @Enumerated(EnumType.STRING)
    String role;

    @Size(max = 255)
    @NotBlank(message = "Email must not be blank")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email is invalid")
    String email;

//    @NotBlank(message = "Phone must not be blank")
//    @Size(max = 15, message = "Phone size must not be more than 15")
    String phone;

    @NotBlank(message = "Branch must not be blank")
    @Size(max = 255, message = "Branch size must not be more than 255")
    String branch;

}