package doc.backendapi.DTO.approval;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDocAndManagersDto implements Serializable {
    private UserVerifydocDto documentInfo;
    private List<VerifydocDto> managersWhoVerified;
}