package doc.backendapi.DTO.approval;

// ManagerVerifyListDto
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManagerVerifyListDto implements Serializable {
    int status_type_id;
    int totalManagers;
    int totalManagersWhoVerified;
    int approve_type_Id;
    int isManagerRead;
    int sentToUser;
    private List<VerifydocDto> managersWhoVerified;
    private UserVerifydocDto documentInfo;
}