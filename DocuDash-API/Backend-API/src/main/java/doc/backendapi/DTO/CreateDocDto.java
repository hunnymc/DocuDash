package doc.backendapi.DTO;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocDto implements Serializable {

    @NotNull(message = "Title must not be null")
    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title size must not be more than 255")
    String title;
    @NotNull(message = "Category must not be null")
    @NotBlank(message = "Category must not be blank")
    @Size(max = 255, message = "Category size must not be more than 255")
    String category;

    @NotNull(message = "Secrecy Level must not be null")
    @NotBlank(message = "Secrecy Level must not be blank")
    String secrecyLevel;

    @NotNull(message = "Urgency must not be null")
    @NotBlank(message = "Urgency must not be blank")
    String urgency;

    @NotNull(message = "From Source must not be null")
    @NotBlank(message = "From Source must not be blank")
    String fromSource;

    @NotNull(message = "Branch Source must not be null")
    @NotBlank(message = "Branch Source must not be blank")
    @Size(max = 255, message = "Branch Source size must not be more than 255")
    String branchSource;

    @NotNull(message = "Email Source must not be null")
    @NotBlank(message = "Email Source must not be blank")
    @Size(max = 255, message = "Email Source size must not be more than 255")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email is invalid")
    String emailSource;

    @NotNull(message = "Phone Source must not be null")
    @NotBlank(message = "Phone Source must not be blank")
    @Size(max = 15, message = "Phone Source size must not be more than 15")
    String phoneSource;

//    Integer status;

    @Size(max = 255, message = "Description size must not be more than 255")
    String description;

    @NotNull(message = "User ID must not be null")
    @NotBlank(message = "User ID must not be blank")
    CreateDocDto.UserDto1 usersUserid;

    @Data
    @NoArgsConstructor
    public static class UserDto1 implements Serializable {
        @NotNull(message = "User ID must not be null")
        @NotBlank(message = "User ID must not be blank")
        Integer id;
    }
}