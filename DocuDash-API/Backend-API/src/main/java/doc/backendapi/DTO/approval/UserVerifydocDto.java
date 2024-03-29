package doc.backendapi.DTO.approval;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link doc.backendapi.entities.Document}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVerifydocDto implements Serializable {
    Integer id;
    @Size(max = 255)
    String title;
    @Size(max = 255)
    String filePath;
    Instant dateAdd;
    Instant dateUpdate;
    @Size(max = 255)
    String category;
    @Size(max = 255)
    String secrecyLevel;
    @Size(max = 255)
    String urgency;
    @Size(max = 255)
    String fromSource;
    @Size(max = 255)
    String branchSource;
    @Size(max = 255)
    String emailSource;
    @Size(max = 255)
    String phoneSource;
    @Size(max = 255)
    Integer status;
    @Size(max = 255)
    String description;
    @NotNull
    UserVerifydocDto.UserDto usersUserid;

    /**
     * DTO for {@link doc.backendapi.entities.User}
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto implements Serializable {
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
}