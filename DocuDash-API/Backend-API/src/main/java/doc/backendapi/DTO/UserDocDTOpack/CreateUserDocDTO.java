package doc.backendapi.DTO.UserDocDTOpack;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link doc.backendapi.entities.Userdocument}
 */
@Data
@NoArgsConstructor
public class CreateUserDocDTO implements Serializable {

    String accessLevel;

    @NotNull(message = "Document ID must not be null")
    @NotBlank(message = "Document ID must not be blank")
    Integer documentsDocumentid1Id;

    @NotNull(message = "User ID must not be null")
    @NotBlank(message = "User ID must not be blank")
    Integer usersUseridId;

    @Size(max = 255, message = "Owner Document size must not be more than 255")
    @NotBlank(message = "Owner Document must not be blank")
    String ownerDocument;

    @NotNull(message = "Show status must not be null")
    @NotBlank(message = "Show status must not be blank")
    Integer isShow;
}
