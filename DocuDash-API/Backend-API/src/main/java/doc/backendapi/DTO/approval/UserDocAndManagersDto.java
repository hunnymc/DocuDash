package doc.backendapi.DTO.approval;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDocAndManagersDto implements Serializable {

    int status_type_id;
    int totalManagers;
    int totalManagersWhoVerified;
    int approve_type_Id;
    int sentToUser;
    private List<VerifydocDto> managersWhoVerified;
    private UserVerifydocDto documentInfo;

}