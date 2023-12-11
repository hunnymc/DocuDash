package doc.backendapi.DTO;

import doc.backendapi.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

/**
 * DTO for {@link doc.backendapi.entities.Document}
 */
@Getter
@Setter
public class DocumentDto {

    Integer id;
    String title;
    Instant dateAdd;
    Instant dateUpdate;
    String category;
    String secrecyLevel;
    String urgency;
    String filePath;
    String fromSource;
    String branchSource;
    String description;
    String emailSource;
    String phoneSource;
    String status;

    private UserDto usersUserid;


}