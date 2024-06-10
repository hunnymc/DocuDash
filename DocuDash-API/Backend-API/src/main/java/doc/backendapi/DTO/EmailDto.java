package doc.backendapi.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Data
@NoArgsConstructor
public class EmailDto {

    @NotNull(message = "Email shouldn't be null ")
    @Email(message = " email is require email ")
    @Size(min = 1, max = 100, message = " email must have length between 1-100 ")
    private String email;

}
