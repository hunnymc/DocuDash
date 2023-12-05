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

    @NotNull
    @NotEmpty
    @NotBlank
    String title;

    Instant dateAdd;

    Instant dateUpdate;

    @NotNull
    @NotEmpty
    @NotBlank
    String category;

    @NotNull
    @NotEmpty
    @NotBlank
    String secrecyLevel;

    @NotNull
    @NotEmpty
    @NotBlank
    String urgency;

    @NotNull
    @Size
    @NotEmpty
    @NotBlank
    String fromSource;

    String status;

    private UserDto usersUserid;


}