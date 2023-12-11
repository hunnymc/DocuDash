package doc.backendapi.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link doc.backendapi.entities.Document}
 */
@Data
@NoArgsConstructor
public class CreateDocDto implements Serializable {
    @Size(max = 255)
    String title;
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
    String status;
    @Size(max = 255)
    String description;
    @NotNull
    CreateDocDto.UserDto1 usersUserid;

    /**
     * DTO for {@link doc.backendapi.entities.User}
     */
    @Data
    @NoArgsConstructor
    public static class UserDto1 implements Serializable {
        Integer id;
    }
}