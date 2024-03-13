package doc.backendapi.DTO;

import doc.backendapi.entities.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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