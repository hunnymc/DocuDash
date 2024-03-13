package doc.backendapi.DTO;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthLoginDto {
    @NotNull(message = "Email shouldn't be null ")
    @Email(message = " email is require email ")
    @Size(min = 1, max = 100, message = " email must have length between 1-100 ")
    private String userEmail;

    @NotNull(message = "password is required")
    @Size(min = 8, max = 14, message = "password must have length between 8-14")
    private String password;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail.trim();;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
