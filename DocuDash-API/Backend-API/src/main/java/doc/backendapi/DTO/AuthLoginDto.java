package doc.backendapi.DTO;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthLoginDto {
    @NotNull(message = "Email shouldn't be null ")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email is invalid")
    @Size(min = 1, max = 100, message = "Email should have length between 1-100")
    private String userEmail;

    @NotNull(message = "password is required")
    @NotBlank(message = "password should not be blank")
    private String password;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail.trim();;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
