package doc.backendapi.DTO.UserDocDTOpack;

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
    @Size(max = 45)
    String accessLevel;
    Integer documentsDocumentid1Id;
    Integer usersUseridId;
    String ownerDocument;
    Integer isShow;
}
